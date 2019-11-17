package org.openmore.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Description: Excel解析操作
 * CreateTime: 2017年12月11日  下午3:08:09
 * Change History:
 * Date             CR Number              Name              Description of change
 */
public class ParseExcelUtil {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param in
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) {  //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     *
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     * <p>
     * example excel:
     * -----------------------------------------------------------------------------------------------------------------
     * 序号	\   地址	            \   面积（平方）\	层数	\   管理单位	\   联系方式	\备注
     * -----------------------------------------------------------------------------------------------------------------
     * 1	\1号院1号楼1单元1层102	\34.00	        \3	        \风雨i花园	    \18911111111	\这是备注
     * 2	\1号院1号楼2单元1层102	\56.00	        \3	        \北京穿么	    \13100000000	\vd
     * 3			                                \23	        \国际化	        \13011111111	\的发射点
     * 4		                    \12.00	        \1	        \而非我	        \13011111111	\发生的
     * -----------------------------------------------------------------------------------------------------------------
     * <p>
     * example result:
     * 总列数：7
     * 最大列数：7
     * -----------------------------------------------------------------------------------------------------------------
     * 1	1号院1号楼1单元1层102	34.00	3	风雨i花园	18911111111	这是备注	总列数：7
     * 最大列数：7
     * 2	1号院1号楼2单元1层102	56.00	3	北京穿么	13100000000	vd	总列数：7
     * 最大列数：7
     * 3			23	国际化	13011111111	的发射点	总列数：7
     * 最大列数：7
     * 4		12.00	1	而非我	13011111111	发生的
     * ------------------------------------------------------------------------------------------------------------------
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 同时支持Excel 2003、2007  
            File excelFile = new File("C:\\Users\\psp\\Desktop\\地下空间 .xlsx"); // 创建文件对象
            FileInputStream in = new FileInputStream(excelFile); // 文件流  
            checkExcelVaild(excelFile);
            Workbook workbook = getWorkbok(in, excelFile);
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的  

            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量  
            /**
             * 设置当前excel中sheet的下标：0开始 
             */
            //Sheet sheet = workbook.getSheetAt(0);   // 遍历第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);   // 遍历第三个Sheet

            //获取总行数
//          System.out.println(sheet.getLastRowNum());

            // 为跳过第一行目录设置count  
            int count = 0;
            for (Row row : sheet) {
                try {
                    // 跳过第一行的目录
                    if (count < 1) {
                        count++;
                        continue;
                    }

                    //如果当前行没有数据，跳出循环  
                    if (row.getCell(0).toString().equals("")) {
                        return;
                    }

                    //获取总列数(空格的不计算)
                    int columnTotalNum = row.getPhysicalNumberOfCells();
                    System.out.println("总列数：" + columnTotalNum);

                    System.out.println("最大列数：" + row.getLastCellNum());

                    //for循环的，不扫描空格的列
                    //for (Cell cell : row) {
//                    	System.out.println(cell);
//                    }
                    int end = row.getLastCellNum();
                    for (int i = 0; i < end; i++) {
                        Cell cell = row.getCell(i);
                        if (cell == null) {
                            System.out.print("null" + "\t");
                            continue;
                        }

                        Object obj = getValue(cell);
                        System.out.print(obj + "\t");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }
}
