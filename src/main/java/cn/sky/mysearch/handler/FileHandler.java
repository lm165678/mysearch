package cn.sky.mysearch.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.sky.mysearch.config.Config;

public abstract class FileHandler {
	private Config config = Config.getConfig();
	public enum CONSOLE_LEVEL{
		CONSOLE,FILE,CONSOLE_FILE
	}
	/**
	 * 从excel查找内容
	 * @param filepath 文件名（含全路径）
	 * @param words 关键词
	 * @param logAll 是否记录文件的所有包含关键词的内容，如果否，则只记录第一条就返回结果
	 * @param console_level  搜索结果打印在控制台还是保存到文件
	 * @param resultpath 保存到文件的全路径
	 */
	public abstract void findText(String filepath,List<String> words,boolean logAll,CONSOLE_LEVEL console_level,String resultpath);
	
	protected synchronized void appendToFile(String content,String filepath) {
		File file = new File(filepath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,true);//追加
			fos.write(content.getBytes(config.getLogfile_encoding()));
			fos.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
