package cn.sky.mysearch.config;

import cn.sky.mysearch.util.PropertiesUtil;

public class Config {
	private String logfile_path = "E:\\result.txt";
	private String logfile_encoding = "UTF-8";
	private String logfile_content_template = "filename={filepath};\tsheetname={sheetname};\taddress={address};\tvalue={value}\r\n;";
	
	private Config() {
		this.logfile_path = PropertiesUtil.get("logfile_path");
		this.logfile_encoding = PropertiesUtil.get("logfile_encoding");
		this.logfile_content_template = PropertiesUtil.get("logfile_content_template");
	}
	
	private static Config instance = new Config();
	public static Config getConfig() {
		return instance;
	}
	
	public String getLogfile_content_template() {
		return logfile_content_template;
	}
	public void setLogfile_content_template(String logfile_content_template) {
		this.logfile_content_template = logfile_content_template;
	}
	public String getLogfile_path() {
		return logfile_path;
	}
	public void setLogfile_path(String logfile_path) {
		this.logfile_path = logfile_path;
	}
	public String getLogfile_encoding() {
		return logfile_encoding;
	}
	public void setLogfile_encoding(String logfile_encoding) {
		this.logfile_encoding = logfile_encoding;
	}
}
