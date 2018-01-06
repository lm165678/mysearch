package cn.sky.mysearch.util;

import java.util.Properties;

public class PropertiesUtil {
	private static Properties prop = null;
	static {
		prop = new Properties();
		try {
			prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("mysearch.properties"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public static String get(String key) {
		return prop.getProperty(key);
	}
}
