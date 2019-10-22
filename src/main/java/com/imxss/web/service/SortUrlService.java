package com.imxss.web.service;

import org.coody.framework.context.annotation.CacheWrite;
import org.coody.framework.context.entity.HttpEntity;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.util.HttpUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class SortUrlService {

	@CacheWrite(fields = "url", time = 72000)
	public String getSortUrl(String url) {
		try {
			return getSortUrlImplSina(url);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getSortUrlImplSina(String url) {
		try {
			HttpEntity entity = HttpUtil
					.Get("http://ln.mk/short/create?unionId=3056c4478c744127bc8cd0fadf603d29&url=" + url.replace("http:", "").replace("https:", ""));
			String html = entity.getHtml();
			System.out.println(html);
			MsgEntity result=JSON.parseObject(html, MsgEntity.class);
			String sortUrl = result.getDatas().toString();
			sortUrl = sortUrl.replace("http:", "");
			return sortUrl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(new SortUrlService().getSortUrl("http://imxss.com/"));
	}
}
