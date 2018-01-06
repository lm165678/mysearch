package cn.sky.mysearch.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import cn.sky.mysearch.config.Config;
import cn.sky.mysearch.util.StringTemplateUtil;

public class WordHandler extends FileHandler {
	private WordHandler() {}
	public static WordHandler getInstance() {
		return new WordHandler();
	}

	public void findText(String filepath, List<String> words, boolean logAll, CONSOLE_LEVEL console_level, String resultpath) {
		String filename = filepath.substring(filepath.lastIndexOf("\\")+1,filepath.length());
		if(filename.startsWith("~$")) {
			return;
		}
		List<String> newWords = new ArrayList<String>();
		for(String word:words) {
			newWords.add(word.toLowerCase());
		}
		FileInputStream inputStream = null; 
		HWPFDocument doc = null;
		XWPFDocument docx = null;
		if(filename.endsWith(".doc")){
			try {
				inputStream = new FileInputStream(new File(filepath));
				doc = new HWPFDocument(inputStream);
				String doctext = doc.getDocumentText().toLowerCase();
				for(String word:newWords){
					if(doctext.contains(word)){
						String content_template = Config.getConfig().getLogfile_content_template_word();
						String content = null;
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("filepath", filepath);
						param.put("word", word);
						content = StringTemplateUtil.process(content_template,param);
						if(console_level.equals(CONSOLE_LEVEL.CONSOLE)) {
							System.out.print(content);
						}else if(console_level.equals(CONSOLE_LEVEL.FILE)) {
							appendToFile(content, resultpath);
						}else if(console_level.equals(CONSOLE_LEVEL.CONSOLE_FILE)){
							System.out.print(content);
							appendToFile(content, resultpath);
						}
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(filepath+"异常:"+e.getMessage());
			} finally {
				if(doc!=null){
					try {
						doc.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(inputStream!=null){
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else if(filename.endsWith(".docx")){
			try {
				inputStream = new FileInputStream(new File(filepath));
				docx = new XWPFDocument(inputStream);
				Iterator<XWPFParagraph> it = docx.getParagraphsIterator();
				while(it.hasNext()){
					XWPFParagraph p = it.next();
					String docxtext = p.getText().toLowerCase();
					for(String word:newWords){
						if(docxtext.contains(word)){
							String content_template = Config.getConfig().getLogfile_content_template_word();
							String content = null;
							Map<String,Object> param = new HashMap<String,Object>();
							param.put("filepath", filepath);
							param.put("word", word);
							content = StringTemplateUtil.process(content_template,param);
							if(console_level.equals(CONSOLE_LEVEL.CONSOLE)) {
								System.out.print(content);
							}else if(console_level.equals(CONSOLE_LEVEL.FILE)) {
								appendToFile(content, resultpath);
							}else if(console_level.equals(CONSOLE_LEVEL.CONSOLE_FILE)){
								System.out.print(content);
								appendToFile(content, resultpath);
							}
							return;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(filepath+"异常:"+e.getMessage());
			} finally {
				if(docx!=null){
					try {
						docx.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(inputStream!=null){
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			//System.out.println("该文件不是word。");
			return;
		}
	}

}
