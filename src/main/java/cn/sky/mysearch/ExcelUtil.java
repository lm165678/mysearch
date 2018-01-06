package cn.sky.mysearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	private static final String FILE_SAVE_ENCODING = "UTF-8";
	
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
	public static void findWords_excel(String filepath,List<String> words,boolean logAll,CONSOLE_LEVEL console_level,String resultpath) {
		String filename = filepath.substring(filepath.lastIndexOf("\\")+1,filepath.length());
		if(filename.startsWith("~$")) {
			return;
		}
		List<String> newWords = new ArrayList<String>();
		for(String word:words) {
			newWords.add(word.toLowerCase());
		}
		Workbook workbook = null;
		try {
			if(filepath.endsWith(".xls")) {
				try {
					workbook = new HSSFWorkbook(new FileInputStream(filepath));//HSSF
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
				//throw new RuntimeException("该文件不是Excel。");
				return;
			}
			Iterator<Sheet> it_sheet = workbook.sheetIterator();
			while(it_sheet.hasNext()) {
				Sheet sheet = it_sheet.next();
				Iterator<Row> it_row = sheet.iterator();
				while(it_row.hasNext()) {
					Row row = it_row.next();
					Iterator<Cell> it_cell = row.cellIterator();
					while(it_cell.hasNext()) {
						Cell cell = it_cell.next();
						cell.setCellType(CellType.STRING);
						String value = cell.getStringCellValue();
						if(newWords.contains(value.toLowerCase())) {
							String content = "filename="+filepath+";\tsheetname="+sheet.getSheetName()+";\taddress="+cell.getAddress()+";\tvalue="+value+"\r\n";
							if(console_level.equals(CONSOLE_LEVEL.CONSOLE)) {
								System.out.print(content);
								if(!logAll) {
									break;
								}
							}else if(console_level.equals(CONSOLE_LEVEL.FILE)) {
								appendToFile(content, resultpath);
								if(!logAll) {
									break;
								}
							}else if(console_level.equals(CONSOLE_LEVEL.CONSOLE_FILE)){
								System.out.print(content);
								appendToFile(content, resultpath);
								if(!logAll) {
									break;
								}
							}
							
						}
					}
				}
			}
		} finally {
			if(workbook!=null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void appendToFile(String content,String filepath) {
		File file = new File(filepath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,true);//追加
			fos.write(content.getBytes(FILE_SAVE_ENCODING));
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
