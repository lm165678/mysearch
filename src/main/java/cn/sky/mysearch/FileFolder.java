package cn.sky.mysearch;

import java.io.File;

public abstract class FileFolder {
	
	public void recursive(String filepath) {
		File file = new File(filepath);
		if(file.exists()&&file.isDirectory()) {
			String[] filenames = file.list();
			if(filenames!=null&&filenames.length>0) {
				for(String filename:filenames) {
					recursive(filepath+File.separatorChar+filename);
				}
			}
		}else {
			processFile(filepath);
		}
	}
	protected abstract void processFile(String filepath);
	
}
