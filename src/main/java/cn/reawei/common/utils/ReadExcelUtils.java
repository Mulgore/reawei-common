package cn.reawei.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Created in 2018/5/11 11:27
 *
 * @author qigong
 */
public class ReadExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(ReadExcelUtils.class);
    private static Workbook wb;
    private static Sheet sheet;
    private static Row row;

    /**
     * 读取Excel表格表头的内容
     *
     * @return String 表头内容的数组
     */
    private static List<String> readExcelTitle() throws Exception {
        if (wb == null) {
            throw new Exception("Workbook对象为空！");
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        List<String> title = new ArrayList<>();
        row.cellIterator().forEachRemaining((item) -> title.add(item.getStringCellValue()));
        return title;
    }

    /**
     * 读取Excel数据内容
     *
     * @return Map 包含单元格数据内容的Map对象
     */
    private static Map<Integer, Map<Integer, Object>> readExcelContent() throws Exception {
        if (wb == null) {
            throw new Exception("Workbook对象为空！");
        }
        Map<Integer, Map<Integer, Object>> content = new HashMap<>();

        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<Integer, Object> cellValue = new HashMap<>();
            while (j < colNum) {
                Object obj = getCellFormatValue(row.getCell(j));
                cellValue.put(j, obj);
                j++;
            }
            content.put(i, cellValue);
        }
        return content;
    }

    /**
     * 根据Cell类型设置数据
     */
    private static Object getCellFormatValue(Cell cell) {
        Object cellValue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: // 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        // data格式是带时分秒的：2013-7-10 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();
                        // data格式是不带带时分秒的：2013-7-10
                        Date date = cell.getDateCellValue();
                        cellValue = date;
                    } else {// 如果是纯数字
                        // 取得当前Cell的数值
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    private static Map<Integer, Map<Integer, Object>> readExcelContent(String filePath, File file) throws Exception {
        try {
            if (Objects.nonNull(filePath) && filePath.length() > 0) {
                getWorKBook(filePath, null);
            }
            if (Objects.nonNull(file)) {
                getWorKBook(null, file);
            }
            return readExcelContent();
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        }
        return null;
    }

    private static List<String> readExcelTitle(String filePath, File file) throws Exception {
        try {
            if (Objects.nonNull(filePath) && filePath.length() > 0) {
                getWorKBook(filePath, null);
            }
            if (Objects.nonNull(file)) {
                getWorKBook(null, file);
            }
            return readExcelTitle();
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        }
        return null;
    }

    private static void getWorKBook(String filePath, File file) throws IOException {
        String suffix = null;
        InputStream is = null;
        if (Objects.nonNull(filePath) && filePath.length() > 0) {
            suffix = filePath.substring(filePath.lastIndexOf("."));
            is = new FileInputStream(filePath);
        }
        if (Objects.nonNull(file)) {
            String fileName = file.getName();
            suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            is = new FileInputStream(file);
        }
        if (Objects.isNull(suffix)) return;
        if (".xls".equals(suffix)) {
            wb = new HSSFWorkbook(is);
        } else if (".xlsx".equals(suffix)) {
            wb = new XSSFWorkbook(is);
        } else {
            wb = null;
        }
    }

    public static Map<Integer, Map<Integer, Object>> readExcelContent(String filePath) throws Exception {
        return readExcelContent(filePath, null);
    }

    public static Map<Integer, Map<Integer, Object>> readExcelContent(File file) throws Exception {
        return readExcelContent(null, file);
    }

    public static List<String> readExcelTitle(String filePath) throws Exception {
        return readExcelTitle(filePath, null);
    }

    public static List<String> readExcelTitle(File file) throws Exception {
        return readExcelTitle(null, file);
    }
}
