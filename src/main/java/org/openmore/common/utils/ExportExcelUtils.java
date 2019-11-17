package org.openmore.common.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import java.awt.Color;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

public class ExportExcelUtils {

    /**
     * 快速开始创建excel
     */
    public static void httpFastCreate(HttpServletResponse response, String fileName, String name, List<String> titles, List<List<Object>> rows) throws Exception {
        ExcelData data = new ExcelData();
        data.setName(name);
        data.setTitles(titles);
        data.setRows(rows);
        ExportExcelUtils.exportExcel(response, fileName, data);
    }

    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        exportExcel(data, response.getOutputStream());
    }

    public static void exportExcel(ExcelData data, OutputStream out) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.createSheet(sheetName);
            writeExcel(wb, sheet, data);

            wb.write(out);
        } finally {
            wb.close();
        }
    }

    private static void writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {

        int rowIndex = 0;

        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
        writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);
        autoSizeColumns(sheet, data.getTitles().size() + 1);

    }

    private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;

        Font titleFont = wb.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setFillForegroundColor(new XSSFColor(new Color(182, 184, 192)));
        titleStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        Row titleRow = sheet.createRow(rowIndex);
        // titleRow.setHeightInPoints(25);
        colIndex = 0;

        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }

        rowIndex++;
        return rowIndex;
    }

    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<List<Object>> rows, int rowIndex) {
        int colIndex = 0;

        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            // dataRow.setHeightInPoints(25);
            colIndex = 0;

            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    cell.setCellValue(cellData.toString());
                } else {
                    cell.setCellValue("");
                }

                cell.setCellStyle(dataStyle);
                colIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {

        for (int i = 0; i < columnNumber; i++) {
            /*int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }*/
            int colWidth = sheet.getColumnWidth(i) * 2;
            if (colWidth < 255 * 256) {
                sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
            } else {
                sheet.setColumnWidth(i, 6000);
            }
        }
    }

    public static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
    }

    /**
     * 设置某一列为文本模式
     */
    public static void setTextStyle(XSSFWorkbook wb, Sheet sheet, int colNum) {
        CellStyle textStyle = wb.createCellStyle();

        DataFormat format = wb.createDataFormat();

        textStyle.setDataFormat(format.getFormat("@"));

        sheet.setDefaultColumnStyle(colNum, textStyle);
    }


    /**
     * 获取某一行，没有则创建
     */
    public static XSSFRow getRow(XSSFSheet sheet, int index) {
        XSSFRow row = null;
        try {
            row = sheet.getRow(index);
            if (null == row) {
                row = sheet.createRow(index);
            }
        } catch (Exception e) {
            row = sheet.createRow(index);
        }
        return row;
    }

    /**
     * 获取某一列，没有则创建
     */
    public static XSSFCell getCell(XSSFRow row, int index) {
        XSSFCell cell = null;
        try {
            cell = row.getCell(index);
            if (null == cell) {
                cell = row.createCell(index);
            }
        } catch (Exception e) {
            cell = row.createCell(index);
        }
        return cell;
    }
}