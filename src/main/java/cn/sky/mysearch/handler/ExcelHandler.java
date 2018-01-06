package cn.sky.mysearch.handler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.sky.mysearch.config.Config;
import cn.sky.mysearch.util.StringTemplateUtil;

public class ExcelHandler extends FileHandler{
	private ExcelHandler() {}
	public static ExcelHandler getInstance() {
		return new ExcelHandler();
	}
	
	public void findText(String filepath,List<String> words,boolean logAll,CONSOLE_LEVEL console_level,String resultpath) {
		String filename = filepath.substring(filepath.lastIndexOf("\\")+1,filepath.length());
		if(filename.startsWith("~$")) {
			return;
		}
		List<String> newWords = new ArrayList<String>();
		for(String word:words) {
			newWords.add(word.toLowerCase());
		}
		Workbook workbook = null;
		InputStream inputStream = null;
		try {
			if(filepath.endsWith(".xls")) {
				try {
					inputStream = new FileInputStream(filepath);
					workbook = new HSSFWorkbook(inputStream);//HSSF
				}catch (Exception e) {
					System.out.println(filepath+"异常:"+e.getMessage());
					return;
				}
			}else if(filepath.endsWith(".xlsx")) {
				try {
					workbook = new XSSFWorkbook(new FileInputStream(filepath));//XSSF
				}catch (Exception e) {
					System.out.println(filepath+"异常:"+e.getMessage());
					return;
				}
			}else {
				//System.out.println("该文件不是Excel。");
				return;
			}
			Iterator<Sheet> it_sheet = workbook.sheetIterator();
			label_sheet:while(it_sheet.hasNext()) {
				Sheet sheet = it_sheet.next();
				Iterator<Row> it_row = sheet.iterator();
				while(it_row.hasNext()) {
					Row row = it_row.next();
					Iterator<Cell> it_cell = row.cellIterator();
					while(it_cell.hasNext()) {
						Cell cell = it_cell.next();
						cell.setCellType(CellType.STRING);
						String text = cell.getStringCellValue().toLowerCase();
						for(String word:newWords){
							if(text.contains(word)){
								String content_template = Config.getConfig().getLogfile_content_template_excel();
								String content = null;
								Map<String,Object> param = new HashMap<String,Object>();
								param.put("filepath", filepath);
								param.put("sheetname", sheet.getSheetName());
								param.put("address", cell.getAddress());
								param.put("word", word);
								content = StringTemplateUtil.process(content_template,param);
								if(console_level.equals(CONSOLE_LEVEL.CONSOLE)) {
									System.out.print(content);
									if(!logAll) {
										continue label_sheet;
									}
								}else if(console_level.equals(CONSOLE_LEVEL.FILE)) {
									appendToFile(content, resultpath);
									if(!logAll) {
										continue label_sheet;
									}
								}else if(console_level.equals(CONSOLE_LEVEL.CONSOLE_FILE)){
									System.out.print(content);
									appendToFile(content, resultpath);
									if(!logAll) {
										continue label_sheet;
									}
								}else{
									//
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			System.out.println(filepath+"异常:"+e.getMessage());
		} finally {
			if(workbook!=null) {
				try {
					workbook.close();
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
	}
	
}
