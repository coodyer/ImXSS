package org.coody.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.entity.HttpEntity;

/**
 * HTTP工具类 支持Cookie操作、支持GZIP解压、支持图片下载、支持Head操作、支持编码 BY:WebSOS QQ644556636
 * 2015-05-26
 */
public class HttpUtil {

	private static final BaseLogger logger = BaseLogger.getLoggerPro(HttpUtil.class);

	public final static String POST = "POST";
	public final static String GET = "GET";
	public final static String HEAD = "HEAD";
	public final static String PUT = "PUT";
	public final static String CONNECT = "CONNECT";
	public final static String OPTIONS = "OPTIONS";
	public final static String DELETE = "DELETE";
	public final static String PATCH = "PATCH";
	public final static String PROPFIND = "PROPFIND";
	public final static String PROPPATCH = "PROPPATCH";
	public final static String MKCOL = "MKCOL";
	public final static String COPY = "COPY";
	public final static String MOVE = "MOVE";
	public final static String LOCK = "LOCK";
	public final static String UNLOCK = "UNLOCK";
	public final static String TRACE = "TRACE";

	public final static String DEF_ENCODE = "UTF-8";

	// private final static BaseLogger logger =
	// BaseLogger.getLogger(HttpUtil.class);

	public static HttpEntity Get(String url) {
		try {
			return httpBaseConn(url, GET, null, null, null, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Head(String url, Proxy proxy) {
		try {
			return httpBaseConn(url, HEAD, null, null, null, null, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, null, null, null, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, encode, null, null, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, null, timeout, null, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, Integer timeout, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, encode, timeout, null, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, encode, null, sendCookie, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout, String sendCookie, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, null, timeout, sendCookie, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie, Integer timeout, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, encode, timeout, sendCookie, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, null, null, null, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, encode, null, null, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout, Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, null, timeout, null, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, Integer timeout, Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, encode, timeout, null, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie, Map<String, String> headers,
			Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, encode, null, sendCookie, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout, String sendCookie, Map<String, String> headers,
			Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, null, timeout, sendCookie, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie, Integer timeout,
			Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, GET, null, encode, timeout, sendCookie, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Head(String url) {
		try {
			return httpBaseConn(url, HEAD, null, null, null, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode) {
		try {
			return httpBaseConn(url, GET, null, encode, null, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout) {
		try {
			return httpBaseConn(url, GET, null, null, timeout, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, Integer timeout) {
		try {
			return httpBaseConn(url, GET, null, encode, timeout, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie) {
		try {
			return httpBaseConn(url, GET, null, encode, null, sendCookie, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout, String sendCookie) {
		try {
			return httpBaseConn(url, GET, null, null, timeout, sendCookie, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie, Integer timeout) {
		try {
			return httpBaseConn(url, GET, null, encode, timeout, sendCookie, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Map<String, String> headers) {
		try {
			return httpBaseConn(url, GET, null, null, null, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, Map<String, String> headers) {
		try {
			return httpBaseConn(url, GET, null, encode, null, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout, Map<String, String> headers) {
		try {
			return httpBaseConn(url, GET, null, null, timeout, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, Integer timeout, Map<String, String> headers) {
		try {
			return httpBaseConn(url, GET, null, encode, timeout, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie, Map<String, String> headers) {
		try {
			return httpBaseConn(url, GET, null, encode, null, sendCookie, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, Integer timeout, String sendCookie, Map<String, String> headers) {
		try {
			return httpBaseConn(url, GET, null, null, timeout, sendCookie, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Get(String url, String encode, String sendCookie, Integer timeout,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, GET, null, encode, timeout, sendCookie, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, null, null, null, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, encode, null, null, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, null, timeout, null, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Integer timeout, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, encode, timeout, null, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout, String sendCookie, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, null, timeout, sendCookie, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, String sendCookie, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, encode, null, sendCookie, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Integer timeout, String sendCookie,
			Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, encode, timeout, sendCookie, null, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, null, null, null, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Map<String, String> headers,
			Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, encode, null, null, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout, Map<String, String> headers,
			Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, null, timeout, null, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Integer timeout,
			Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, encode, timeout, null, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout, String sendCookie,
			Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, null, timeout, sendCookie, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, String sendCookie,
			Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, encode, null, sendCookie, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Integer timeout, String sendCookie,
			Map<String, String> headers, Proxy proxy) {
		try {
			return httpBaseConn(url, POST, postData, encode, timeout, sendCookie, headers, proxy);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData) {
		try {
			return httpBaseConn(url, POST, postData, null, null, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode) {
		try {
			return httpBaseConn(url, POST, postData, encode, null, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout) {
		try {
			return httpBaseConn(url, POST, postData, null, timeout, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Integer timeout) {
		try {
			return httpBaseConn(url, POST, postData, encode, timeout, null, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout, String sendCookie) {
		try {
			return httpBaseConn(url, POST, postData, null, timeout, sendCookie, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, String sendCookie) {
		try {
			return httpBaseConn(url, POST, postData, encode, null, sendCookie, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Integer timeout, String sendCookie) {
		try {
			return httpBaseConn(url, POST, postData, encode, timeout, sendCookie, null);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Map<String, String> headers) {
		try {
			return httpBaseConn(url, POST, postData, null, null, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Map<String, String> headers) {
		try {
			return httpBaseConn(url, POST, postData, encode, null, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout, Map<String, String> headers) {
		try {
			return httpBaseConn(url, POST, postData, null, timeout, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Integer timeout,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, POST, postData, encode, timeout, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, Integer timeout, String sendCookie,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, POST, postData, null, timeout, sendCookie, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, String sendCookie,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, POST, postData, encode, null, sendCookie, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Post(String url, String postData, String encode, Integer timeout, String sendCookie,
			Map<String, String> headers) {
		try {
			return httpBaseConn(url, POST, postData, encode, timeout, sendCookie, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Delete(String url, Map<String, String> headers) {
		try {
			return httpBaseConn(url, DELETE, null, null, null, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity Put(String url, String postData, Map<String, String> headers) {
		try {
			return httpBaseConn(url, PUT, postData, null, null, null, headers);
		} catch (Exception e) {

			return null;
		}
	}

	public static HttpEntity httpBaseConn(String url, String method, String postData, String encode, Integer timeout,
			String sendCookie, Map<String, String> headers) throws Exception {
		return httpBaseConn(url, method, postData, encode, timeout, sendCookie, headers, null);
	}

	public static HttpEntity httpBaseConn(String url, String method, String postData, String encode, Integer timeout,
			String sendCookie, Map<String, String> headers, Proxy proxy) throws Exception {
		HttpEntity hEntity = new HttpEntity();
		try {
			if (encode == null || encode.equals("")) {
				encode = DEF_ENCODE;
			}
			if (timeout == null || timeout < 500) {
				timeout = 6000;
			}
			URL serverUrl = new URL(url);
			HttpURLConnection conn = getConnection(serverUrl, headers, proxy);
			if (conn == null) {
				return null;
			}
			if (sendCookie != null) {
				conn.addRequestProperty("Cookie", sendCookie);
			}
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			OutputStream ous = null;
			conn.setRequestMethod(method.toUpperCase());
			if (method.equalsIgnoreCase(POST) || method.equalsIgnoreCase(PUT)) {
				conn.setDoOutput(true);
				byte[] postByte = postData.getBytes(encode);
				conn.setRequestProperty("Content-Length", String.valueOf(postByte.length));
				ous = conn.getOutputStream();
				ous.write(postByte);
				ous.flush();
			}
			InputStream ins = null;
			String key = "";
			StringBuilder cookie = new StringBuilder();
			String newCookie = null;
			try {
				hEntity.setCode(conn.getResponseCode());
				if (hEntity.getCode() == 200) {
					ins = conn.getInputStream();
				} else {
					ins = conn.getErrorStream();
				}
				Map<String, String> headMap = new TreeMap<String, String>();
				for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
					headMap.put(key, conn.getHeaderField(i));
					if (!key.equalsIgnoreCase("set-cookie")) {
						continue;
					}
					try {
						cookie.append(conn.getHeaderField(i).replace("/", ""));
					} catch (Exception e) {
					}
				}
				newCookie = mergeCookie(sendCookie, cookie.toString());
				byte[] b = toByte(ins);
				if (headMap.get("Content-Encoding") != null && headMap.get("Content-Encoding").contains("gzip")) {
					b = gzipDecompress(b);
				}
				hEntity.setBye(b);
				hEntity.setHeadMap(headMap);
				hEntity.setCookie(newCookie);
				hEntity.setEncode(encode);

			} catch (Exception e) {
				PrintException.printException(logger, e);
			} finally {
				if (ous != null) {
					try {
						ous.close();
					} catch (Exception e2) {
					}
				}
				if (ins != null) {
					try {
						ins.close();
					} catch (Exception e2) {
					}
				}
			}
		} catch (Exception e) {
		}
		return hEntity;
	}

	public static byte[] gzipDecompress(final byte[] srcByte) {
		try {
			byte[] depBytes;
			ByteArrayInputStream bis = null;
			ByteArrayOutputStream bos = null;
			try {
				bis = new ByteArrayInputStream(srcByte);
				GZIPInputStream gis = new GZIPInputStream(bis);
				bos = new ByteArrayOutputStream();
				byte[] tmp = new byte[1024];
				int read;
				while ((read = gis.read(tmp)) != -1) {
					bos.write(tmp, 0, read);
				}
				gis.close();
				bos.flush();
				depBytes = bos.toByteArray();
			} finally {
				if (bos != null) {
					bos.close();
					bos = null;
				}
				if (bis != null) {
					bis.close();
					bis = null;
				}
			}
			return depBytes;
		} catch (Exception e) {

			return null;
		}
	}

	private static byte[] toByte(InputStream ins) {
		ByteArrayOutputStream swapStream = null;
		try {
			swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int rc = 0;
			while ((rc = ins.read(buff, 0, 1024)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			return swapStream.toByteArray();
		} catch (Exception e) {

			return null;
		} finally {
			try {
				swapStream.close();
			} catch (IOException e) {

			}
		}
	}

	private static String mergeCookie(String oldCookie, String newCookie) {
		if (newCookie == null) {
			return oldCookie;
		}
		Map<String, String> cookieMap = new TreeMap<String, String>();
		String[] cookTmp = null;
		String[] cookieTab = null;
		StringBuilder valueTmp = new StringBuilder();
		String[] cookies = { oldCookie, newCookie };
		for (String currCookie : cookies) {
			if (StringUtil.isNullOrEmpty(currCookie)) {
				continue;
			}
			cookieTab = currCookie.split(";");
			for (String cook : cookieTab) {
				cookTmp = cook.split("=");
				if (cookTmp.length < 2) {
					continue;
				}
				valueTmp = new StringBuilder();
				for (int i = 1; i < cookTmp.length; i++) {
					valueTmp.append(cookTmp[i]);
					if (i < cookTmp.length - 1) {
						valueTmp.append("=");
					}
				}
				if (StringUtil.findNull(cookTmp[0], valueTmp) > -1) {
					continue;
				}
				cookieMap.put(cookTmp[0], valueTmp.toString());
			}
		}
		valueTmp = new StringBuilder();
		for (String key : cookieMap.keySet()) {
			valueTmp.append(key).append("=").append(cookieMap.get(key));
			valueTmp.append(";");
		}
		return valueTmp.toString();
	}

	private static HttpURLConnection getConnection(URL serverUrl, Map<String, String> headers, Proxy proxy) {
		try {
			HttpURLConnection conn = null;
			if (proxy != null) {
				conn = (HttpURLConnection) serverUrl.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) serverUrl.openConnection();
			}
			if (headers == null) {
				headers = new HashMap<String, String>();
			}
			if (!headers.containsKey("Referer")) {
				conn.addRequestProperty("Referer", serverUrl.toString());
			}
			if (!headers.containsKey("Accept")) {
				conn.addRequestProperty("Accept",
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			}
			if (!headers.containsKey("Content-Type")) {
				conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			}
			if (!headers.containsKey("User-Agent")) {
				conn.addRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
			}
			if (!headers.isEmpty()) {
				for (String key : headers.keySet()) {
					conn.addRequestProperty(key, headers.get(key));
					key = null;
				}
			}
			return conn;
		} catch (Exception e) {
			return null;
		}
	}
}