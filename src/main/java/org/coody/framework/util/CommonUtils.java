package org.coody.framework.util;

import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 公共方法调用�?
 * @author xiaoxiao
 *
 */
public class CommonUtils {

	/**
	 * 根据分割符将字符串分割成String数组
	 * @param src 源字符串
	 * @param separator 分隔�?
	 * @return String数组
	 */
	public static String[] splitToStringArray(String src, String separator) {
		Vector<String> splitArrays = new Vector<String>();
		int i = 0;
		int j = 0;
		while (i <= src.length()) {
			j = src.indexOf(separator, i);
			if (j < 0) {
				j = src.length();
			}
			splitArrays.addElement(src.substring(i, j));
			i = j + 1;
		}
		int size = splitArrays.size();
		String[] array = new String[size];
		System.arraycopy(splitArrays.toArray(), 0, array, 0, size);
		return array;
	}
	
	/**
	 * 根据分割符将字符串分割成Integer数组
	 * @param src 源字符串
	 * @param separator 分隔�?
	 * @return Integer数组
	 */
	public static Integer[] splitToIntgArray(String src,String separator){
		String[] arr = splitToStringArray(src,separator);
		Integer[] intArr = new Integer[arr.length];
		for(int i = 0 ; i < arr.length ; i++){
			intArr[i] = Integer.valueOf(arr[i]);
		}
		return intArr;
	}
	
	/**
	 * 根据分隔符将字符串分割成int数组
	 * @param src 源字符串
	 * @param separator 分隔�?
	 * @return int数组
	 */
	public static int[] splitToIntArray(String src,String separator){
		String[] arr = splitToStringArray(src,separator);
		int[] intArr = new int[arr.length];
		for(int i = 0 ; i < arr.length ; i++){
			intArr[i] = Integer.parseInt(arr[i]);
		}
		return intArr;
	}
	
	/**
	 * 返回固定长度的字符串
	 * @param src 原字符串
	 * @param append 不够指定长度时需要添加的字符�?
	 * @param length 固定长度
	 * @param leftOrRight true 左对齐\右添�?false 右对齐\左添�?
	 * @return 固定长度字符�?
	 */
	public static String formatToString(String src,String append,int length,boolean leftOrRight){
		if(src.length() >= length){
			if(leftOrRight){
				return src.substring(0,length);
			}else{
				return src.substring(src.length()-length);
			}
		}else{
			if(leftOrRight){
				StringBuilder sb = new StringBuilder(src);
				for(int i = 1 ; i <= length-src.length() ; i++){
					sb.append(append);
				}
				return sb.toString();
			}else{
				StringBuilder sb = new StringBuilder();
				for(int i = 1 ; i <= length-src.length() ; i++){
					sb.append(append);
				}
				sb.append(src);
				return sb.toString();
			}
		}
	}
	
	/**
	 * 获取指定长度的随机字符串
	 * @return
	 */
	public synchronized static String getRandomStr(int length) {
		char[] takeArr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', '0',
				'1', '2', '3', '4', '5', '6', '7', '8', '9', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		char[] result = new char[length];
		for (int i = 0, j = 56; i < length; ++i, --j) {
			int take = (int) (Math.random() * j);
			result[i] = takeArr[take];
			char m = takeArr[j - 1];
			takeArr[j - 1] = takeArr[take];
			takeArr[take] = m;
		}
		return new String(result);
	}
	
	public static byte[] initHmacSHA256Key() throws NoSuchAlgorithmException {  
        // 初始化HmacMD5摘要算法的密钥产生器  
        KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");  
        return generator.generateKey().getAlgorithm().getBytes();
    }  

	public static String getHexString(byte[] b) throws Exception {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

    public static String encodeHmacSHA256(byte[] data, byte[] key) throws Exception {  
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");  
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
        mac.init(secretKey);  
        byte[] digest = mac.doFinal(data);
        return getHexString(digest);
    }
	
    public static String decodeUnicode(String theString) {
  	  char aChar;
  	  int len = theString.length();
  	  StringBuffer outBuffer = new StringBuffer(len);
  	  for (int x = 0; x < len;) {
  	   aChar = theString.charAt(x++);
  	   if (aChar == '\\') {
  	    aChar = theString.charAt(x++);
  	    if (aChar == 'u') {
  	     int value = 0;
  	     for (int i = 0; i < 4; i++) {
  	      aChar = theString.charAt(x++);
  	      switch (aChar) {
  	      case '0':
  	      case '1':
  	      case '2':
  	      case '3':
  	      case '4':
  	      case '5':
  	      case '6':
  	      case '7':
  	      case '8':
  	      case '9':
  	       value = (value << 4) + aChar - '0';
  	       break;
  	      case 'a':
  	      case 'b':
  	      case 'c':
  	      case 'd':
  	      case 'e':
  	      case 'f':
  	       value = (value << 4) + 10 + aChar - 'a';
  	       break;
  	      case 'A':
  	      case 'B':
  	      case 'C':
  	      case 'D':
  	      case 'E':
  	      case 'F':
  	       value = (value << 4) + 10 + aChar - 'A';
  	       break;
  	      default:
  	       throw new IllegalArgumentException(
  	         "Malformed      encoding.");
  	      }

  	     }
  	     outBuffer.append((char) value);
  	    } else {
  	     if (aChar == 't') {
  	      aChar = '\t';
  	     } else if (aChar == 'r') {
  	      aChar = '\r';
  	     } else if (aChar == 'n') {
  	      aChar = '\n';
  	     } else if (aChar == 'f') {
  	      aChar = '\f';
  	     }
  	     outBuffer.append(aChar);
  	    }
  	   } else {
  	    outBuffer.append(aChar);
  	   }

  	  }
  	  return outBuffer.toString();

  	 }
    
	public static void main(String args[]){
	}
}
