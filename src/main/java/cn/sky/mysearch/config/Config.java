package cn.sky.mysearch.config;

import cn.sky.mysearch.util.PropertiesUtil;

public class Config {
	private String logfile_path = "E:\\result.txt";
	private String logfile_encoding = "UTF-8";
	private String logfile_content_template_excel = "filename={filepath};\tsheetname={sheetname};\taddress={address};\tvalue={value}\r\n;";
	private String logfile_content_template_word = "filename={filepath};\tword={word}\r\n;";
	
	private Config() {
		this.logfile_path = PropertiesUtil.get("logfile_path")==null?logfile_path:PropertiesUtil.get("logfile_path");
		this.logfile_encoding = PropertiesUtil.get("logfile_encoding")==null?logfile_encoding:PropertiesUtil.get("logfile_encoding");
		this.logfile_content_template_excel = PropertiesUtil.get("logfile_content_template_excel")==null?logfile_content_template_excel:PropertiesUtil.get("logfile_content_template_excel");
		this.logfile_content_template_word = PropertiesUtil.get("logfile_content_template_word")==null?logfile_content_template_word:PropertiesUtil.get("logfile_content_template_word");
	}
	
	private static Config instance = new Config();
	public static Config getConfig() {
		return instance;
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

	public String getLogfile_content_template_excel() {
		return logfile_content_template_excel;
	}

	public void setLogfile_content_template_excel(String logfile_content_template_excel) {
		this.logfile_content_template_excel = logfile_content_template_excel;
	}

	public String getLogfile_content_template_word() {
		return logfile_content_template_word;
	}

	public void setLogfile_content_template_word(String logfile_content_template_word) {
		this.logfile_content_template_word = logfile_content_template_word;
	}
}
