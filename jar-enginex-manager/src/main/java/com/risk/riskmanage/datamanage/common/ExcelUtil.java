package com.risk.riskmanage.datamanage.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelUtil<T> {
	
	public static <T>  void exportFieldExcel(OutputStream  out ,String exlType,String[] headers,String[] classNames, List<T> list ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Workbook workbook;
		if (exlType.equalsIgnoreCase("xlsx")) {
			// 2007以上
			workbook = new SXSSFWorkbook(200);
		} else {
			// 97-2003
			workbook = new HSSFWorkbook();
		}
		Sheet sheet = workbook.createSheet("title");	
		sheet.setColumnWidth((short)0, 10* 256);
		sheet.setColumnWidth((short)1, 20* 256);
		sheet.setColumnWidth((short)2, 20* 256);
		sheet.setColumnWidth((short)3, 10* 256);
		sheet.setColumnWidth((short)4, 10* 256);
		sheet.setColumnWidth((short)5, 75* 256);
//		sheet.setColumnWidth((short)6, 75* 256);

		
		CellStyle cellStyle = workbook.createCellStyle(); 
		CellStyle cellStyle1 = workbook.createCellStyle(); 
		
		Font font = workbook.createFont(); 
		Font font1 = workbook.createFont();
		
		//微软雅黑,字体10,加粗，背景灰色
		font.setFontName("微软雅黑"); 
		font.setFontHeight((short) 240); 
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());  
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setWrapText(true); 
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN); 
		cellStyle.setBorderRight(CellStyle.BORDER_THIN); 
		cellStyle.setBorderTop(CellStyle.BORDER_THIN); 
				
		//微软雅黑,字体10,加粗，背景灰色
		font1.setFontName("微软雅黑"); 
		font1.setFontHeight((short) 240); 
		cellStyle1.setFont(font1);
		cellStyle1.setAlignment(CellStyle.ALIGN_LEFT); // 指定单元格居中对齐 
		cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle1.setWrapText(true); 
		cellStyle1.setBorderBottom(CellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyle1.setBorderLeft(CellStyle.BORDER_THIN); 
		cellStyle1.setBorderRight(CellStyle.BORDER_THIN); 
		cellStyle1.setBorderTop(CellStyle.BORDER_THIN); 
		
		Row row = sheet.createRow(0);
		row.setHeight((short) 400);
		
		for (short i = 0; i < headers.length; i++) {
	         Cell cell = row.createCell(i);
	         cell.setCellStyle(cellStyle);
	         cell.setCellValue(headers[i]);
        }
		Iterator<T> it = list.iterator();
	    int index = 0;
	    while (it.hasNext()) {
	         index++;
	         row = sheet.createRow(index);
	         row.setHeight((short) 400);
	         T t = it.next();
	       if(null!=t){
	          for (int j = 0; j < headers.length; j++) { 
		         Cell cell = row.createCell(j);
		         Field field = t.getClass().getDeclaredField(classNames[j]);
		         field.setAccessible(true);   	
		         Class valType = field.getType();
		         cell.setCellStyle(cellStyle1);
		            //数据转类型
		            if("java.lang.String".equalsIgnoreCase( valType.getName())){
		            	cell.setCellValue((String) field.get(t));
		            }else if("java.lang.Integer".equalsIgnoreCase(valType.getName())||"int".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Integer) field.get(t));
		            }else if("java.lang.Double".equalsIgnoreCase(valType.getName())||"double".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Double) field.get(t)==null?0:(Double) field.get(t));
		            }else if("java.lang.Long".equalsIgnoreCase(valType.getName())||"long".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Long) field.get(t)==null?0:(Long) field.get(t));
		            }else if("java.util.Date".equalsIgnoreCase(valType.getName())) {
		            	if(null==field.get(t)){
		            		cell.setCellValue("");
		            	}else {
		            		SimpleDateFormat sdf   = new SimpleDateFormat("yyyy/MM/dd");
		            		cell.setCellValue(sdf.format((Date) field.get(t)));
		            	}
		            }
		            field.setAccessible(false);
		        }  
	         }   
	       }
		try {
			workbook.write(out);
			out.flush();
			out.close();
			out = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
			if (out != null) 
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 导出黑白名单库里的客户列表到excel（兼容黑白名单库的导入模版）
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static <T>  void exportCustListExcel(OutputStream  out ,String exlType,String[] headers,String[] classNames, List<T> list ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Workbook workbook;
		if (exlType.equalsIgnoreCase("xlsx")) {
			// 2007以上
			workbook = new SXSSFWorkbook(200);
		} else {
			// 97-2003
			workbook = new HSSFWorkbook();
		}
		Sheet sheet = workbook.createSheet("title");
		//默认20列
		sheet.setColumnWidth((short)0, 10* 256);
		sheet.setColumnWidth((short)1, 10* 256);
		sheet.setColumnWidth((short)2, 10* 256);
		sheet.setColumnWidth((short)3, 10* 256);
		sheet.setColumnWidth((short)4, 10* 256);
		sheet.setColumnWidth((short)5, 10* 256);
		sheet.setColumnWidth((short)6, 10* 256);
		sheet.setColumnWidth((short)7, 10* 256);
		sheet.setColumnWidth((short)8, 10* 256);
		sheet.setColumnWidth((short)9, 10* 256);
		sheet.setColumnWidth((short)10, 10* 256);
		sheet.setColumnWidth((short)11, 10* 256);
		sheet.setColumnWidth((short)12, 10* 256);
		sheet.setColumnWidth((short)13, 10* 256);
		sheet.setColumnWidth((short)14, 10* 256);
		sheet.setColumnWidth((short)15, 10* 256);
		sheet.setColumnWidth((short)16, 10* 256);
		sheet.setColumnWidth((short)17, 10* 256);
		sheet.setColumnWidth((short)18, 10* 256);
		sheet.setColumnWidth((short)19, 10* 256);
		
		CellStyle cellStyle = workbook.createCellStyle(); 
		CellStyle cellStyle1 = workbook.createCellStyle(); 
		
		Font font = workbook.createFont(); 
		Font font1 = workbook.createFont();
		
		//微软雅黑,字体10,加粗，背景灰色
		font.setFontName("微软雅黑"); 
		font.setFontHeight((short) 240); 
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());  
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setWrapText(true); 
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN); 
		cellStyle.setBorderRight(CellStyle.BORDER_THIN); 
		cellStyle.setBorderTop(CellStyle.BORDER_THIN); 
				
		//微软雅黑,字体10,加粗，背景灰色
		font1.setFontName("微软雅黑"); 
		font1.setFontHeight((short) 240); 
		cellStyle1.setFont(font1);
		cellStyle1.setAlignment(CellStyle.ALIGN_LEFT); // 指定单元格居中对齐 
		cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle1.setWrapText(true); 
		cellStyle1.setBorderBottom(CellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyle1.setBorderLeft(CellStyle.BORDER_THIN); 
		cellStyle1.setBorderRight(CellStyle.BORDER_THIN); 
		cellStyle1.setBorderTop(CellStyle.BORDER_THIN); 
		
		Row row = sheet.createRow(0);
		row.setHeight((short) 400);
		
		for (short i = 0; i < headers.length; i++) {
	         Cell cell = row.createCell(i);
	         cell.setCellStyle(cellStyle);
	         cell.setCellValue(headers[i]);
        }
		Iterator<T> it = list.iterator();
	    int index = 0;
	    while (it.hasNext()) {
	         index++;
	         row = sheet.createRow(index);
	         row.setHeight((short) 400);
	         T t = it.next();
	       if(null!=t){
	          for (int j = 0; j < headers.length; j++) { 
		         Cell cell = row.createCell(j);
		         Field field = t.getClass().getDeclaredField(classNames[j]);
		         field.setAccessible(true);   	
		         Class valType = field.getType();
		         cell.setCellStyle(cellStyle1);
		            //数据转类型
		            if("java.lang.String".equalsIgnoreCase( valType.getName())){
		            	cell.setCellValue((String) field.get(t));
		            }else if("java.lang.Integer".equalsIgnoreCase(valType.getName())||"int".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Integer) field.get(t));
		            }else if("java.lang.Double".equalsIgnoreCase(valType.getName())||"double".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Double) field.get(t)==null?0:(Double) field.get(t));
		            }else if("java.lang.Long".equalsIgnoreCase(valType.getName())||"long".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Long) field.get(t)==null?0:(Long) field.get(t));
		            }else if("java.util.Date".equalsIgnoreCase(valType.getName())) {
		            	if(null==field.get(t)){
		            		cell.setCellValue("");
		            	}else {
		            		SimpleDateFormat sdf   = new SimpleDateFormat("yyyy/MM/dd");
		            		cell.setCellValue(sdf.format((Date) field.get(t)));
		            	}
		            }
		            field.setAccessible(false);
		        }  
	         }   
	       }
		try {
			workbook.write(out);
			out.flush();
			out.close();
			out = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
			if (out != null) 
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 导出黑白名单库里的客户列表到excel（兼容黑白名单库的导入模版）
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static <T>  void createCustListExcel(String path, String exlType,String[] headers,String[] classNames, List<T> list ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Workbook workbook;
		if (exlType.equalsIgnoreCase("xlsx")) {
			// 2007以上
			workbook = new SXSSFWorkbook(200);
		} else {
			// 97-2003
			workbook = new HSSFWorkbook();
		}
		Sheet sheet = workbook.createSheet("title");
		//默认20列
		sheet.setColumnWidth((short)0, 10* 256);
		sheet.setColumnWidth((short)1, 10* 256);
		sheet.setColumnWidth((short)2, 10* 256);
		sheet.setColumnWidth((short)3, 10* 256);
		sheet.setColumnWidth((short)4, 10* 256);
		sheet.setColumnWidth((short)5, 10* 256);
		sheet.setColumnWidth((short)6, 10* 256);
		sheet.setColumnWidth((short)7, 10* 256);
		sheet.setColumnWidth((short)8, 10* 256);
		sheet.setColumnWidth((short)9, 10* 256);
		sheet.setColumnWidth((short)10, 10* 256);
		sheet.setColumnWidth((short)11, 10* 256);
		sheet.setColumnWidth((short)12, 10* 256);
		sheet.setColumnWidth((short)13, 10* 256);
		sheet.setColumnWidth((short)14, 10* 256);
		sheet.setColumnWidth((short)15, 10* 256);
		sheet.setColumnWidth((short)16, 10* 256);
		sheet.setColumnWidth((short)17, 10* 256);
		sheet.setColumnWidth((short)18, 10* 256);
		sheet.setColumnWidth((short)19, 10* 256);
		
		CellStyle cellStyle = workbook.createCellStyle(); 
		CellStyle cellStyle1 = workbook.createCellStyle(); 
		
		Font font = workbook.createFont(); 
		Font font1 = workbook.createFont();
		
		//微软雅黑,字体10,加粗，背景灰色
		font.setFontName("微软雅黑"); 
		font.setFontHeight((short) 240); 
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());  
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setWrapText(true); 
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN); 
		cellStyle.setBorderRight(CellStyle.BORDER_THIN); 
		cellStyle.setBorderTop(CellStyle.BORDER_THIN); 
				
		//微软雅黑,字体10,加粗，背景灰色
		font1.setFontName("微软雅黑"); 
		font1.setFontHeight((short) 240); 
		cellStyle1.setFont(font1);
		cellStyle1.setAlignment(CellStyle.ALIGN_LEFT); // 指定单元格居中对齐 
		cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle1.setWrapText(true); 
		cellStyle1.setBorderBottom(CellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyle1.setBorderLeft(CellStyle.BORDER_THIN); 
		cellStyle1.setBorderRight(CellStyle.BORDER_THIN); 
		cellStyle1.setBorderTop(CellStyle.BORDER_THIN); 
		
		Row row = sheet.createRow(0);
		row.setHeight((short) 400);
		
		for (short i = 0; i < headers.length; i++) {
	         Cell cell = row.createCell(i);
	         cell.setCellStyle(cellStyle);
	         cell.setCellValue(headers[i]);
        }
		Iterator<T> it = list.iterator();
	    int index = 0;
	    while (it.hasNext()) {
	         index++;
	         row = sheet.createRow(index);
	         row.setHeight((short) 400);
	         T t = it.next();
	       if(null!=t){
	          for (int j = 0; j < headers.length; j++) { 
		         Cell cell = row.createCell(j);
		         Field field = t.getClass().getDeclaredField(classNames[j]);
		         field.setAccessible(true);
		         Class valType = field.getType();
		         cell.setCellStyle(cellStyle1);
		            //数据转类型
		            if("java.lang.String".equalsIgnoreCase( valType.getName())){
		            	cell.setCellValue((String) field.get(t));
		            }else if("java.lang.Integer".equalsIgnoreCase(valType.getName())||"int".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Integer) field.get(t));
		            }else if("java.lang.Double".equalsIgnoreCase(valType.getName())||"double".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Double) field.get(t)==null?0:(Double) field.get(t));
		            }else if("java.lang.Long".equalsIgnoreCase(valType.getName())||"long".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Long) field.get(t)==null?0:(Long) field.get(t));
		            }else if("java.util.Date".equalsIgnoreCase(valType.getName())) {
		            	if(null==field.get(t)){
		            		cell.setCellValue("");
		            	}else {
		            		SimpleDateFormat sdf   = new SimpleDateFormat("yyyy/MM/dd");
		            		cell.setCellValue(sdf.format((Date) field.get(t)));
		            	}
		            }
		            field.setAccessible(false);
		        }  
	         }   
	       }
		try {
			FileOutputStream output=new FileOutputStream(path); 
			workbook.write(output);
			output.close();
			output = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	/**
	 * 导出引擎批量测试结果到excel
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static <T>  void exportEngineTestResultExcel(OutputStream  out ,String exlType,String[] headers,String[] classNames, List<T> list ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Workbook workbook;
		if (exlType.equalsIgnoreCase("xlsx")) {
			// 2007以上
			workbook = new SXSSFWorkbook(200);
		} else {
			// 97-2003
			workbook = new HSSFWorkbook();
		}
		Sheet sheet = workbook.createSheet("title");

		sheet.setColumnWidth((short)0, 10* 256);
		sheet.setColumnWidth((short)1, 15* 256);
		sheet.setColumnWidth((short)2, 15* 256);
		sheet.setColumnWidth((short)3, 15* 256);
		sheet.setColumnWidth((short)4, 15* 256);
		sheet.setColumnWidth((short)5, 15* 256);
		sheet.setColumnWidth((short)6, 20* 256);
		sheet.setColumnWidth((short)7, 20* 256);
		
		CellStyle cellStyle = workbook.createCellStyle(); 
		CellStyle cellStyle1 = workbook.createCellStyle(); 
		
		Font font = workbook.createFont(); 
		Font font1 = workbook.createFont();
		
		//微软雅黑,字体10,加粗，背景灰色
		font.setFontName("微软雅黑"); 
		font.setFontHeight((short) 240); 
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());  
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setWrapText(true); 
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN); 
		cellStyle.setBorderRight(CellStyle.BORDER_THIN); 
		cellStyle.setBorderTop(CellStyle.BORDER_THIN); 
				
		//微软雅黑,字体10,加粗，背景灰色
		font1.setFontName("微软雅黑"); 
		font1.setFontHeight((short) 240); 
		cellStyle1.setFont(font1);
		cellStyle1.setAlignment(CellStyle.ALIGN_LEFT); // 指定单元格居中对齐 
		cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle1.setWrapText(true); 
		cellStyle1.setBorderBottom(CellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyle1.setBorderLeft(CellStyle.BORDER_THIN); 
		cellStyle1.setBorderRight(CellStyle.BORDER_THIN); 
		cellStyle1.setBorderTop(CellStyle.BORDER_THIN); 
		
		Row row = sheet.createRow(0);
		row.setHeight((short) 400);
		
		for (short i = 0; i < headers.length; i++) {
	         Cell cell = row.createCell(i);
	         cell.setCellStyle(cellStyle);
	         cell.setCellValue(headers[i]);
        }
		Iterator<T> it = list.iterator();
	    int index = 0;
	    while (it.hasNext()) {
	         index++;
	         row = sheet.createRow(index);
	         row.setHeight((short) 400);
	         T t = it.next();
	       if(null!=t){
	          for (int j = 0; j < headers.length; j++) { 
		         Cell cell = row.createCell(j);
		         Field field = t.getClass().getDeclaredField(classNames[j]);
		         field.setAccessible(true);   	
		         Class valType = field.getType();
		         cell.setCellStyle(cellStyle1);
		            //数据转类型
		            if("java.lang.String".equalsIgnoreCase( valType.getName())){
		            	cell.setCellValue((String) field.get(t));
		            }else if("java.lang.Integer".equalsIgnoreCase(valType.getName())||"int".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Integer) field.get(t));
		            }else if("java.lang.Double".equalsIgnoreCase(valType.getName())||"double".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Double) field.get(t)==null?0:(Double) field.get(t));
		            }else if("java.lang.Long".equalsIgnoreCase(valType.getName())||"long".equalsIgnoreCase(valType.getName())){
		            	cell.setCellValue((Long) field.get(t)==null?0:(Long) field.get(t));
		            }else if("java.util.Date".equalsIgnoreCase(valType.getName())) {
		            	if(null==field.get(t)){
		            		cell.setCellValue("");
		            	}else {
		            		SimpleDateFormat sdf   = new SimpleDateFormat("yyyy/MM/dd");
		            		cell.setCellValue(sdf.format((Date) field.get(t)));
		            	}
		            }
		            field.setAccessible(false);
		        }  
	         }   
	       }
		try {
			workbook.write(out);
			out.flush();
			out.close();
			out = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
			if (out != null) 
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static  String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}
	
	/**
	 * 判断合并行
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean isMergedRow(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row == firstRow && row == lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断合并列
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean isMergedCol(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if(column== firstColumn && column==lastColumn){
				if (row>=firstRow&& row<=lastRow){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	public static  boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			return cell.getStringCellValue();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

			return String.valueOf(cell.getNumericCellValue());
			
		}
		return "";
	}
	
}
