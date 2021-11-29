package com.risk.riskmanage.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.risk.riskmanage.common.model.ExcelModel;
import com.risk.riskmanage.common.model.ExcelSheetModel;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ClassName:ExcelUtil</br>
 * Description: Excel实用类
 */
@SuppressWarnings("rawtypes")
public class ExcelUtil {

    /**
     * exportExcel:导出excel
     *
     * @param out     输出流
     * @param exlType 导出格式
     * @param headers 表头信息
     * @param list    要导出的数据
     * @return cell
     *
     */
    public static <T> void exportExcel(OutputStream out, String exlType, String[] headers, String[] classNames, List<T> list) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Workbook workbook;
        if (exlType.equalsIgnoreCase("xlsx")) {
            // 2007以上
            workbook = new SXSSFWorkbook(200);
        } else {
            // 97-2003
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = workbook.createSheet("title");
        for (short i = 0; i < headers.length; i++) {
            if (i == headers.length - 1 || i == headers.length - 2) {
                sheet.setColumnWidth(i, 50 * 256);
            } else {
                sheet.setColumnWidth(i, 25 * 256);
            }
        }

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
            row.setHeight((short) 800);
            T t = it.next();
            if (null != t) {
                for (int j = 0; j < headers.length; j++) {
                    Cell cell = row.createCell(j);
                    Field field = t.getClass().getDeclaredField(classNames[j]);
                    field.setAccessible(true);
                    Class valType = field.getType();

                    cell.setCellStyle(cellStyle1);
                    if ("java.lang.String"
                            .equalsIgnoreCase(valType.getName())) {
                        cell.setCellValue((String) field.get(t));
                    } else if ("java.lang.Integer"
                            .equalsIgnoreCase(valType.getName())
                            || "int".equalsIgnoreCase(valType.getName())) {
                        cell.setCellValue((Integer) field.get(t));
                    } else if ("java.lang.Double"
                            .equalsIgnoreCase(valType.getName())
                            || "double".equalsIgnoreCase(valType.getName())) {
                        cell.setCellValue((Double) field.get(t) == null
                                ? 0
                                : (Double) field.get(t));
                    } else if ("java.lang.Long"
                            .equalsIgnoreCase(valType.getName())
                            || "long".equalsIgnoreCase(valType.getName())) {
                        cell.setCellValue((Long) field.get(t) == null
                                ? 0
                                : (Long) field.get(t));
                    } else if ("java.util.Date"
                            .equalsIgnoreCase(valType.getName())) {
                        if (null == field.get(t)) {
                            cell.setCellValue("");
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat(
                                    "yyyy/MM/dd");
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
     * 处理单元格格式
     *
     * @param cell
     * @return
     *
     */
    public static String formatCell(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                //日期格式的处理
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                }

                return String.valueOf(cell.getNumericCellValue());

            //字符串
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            // 公式
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();

            // 空白
            case Cell.CELL_TYPE_BLANK:
                return "";

            // 布尔取值
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";

            //错误类型
            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }

        return "";
    }


    public static void exportExcelTemplate(OutputStream out, ExcelModel model) throws SecurityException,  IllegalArgumentException {
        Workbook workbook;
        if ("xlsx".equalsIgnoreCase(model.getType())) {
            // 2007以上
            workbook = new XSSFWorkbook();
        } else {
            // 97-2003
            workbook = new HSSFWorkbook();
        }
        List<ExcelSheetModel> sheets = model.getSheets();
        if (sheets == null && sheets.size() == 0) {
            return;
        }
        for (ExcelSheetModel info : sheets) {
            Sheet sheet = workbook.createSheet(info.getSheetName());
            int headerSize = info.getHeaders().size();
            for (short i = 0; i < headerSize; i++) {
                if (i == headerSize - 1 || i == headerSize - 2) {
                    sheet.setColumnWidth(i, 50 * 256);
                } else {
                    sheet.setColumnWidth(i, 25 * 256);
                }
            }
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

            for (short i = 0; i < headerSize; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(info.getHeaders().get(i));
            }
            List<List> data = info.getData();
            for (int i = 0; i < data.size(); i++) {
                List oneRow = data.get(i);
                row = sheet.createRow(i+1);
                for (int j = 0; j < oneRow.size(); j++) {
                    Object cellData = oneRow.get(j);
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(cellStyle1);
                    cell.setCellValue(cellData.toString());
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
    }
}