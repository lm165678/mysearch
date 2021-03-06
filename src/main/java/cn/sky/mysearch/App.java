package cn.sky.mysearch;

import java.util.Arrays;

import cn.sky.mysearch.config.Config;
import cn.sky.mysearch.handler.ExcelHandler;
import cn.sky.mysearch.handler.FileHandler;
import cn.sky.mysearch.handler.FileHandler.CONSOLE_LEVEL;
import cn.sky.mysearch.handler.WordHandler;
import cn.sky.mysearch.util.FileFolder;

public class App {

	public static void main(String[] args) {
		long time1 = System.currentTimeMillis();
		Config config = Config.getConfig();
		String[] words = {"java"};
		
//		testexcel(config,words);
		testword(config,words);
		
		long time2 = System.currentTimeMillis();
		System.out.println("耗时:"+(time2-time1)/1000.0+"s");
	}

	private static void testword(Config config, String[] words) {
		FileHandler fileHandler = WordHandler.getInstance();
		FileFolder folder = new FileFolder() {
			@Override
			protected void processFile(String filepath) {
				fileHandler.findText(filepath, Arrays.asList(words), true, CONSOLE_LEVEL.CONSOLE_FILE,config.getLogfile_path());
			}
		};
		folder.recursive("E:\\aaa");
	}

	private static void testexcel(Config config, String[] words) {
		FileHandler fileHandler = ExcelHandler.getInstance();
		FileFolder folder = new FileFolder() {
			@Override
			protected void processFile(String filepath) {
				fileHandler.findText(filepath, Arrays.asList(words), true, CONSOLE_LEVEL.CONSOLE_FILE,config.getLogfile_path());
			}
		};
		folder.recursive("E:\\aaa");
	}

}
