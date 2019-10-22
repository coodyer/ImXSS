package org.coody.framework.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipUtil {

	private static void makeSupDir(String outFileName) {
		Pattern p = Pattern.compile("[/\\" + File.separator + "]");
		Matcher m = p.matcher(outFileName);
		while (m.find()) {
			int index = m.start();
			String subDir = outFileName.substring(0, index);
			File subDirFile = new File(subDir);
			if (!subDirFile.exists())
				subDirFile.mkdir();
		}
	}

	@SuppressWarnings("resource")
	public static String unZipFile(String jarPath) {
		try {
			JarFile jarFile = new JarFile(jarPath);
			Enumeration<JarEntry> jarEntrys = jarFile.entries();
			if(jarPath.toLowerCase().endsWith(".jar")){
				jarPath=jarPath.substring(0,jarPath.length()-".jar".length());
			}
			while (jarEntrys.hasMoreElements()) {
				JarEntry jarEntry = jarEntrys.nextElement();
				jarEntry.getName();
				String outFileName = jarPath+"/" + jarEntry.getName();
				makeSupDir(outFileName);
				if (jarEntry.isDirectory()) {
					continue;
				}
				File f = new File(outFileName);
				writeFile(jarFile.getInputStream(jarEntry), f);
			}
			return jarPath;
		} catch (Exception e) {
			return null;
		}
		
	}

	private static void writeFile(InputStream ips, File outputFile) {
		try {
			OutputStream ops = new BufferedOutputStream(new FileOutputStream(outputFile));
			try {
				byte[] buffer = new byte[1024];
				int nBytes = 0;
				while ((nBytes = ips.read(buffer)) > 0) {
					ops.write(buffer, 0, nBytes);
				}
			} catch (IOException ioe) {
				throw ioe;
			} finally {
				try {
					if (null != ops) {
						ops.flush();
						ops.close();
					}
				} catch (IOException ioe) {
					throw ioe;
				} finally {
					if (null != ips) {
						ips.close();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void main(String[] args) {
		unZipFile("D:/个人文件/mysql-connector-java-5.1.27-bin.jar");
	}

}
