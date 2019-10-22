package com.imxss.web.install;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.util.EncryptUtil;
import org.coody.framework.util.PrintException;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.SpringContextHelper;
import org.coody.framework.util.StringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.imxss.web.constant.FormatFinal;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Component
public class InstallHandle implements InitializingBean {

	private static BaseLogger logger = BaseLogger.getLogger(InstallHandle.class);

	public static List<PropertyConfig> propertys;

	private static Boolean isInstall = null;

	public static boolean isInstall() {
		if (isInstall != null) {
			return isInstall;
		}
		if (propertys == null) {
			propertys = getPropertyConfig();
		}
		if (StringUtil.isNullOrEmpty(propertys)) {
			return false;
		}
		for (PropertyConfig property : propertys) {
			String value = property.config.getProperty("installed");
			if ("1".equals(value)) {
				isInstall = true;
				return isInstall;
			}
		}
		isInstall = false;
		return false;
	}

	public static boolean writeConfig(String fieldName, String value) throws URISyntaxException {
		for (PropertyConfig property : propertys) {
			if (!property.config.containsKey(fieldName)) {
				continue;
			}
			try {
				property.config.setProperty(fieldName, value);
				FileOutputStream output = new FileOutputStream(new File(property.path));
				property.config.store(output, "");
				output.close();
				return true;
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
		}
		return false;
	}

	public static Connection getConnection(String host, String dbUser, String pwd, String dbName) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");// 指定连接类型
			String url = MessageFormat.format(
					"jdbc:mysql://{0}/{1}?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8", host,
					dbName == null ? "" : dbName);
			conn = DriverManager.getConnection(url, dbUser, pwd);// 获取连接
			conn.setAutoCommit(true);
			return conn;
		} catch (Exception e) {
			return conn;
		}
	}

	public static boolean dbCheck(String host, String dbUser, String pwd, String dbName) {
		Connection conn = null;
		try {
			conn = getConnection(host, dbUser, pwd, dbName);
			return conn != null;
		} catch (Exception e) {
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	// 创建数据库
	public static boolean createDataBase(String host, String dbUser, String pwd, String dbName) {
		Connection conn = getConnection(host, dbUser, pwd, null);
		try {
			String sql = "CREATE DATABASE `" + dbName + "` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;";
			PreparedStatement stat = conn.prepareStatement(sql);
			int code = stat.executeUpdate();
			return code > 0;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	public static boolean createTables(String host, String dbUser, String pwd, String dbName)
			throws URISyntaxException {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + "/imxss.sql";
		try {
			SQLExec sqlExec = new SQLExec();
			Class.forName("com.mysql.jdbc.Driver");// 指定连接类型
			String url = MessageFormat.format(
					"jdbc:mysql://{0}/{1}?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8", host,
					dbName == null ? "" : dbName);
			sqlExec.setDriver("com.mysql.jdbc.Driver");
			sqlExec.setUrl(url);
			sqlExec.setUserid(dbUser);
			sqlExec.setPassword(pwd);
			sqlExec.setAutocommit(true);
			sqlExec.setSrc(new File(path));
			sqlExec.setEncoding("UTF-8");
			sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute.getInstance(SQLExec.OnError.class, "continue")));
			sqlExec.setPrint(true);
			sqlExec.setProject(new Project());
			sqlExec.execute();
			return true;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return false;
		}
	}

	public static boolean writeSetting(String host, String dbUser, String pwd, String dbName) {
		Connection conn = getConnection(host, dbUser, pwd, dbName);
		try {
			String sql = "INSERT INTO `setting_info` set id='1', siteName='ImXSS 国内最专业的Xss渗透测试平台', keywords='国内最专业的Xss渗透测试平台  --ImXSS', description='ImXSS为Coody研发且开源。是国内最专业的Xss渗透平台', copyright='Copyright © 2014-2019 Scrum Group 版权所有',openReg= '1', needInvite='0'";
			PreparedStatement stat = conn.prepareStatement(sql);
			int code = stat.executeUpdate();
			return code > 0;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	public static boolean writeAdminUser(String host, String dbUser, String pwd, String dbName, String adminUser,
			String adminPwd) {
		Connection conn = getConnection(host, dbUser, pwd, dbName);
		String sql = "insert into user_info set email=?,userPwd=?,status=0,roleId=1,nickName='admin'";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setObject(1, adminUser);
			stat.setObject(2, EncryptUtil.customEnCode(adminPwd));
			int code = stat.executeUpdate();
			return code > 0;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	private static void writeDataSource(String jdbcUrl, String dbUser, String dbPwd) {
		ComboPooledDataSource dataSource = SpringContextHelper.getBean(ComboPooledDataSource.class);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(dbUser);
		dataSource.setPassword(dbPwd);
		dataSource.resetPoolManager();
	}

	private static List<PropertyConfig> getPropertyConfig() {
		PropertyPlaceholderConfigurer configurer = SpringContextHelper.getBean(PropertyPlaceholderConfigurer.class);
		if (StringUtil.isNullOrEmpty(configurer)) {
			return null;
		}
		Resource[] locations = (Resource[]) PropertUtil.getFieldValue(configurer, "locations");
		List<PropertyConfig> propertys = new ArrayList<InstallHandle.PropertyConfig>();
		for (Resource resource : locations) {
			try {
				File file = resource.getFile();
				Properties properties = new Properties();
				properties.load(new FileInputStream(file));
				PropertyConfig config = new PropertyConfig();
				config.path = file.getPath();
				config.config = properties;
				propertys.add(config);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return propertys;
	}

	public static MsgEntity install(InstallConfig config) throws URISyntaxException {
		if (isInstall()) {
			return new MsgEntity(-1, "ImXSS已安装，如需重装请修改config/*.properties下installed参数为0");
		}
		if (!StringUtil.isNullOrEmpty(config.host)) {
			config.host = config.host.replace("：", ":");
		}
		if (!StringUtil.isEmail(config.adminUser)) {
			return new MsgEntity(-1, "用户名格式有误");
		}
		if (!StringUtil.isMatcher(config.adminPwd, FormatFinal.USER_PWD)) {
			return new MsgEntity(-1, "密码格式有误");
		}
		if (!dbCheck(config.host, config.dbUser, config.dbPwd, "")) {
			return new MsgEntity(-1, "数据库连接失败");
		}
		if (dbCheck(config.host, config.dbUser, config.dbPwd, config.dbName)) {
			return new MsgEntity(-1, "数据库已存在");
		}
		createDataBase(config.host, config.dbUser, config.dbPwd, config.dbName);
		if (!createTables(config.host, config.dbUser, config.dbPwd, config.dbName)) {
			return new MsgEntity(-1, "数据内容导入失败");
		}
		String jdbcUrl = MessageFormat.format(
				"jdbc:mysql://{0}/{1}?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8", config.host,
				config.dbName);
		// 写入超级管理员
		if (!writeAdminUser(config.host, config.dbUser, config.dbPwd, config.dbName, config.adminUser,
				config.adminPwd)) {
			return new MsgEntity(-1, "管理员添加失败，请手动添加");
		}
		// 写入网站配置
		writeSetting(config.host, config.dbUser, config.dbPwd, config.dbName);
		// 写入配置文件
		if (!writeConfig("jdbc_url", jdbcUrl) || !writeConfig("jdbc_user", config.dbUser)
				|| !writeConfig("jdbc_password", config.dbPwd) || !writeConfig("installed", "1")) {
			return new MsgEntity(-1, "配置文件写入失败");
		}
		// 重置数据源
		writeDataSource(jdbcUrl, config.dbUser, config.dbPwd);
		// 刷新安装配置
		isInstall = true;
		return new MsgEntity(0, "安装成功");
	}

	private static class PropertyConfig {
		private String path;

		private Properties config;

	}

	public static class InstallConfig {
		String host;
		String dbUser;
		String dbPwd;
		String dbName;
		String adminUser;
		String adminPwd;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		isInstall();
	}

}
