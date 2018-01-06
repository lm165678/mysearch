package cn.sky.mysearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.sky.mysearch.ExcelUtil.CONSOLE_LEVEL;

public class App {

	public static void main(String[] args) {
		
		List<String> words = new ArrayList<String>();
		words.add("provision");
		FileFolder ff = new FileFolder() {
			@Override
			protected void processFile(String filepath) {
				ExcelUtil.findWords_excel(filepath, words, true, CONSOLE_LEVEL.CONSOLE_FILE,"E:/result.txt");
			}
		};
		ff.recursive("E:"+File.separatorChar+"aaa");
	}

}
