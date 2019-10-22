package com.imxss.web.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.CacheWipe;
import org.coody.framework.context.annotation.CacheWrite;
import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.base.BaseModel;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.context.entity.Where;
import org.coody.framework.core.cache.LocalCache;
import org.coody.framework.core.jdbc.JdbcHandle;
import org.coody.framework.util.DateUtils;
import org.coody.framework.util.EmailSenderUtil;
import org.coody.framework.util.EncryptUtil;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Service;

import com.imxss.web.constant.CacheFinal;
import com.imxss.web.domain.EmailInfo;
import com.imxss.web.domain.EmailQueue;
import com.imxss.web.domain.EmailRecord;
import com.imxss.web.domain.EmailSendCensus;
import com.imxss.web.domain.UserInfo;

/**
 * @author coody
 * @date 2017年7月11日
 * @blog http://54sb.org
 * @email 644556636@qq.com
 */
@SuppressWarnings("unchecked")
@Service
public class EmailService {

	protected static final BaseLogger logger = BaseLogger.getLogger(EmailService.class);

	@Resource
	JdbcHandle jdbcHandle;
	@Resource
	UserService userService;
	@Resource
	EmailRecordService emailRecordService;

	@CacheWrite(key = CacheFinal.USER_SEND_CENSUS, fields = "userId")
	public EmailSendCensus getSendCensus(Integer userId) {
		Where where = new Where().set("userId", userId).set("day", DateUtils.getDateString());
		return jdbcHandle.findBeanFirst(EmailSendCensus.class, where);
	}

	@CacheWipe(key = CacheFinal.USER_SEND_CENSUS, fields = "userId")
	public Long pushSendNum(Integer userId) {
		EmailSendCensus census = new EmailSendCensus();
		census.setUserId(userId);
		census.setNum(1);
		census.setDay(DateUtils.getDateString());
		return jdbcHandle.saveOrUpdateAuto(census, "sendNum");
	}

	public List<EmailInfo> loadEmails() {
		String key = CacheFinal.EMAIL_LIST;
		List<EmailInfo> emails = (List<EmailInfo>) LocalCache.getCache(key);
		if (!StringUtil.isNullOrEmpty(emails)) {
			return emails;
		}
		emails = jdbcHandle.findBean(EmailInfo.class);
		if (!StringUtil.isNullOrEmpty(emails)) {
			LocalCache.setCache(key, emails, 600);
		}
		return emails;
	}

	public EmailInfo loadEmailInfo(Integer emailId) {
		return jdbcHandle.findBeanFirst(EmailInfo.class, "id", emailId);
	}

	public Long saveEmailInfo(EmailInfo email) {
		Long code = jdbcHandle.saveOrUpdateAuto(email);
		if (code > 0) {
			LocalCache.delCacheFuzz(CacheFinal.EMAIL_LIST);
		}
		return code;
	}

	public Long delEmailInfo(Integer emailId) {
		String sql = "delete from email_info where id=? limit 1";
		Long code = jdbcHandle.doUpdate(sql, emailId);
		if (code > 0) {
			LocalCache.delCacheFuzz(CacheFinal.EMAIL_LIST);
		}
		return code;
	}

	@CacheWrite(key = CacheFinal.EMAIL_LIST, fields = { "keyWorld", "pager.currPage", "pager.pageSize" }, time = 5)
	public Pager loadEmails(Pager pager, String keyWorld) {
		Where where = new Where();
		if (!StringUtil.isNullOrEmpty(keyWorld)) {
			where.set("email", "like", "%" + keyWorld + "%");
		}
		return jdbcHandle.findPager(EmailInfo.class, where, pager, "id", true);
	}

	public boolean sendEmailAuto(String title, String txt, String targeEmail) {
		EmailQueue queue = new EmailQueue();
		queue.setTitle(title);
		queue.setContext(txt);
		queue.setTargeEmail(targeEmail);
		queue.setUnionId(
				EncryptUtil.md5Code(queue.getTitle() + "_" + queue.getContext() + "_" + queue.getTargeEmail()));
		Long code = jdbcHandle.insert(queue);
		if (code != 1) {
			return false;
		}
		return true;
	}

	public void sendEmail(EmailQueue queue) {
		EmailInfo email = null;
		UserInfo userInfo = userService.loadUserInfo(queue.getTargeEmail());
		if (StringUtil.isNullOrEmpty(userInfo)
				|| StringUtil.hasNull(userInfo.getSendEmail(), userInfo.getSendPwd(), userInfo.getSmtp())) {
			List<EmailInfo> emails = loadEmails();
			emails = PropertUtil.doSeq(emails, "sendNum");
			email = emails.get(0);
			writeEmailSendNum(email.getEmail());
		} else {
			email = new EmailInfo();
			email.setSmtp(userInfo.getSmtp());
			email.setEmail(userInfo.getSendEmail());
			email.setPassword(userInfo.getSendPwd());
		}
		String sql = "update email_queue set status=?,updateTime=?,sendEmail=? where id=?";
		Long code = jdbcHandle.doUpdate(sql, 1, new Date(), email.getEmail(),queue.getId());
		if (code < 1) {
			return;
		}
		String day = DateUtils.getDateString();
		EmailRecord emailRecod = emailRecordService.getEmailRecord(queue.getTargeEmail(), day);
		if (emailRecod != null && emailRecod.getSended() > 100) {
			logger.error("该邮箱发信超过100封,本邮件禁止发送>>"+queue.getUnionId()+":"+queue.getContext());
			return;
		}
		code=emailRecordService.addEmailRecord(queue.getTargeEmail(), day);
		if(code<1) {
			logger.error("变更邮件发送次数失败>>"+queue.getUnionId()+":"+queue.getContext());
			return;
		}
		if (EmailSenderUtil.sendEmail(email.getSmtp().trim(), email.getEmail().trim(), email.getPassword().trim(),
				queue.getTitle(), queue.getContext(), queue.getTargeEmail())) {
			return;
		}
		logger.error("邮件发送失败:" + email + ";" + queue);
	}

	public void writeEmailSendNum(String email) {
		List<EmailInfo> emails = loadEmails();
		if (StringUtil.isNullOrEmpty(emails)) {
			return;
		}
		for (EmailInfo emailTmp : emails) {
			if (email.equals(emailTmp.getEmail())) {
				emailTmp.setSendNum(emailTmp.getSendNum() + 1);
				jdbcHandle.doUpdate("update email_info set sendNum=sendNum+1 where email=?", email);
				break;
			}
		}
		LocalCache.setCache(CacheFinal.EMAIL_LIST, emails, 60);
	}

	public void updateErrorEmailTask() {
		String sql = "update email_queue set status=-1 where status=0 and millisecond<?";
		jdbcHandle.doUpdate(sql, System.currentTimeMillis() - (1000 * 60 * 1));
	}

	public List<EmailQueue> getEmailQueues() {
		Where where = new Where();
		where.set("status", 0);
		where.set("millisecond", ">", System.currentTimeMillis() - (1000 * 60 * 1));
		Pager pager = new Pager();
		pager.setPageSize(100);
		List<EmailQueue> queues = jdbcHandle.findBean(EmailQueue.class, where, pager);
		return queues;
	}

	public Integer sendCode(String email) {

		Integer verCode = StringUtil.getRanDom(1000, 9999);
		String key = CacheFinal.EMAIL_CODE + email;
		VerifcatWrapper wrapper = LocalCache.getCache(key);
		if (wrapper != null) {
			if (new Date().getTime() - wrapper.getSendTime().getTime() < 1000 * 60) {
				logger.debug("验证码发送过于频繁:" + email);
				return -1;
			}
		}
		logger.info("发送邮箱验证码：" + email + "==>>" + verCode);
		sendEmailAuto("ImXSS验证码", "您的验证码是：" + verCode + "(五分钟内有效,情尽快使用)", email);
		logger.info("创建验证码:" + email + "==>>" + verCode);
		wrapper = new VerifcatWrapper();
		wrapper.setSendTime(new Date());
		wrapper.setSendNum(wrapper.getSendNum() + 1);
		wrapper.setVerofocatCode(verCode.toString());
		LocalCache.setCache(key, wrapper, 60 * 30);
		return 0;
	}

	public boolean checkVerification(String email, String verificatCode) {
		String key = CacheFinal.EMAIL_CODE + email;
		VerifcatWrapper wrapper = LocalCache.getCache(key);
		if (wrapper == null) {
			return false;
		}
		if (wrapper.getErrNum() > 3) {
			LocalCache.delCache(key);
			return false;
		}
		if (!wrapper.getVerofocatCode().equals(verificatCode)) {
			wrapper.setErrNum(wrapper.getErrNum() + 1);
			LocalCache.setCache(key, wrapper);
			return false;
		}
		return true;
	}

	@SuppressWarnings("serial")
	public static class VerifcatWrapper extends BaseModel {
		private String verofocatCode;

		private Date sendTime;

		private Integer sendNum = 0;

		private Integer errNum = 0;

		public Integer getErrNum() {
			return errNum;
		}

		public void setErrNum(Integer errNum) {
			this.errNum = errNum;
		}

		public String getVerofocatCode() {
			return verofocatCode;
		}

		public void setVerofocatCode(String verofocatCode) {
			this.verofocatCode = verofocatCode;
		}

		public Date getSendTime() {
			return sendTime;
		}

		public void setSendTime(Date sendTime) {
			this.sendTime = sendTime;
		}

		public Integer getSendNum() {
			return sendNum;
		}

		public void setSendNum(Integer sendNum) {
			this.sendNum = sendNum;
		}
	}
}
