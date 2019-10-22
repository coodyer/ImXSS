package org.coody.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.coody.framework.context.base.BaseLogger;

public class StreamUtil {
	
	private static final BaseLogger logger = BaseLogger.getLoggerPro(StreamUtil.class);
	
    /**
     * InputStream转String
     * 这里用的方法和下面一个函数方法不同，看到下面的函数自然会明白
     * @param is
     * @param encode
     * @return
     */
    public static String stream2string(InputStream is, String encode) {
        if (is != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                return sb.toString();
            } catch (UnsupportedEncodingException e) {
                PrintException.printException(logger, e);
            } catch (IOException e) {
                PrintException.printException(logger, e);
            }
        }
        return "";
    }
 
 
    public static String stream2string(InputStream is) {
        return stream2string(is, "utf-8");
    }
 
    /**
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] stream2byte(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }
}