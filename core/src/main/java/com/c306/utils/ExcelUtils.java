package com.c306.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 配置文件的查找
 * 
 * @author cs
 * 
 */
public class ExcelUtils {
	
	/**
	 * 转换Excel, 第一行为 title
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> convert2List(File xls) throws IOException {
		List<String[]> list = new ArrayList<String[]>();
		//得到工作表
		HSSFWorkbook book = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(xls)));
		//得到第一页
		HSSFSheet sheet = book.getSheetAt(0);
		//得到第一页的所有行
//		int rows = sheet.getPhysicalNumberOfRows();
		int rows = sheet.getLastRowNum() + 1;
		HSSFRow row = sheet.getRow(0);
//		int cols = row.getPhysicalNumberOfCells();
		int cols = row.getLastCellNum() + 1;
		
		for (int i = 1; i <= rows; i++) {
			String [] line = new String[cols];
			for (int j = 0; j<cols; j++) {
				line[j] = getCellvalue(row.getCell(j));
			}
			list.add(line);
			//
			row = sheet.getRow(i);
		}
		return list;
	}
	/**
	 * 转换Excel, 第一行为 title
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> convert2List(InputStream in) throws IOException {
		List<String[]> list = new ArrayList<String[]>();
		//得到工作表
		HSSFWorkbook book = new HSSFWorkbook(new POIFSFileSystem(in));
		//得到第一页
		HSSFSheet sheet = book.getSheetAt(0);
		//得到第一页的所有行
//		int rows = sheet.getPhysicalNumberOfRows();
		int rows = sheet.getLastRowNum() + 1;
		HSSFRow row = sheet.getRow(0);
//		int cols = row.getPhysicalNumberOfCells();
		int cols = row.getLastCellNum() + 1;
		
		for (int i = 1; i <= rows; i++) {
			String [] line = new String[cols];
			for (int j = 0; j<cols; j++) {
				line[j] = getCellvalue(row.getCell(j));
			}
			list.add(line);
			//
			row = sheet.getRow(i);
		}
		return list;
	}
	/**
	 * 
	 * @param sheet
	 * @param r
	 * @param c
	 * @return
	 */
	public static HSSFCell findCell(HSSFSheet sheet, int r, int c) {
		try {
			return sheet.getRow(r).getCell(c);
		} catch (Exception e) {
			System.out.println(e.getMessage()+"； 行："+r+", 列："+c);
		}
		return null;
	}
	/**
	 * 取cell中值
	 * @return string
	 */
	public static String getCellvalue(HSSFCell cell) {
		if(cell==null){
			return "";
		}
		String cellValue = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			cellValue = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue.trim();
	}
	/**
	 * 取cell中值
	 * @return string
	 */
	public static String getCellStringValue(HSSFCell cell) {
		if(cell==null){
			return "";
		}
		try {
			return cell.getRichStringCellValue().getString().trim();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 取cell中值
	 * @return long
	 */
	public static long getCellLongValue(HSSFCell cell) {
		if(cell==null){
			return 0l;
		}
		try {
			String str = getCellvalue(cell);
			return new Double(str).longValue();
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	/**
	 * 取cell中值
	 * @return int
	 */
	public static int getCellIntValue(HSSFCell cell) {
		if(cell==null){
			return 0;
		}
		try {
			String str = getCellvalue(cell);
			return new Double(str).intValue();
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	/**
	 * 取cell中值
	 * @return string
	 */
	public static float getCellFloatValue(HSSFCell cell) {
		if(cell==null){
			return 0;
		}
		try {
			String str = getCellvalue(cell);
			return new Double(str).floatValue();
		} catch (NumberFormatException e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return 0;
	}
	/**
	 * Money
	 * @param cell
	 * @return
	 */
	public static double getCellMoneyValue(HSSFCell cell) {
		if(cell==null){
			return 0;
		}
		try {
			String str = getCellvalue(cell);
			if(!"".equals(str))
				return new Double(str.replace(",", "")).doubleValue();
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	public static void setCellType(HSSFCell cell,Class clazzType){
		if(clazzType.equals(Byte.TYPE)
				||clazzType.equals(Short.TYPE)
				||clazzType.equals(Integer.TYPE)
				||clazzType.equals(Long.TYPE)
				||clazzType.equals(Float.TYPE)
				||clazzType.equals(Double.TYPE)
//				||clazzType.equals(Boolean.TYPE)
//				||clazzType.equals(String.class)
//				||clazzType.equals(Date.class)
		){
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		}
		else{
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
	}
	
	/**
	 * 导出Excel
	 * @param data List VO list 
	 * @param map  Map key=fieldName, value=columnName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static byte[] exportToExcel(Collection data, Map headMap) throws Exception {
		String[] fields = (String[]) headMap.keySet().toArray(new String[] {});
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		HSSFCellStyle cellStyle1 = wb.createCellStyle();
		cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//		cellStyle1.setBorderBottom(HSSFColor.GREY_25_PERCENT.index);
//		cellStyle1.setBorderRight(HSSFColor.GREY_25_PERCENT.index);
//		cellStyle1.setBorderLeft(HSSFColor.GREY_25_PERCENT.index);
//		cellStyle1.setBorderTop(HSSFColor.GREY_25_PERCENT.index);
		cellStyle1.setWrapText(false);//
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 14); // 字体高度
		font.setColor(HSSFColor.BLACK.index); // 字体颜色
		font.setFontName("宋体"); // 字体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		font.setItalic(false); // 是否使用斜体
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 10); // 字体高度
		font1.setColor(HSSFColor.BLUE.index); // 字体颜色
		font1.setFontName("Arial"); // 字体
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		font1.setItalic(true); // 是否使用斜体
		
		//表头
		HSSFRow rowhead = sheet.createRow(0);
		HSSFCell cell = rowhead.createCell(0);
		for (int j = 0; j < headMap.size(); j++) {
			cell = rowhead.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			HSSFRichTextString rstr = new HSSFRichTextString(headMap.get(fields[j]).toString());
			cell.setCellValue(rstr);
//			cell.setCellStyle(cellStyle1);
			sheet.autoSizeColumn((short)j);
			sheet.setColumnWidth(j, (int)(35.7*160));
		}
		
		String [] methodNames = new String[fields.length];
		for (int j = 0; j < fields.length; j++) {
			methodNames[j] = "get" + fields[j].substring(0, 1).toUpperCase() + fields[j].substring(1);
		}
		
		Iterator iter = data.iterator();
		//内容
		Object vo = null;
		HSSFRow row = null;
		Class clazz = null;
		for (int i = 1;iter.hasNext();i++) {
			vo = iter.next();
			clazz = vo.getClass();
			row = sheet.createRow(i);
			for (int j = 0; j < headMap.size(); j++) {
				Method m = clazz.getMethod(methodNames[j]);
				cell = row.createCell(j);
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				ExcelUtils.setCellType(cell, m.getReturnType());
				Object objvalue = m.invoke(vo, new Object[]{});
				HSSFRichTextString rstr = null;
				if(objvalue!=null){
					rstr = new HSSFRichTextString(objvalue.toString());
				}else{
					rstr = new HSSFRichTextString("");
				}
				cell.setCellValue(rstr);
			}
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		byte [] 
		rs = out.toByteArray();
		return rs;
//		return wb.getBytes();
	}
	/**
	 * 导出Excel
	 * @param data List VO list 
	 * @param map  Map key=fieldName, value=columnName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static byte[] exportToExcel(Collection data, String [] fields) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		String [] methodNames = new String[fields.length];
		for (int j = 0; j < fields.length; j++) {
			methodNames[j] = "get" + fields[j].substring(0, 1).toUpperCase() + fields[j].substring(1);
		}

		Iterator iter = data.iterator();
		//内容
		Object vo = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		Class clazz = null;
		for (int i = 0;iter.hasNext();i++) {
			row = sheet.createRow(i);
			for (int j = 0; j < fields.length; j++) {
				if(!iter.hasNext())
					break;
				vo = iter.next();
				clazz = vo.getClass();
				Method m = clazz.getMethod(methodNames[j]);
				cell = row.createCell(j);
				ExcelUtils.setCellType(cell, m.getReturnType());
				Object objvalue = m.invoke(vo, new Object[]{});
				HSSFRichTextString rstr = null;
				if(objvalue!=null){
					rstr = new HSSFRichTextString(objvalue.toString());
				}else{
					rstr = new HSSFRichTextString("");
				}
				cell.setCellValue(rstr);
			}
		}

		for (int j = 0; j < fields.length; j++) {
			sheet.setColumnWidth(j, (int)(35.7*200));
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		byte [] rs = out.toByteArray();
		return rs;
	}
	

	public static HSSFWorkbook convert2Excel(List<String[]> data) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		HSSFCellStyle cellStyle1 = wb.createCellStyle();
		cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle1.setWrapText(false);//
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 14); // 字体高度
		font.setColor(HSSFColor.BLACK.index); // 字体颜色
		font.setFontName("宋体"); // 字体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		font.setItalic(false); // 是否使用斜体
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 10); // 字体高度
		font1.setColor(HSSFColor.BLUE.index); // 字体颜色
		font1.setFontName("Arial"); // 字体
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		font1.setItalic(true); // 是否使用斜体
		
		HSSFRow rowhead = sheet.createRow(0);
		HSSFCell cell = rowhead.createCell(0);
		//内容
		for (int i = 0; i<data.size(); i++) {
			String [] strs = data.get(i);
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < strs.length; j++) {
				cell = row.createCell( j);
				//ExcelUtils.setCellType(cell, String.class);
				HSSFRichTextString rstr = null;
				if(strs[j]!=null){
					rstr = new HSSFRichTextString(strs[j]);
				}else{
					rstr = new HSSFRichTextString("");
				}
				cell.setCellValue(rstr);
			}
		}
		
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		wb.write(out);
//		byte [] 
//		rs = out.toByteArray();
//		return rs;
		return wb;
	}
}
