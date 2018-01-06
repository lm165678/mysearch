package cn.sky.mysearch.handler;

import java.util.List;

public class WordHandler extends FileHandler{
	private WordHandler() {}
	public static WordHandler getInstance() {
		return new WordHandler();
	}
	
	public void findText(String filepath,List<String> words,boolean logAll,CONSOLE_LEVEL console_level,String resultpath) {
		//TODO
	}
	
}
