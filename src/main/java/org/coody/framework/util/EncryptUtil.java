package org.coody.framework.util;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Base64;

import org.coody.framework.context.base.BaseLogger;



public final class EncryptUtil {
	
	private static final BaseLogger logger = BaseLogger.getLoggerPro(EncryptUtil.class);

	public final static String CHARSET = "UTF-8";

	private EncryptUtil() {
		throw new Error("Utility classes should not instantiated!");
	}

	public static String decryptMessage(String msg, String key)
			throws Exception {
		byte[] byes=Base64.getDecoder().decode(msg);
		byte[] data = TripleDESUtil.decrypt(byes,
				key.getBytes("UTF-8"));
		return new String(data, CHARSET);
	}

	/**
	 * 加密
	 * 
	 * @param msg
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 * @author hanweizhao
	 */
	public static String encryptMessage(String msg, String key)
			throws Exception {
		byte[] data = TripleDESUtil.encrypt(msg.getBytes(CHARSET),
				key.getBytes("UTF-8"));
		String str= Base64.getEncoder().encodeToString(data);
		return  str;
	}
	
	public static String encryptImg(byte [] imgByet,String key){
		try {
			byte[] data = TripleDESUtil.encrypt(imgByet,
					key.getBytes("UTF-8"));
			String imgStr= Base64.getEncoder().encodeToString(data);
			return imgStr;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return null;
		} 
	}
	public static byte[] decryptImg(String imgStr,String key){
		try {
			byte[] desStr=Base64.getDecoder().decode(imgStr.getBytes("ISO-8859-1"));
			byte[] data = TripleDESUtil.decrypt(desStr,
					key.getBytes("UTF-8"));
			return data;
		} catch (Exception e) {
		 PrintException.printException(logger, e);
		 	return null;
		}
	}
	public static String md5Code(String pwd) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pwd.getBytes(CHARSET));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();

		} catch (Exception e) {

		}
		return "";
	}

	private static String textCode(String s, String key) {
		String str = "";
		int ch;
		if (key.length() == 0) {
			return s;
		} else if (!s.equals(null)) {
			for (int i = 0, j = 0; i < s.length(); i++, j++) {
				if (j > key.length() - 1) {
					j = j % key.length();
				}
				ch = s.codePointAt(i) + key.codePointAt(j);
				if (ch > 65535) {
					ch = ch % 65535;// ch - 33 = (ch - 33) % 95 ;
				}
				str += (char) ch;
			}
		}
		return str;

	}

	@SuppressWarnings("unused")
	private static String textDeCode(String s, String key) {
		String str = "";
		int ch;
		if (key.length() == 0) {
			return s;
		} else if (!s.equals(key)) {
			for (int i = 0, j = 0; i < s.length(); i++, j++) {
				if (j > key.length() - 1) {
					j = j % key.length();
				}
				ch = (s.codePointAt(i) + 65535 - key.codePointAt(j));
				if (ch > 65535) {
					ch = ch % 65535;// ch - 33 = (ch - 33) % 95 ;
				}
				str += (char) ch;
			}
		}
		return str;
	}

	public static String customEnCode(String str) {
		try {
			str = md5Code(str);
			str = textCode(str, str);
			str = str.substring(1, str.length() - 1);
			str = URLEncoder.encode(str, "UTF-8").replace("%", "")
					.toLowerCase();
			str = md5Code(str);
			return str;
		} catch (Exception e) {
			return "";
		}

	}
	
	
	public static void main(String[] args) throws Exception {
		
	}

}
