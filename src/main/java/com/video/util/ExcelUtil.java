package com.video.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-23
 * @Description:
 */
public class ExcelUtil {

    public static void generateExcel(HttpServletResponse response, String fileName) throws IOException {
        //创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建表单
        XSSFSheet sheet = genSheet(workbook, "testExcel");
        //创建表单样式
        XSSFCellStyle titleStyle = genTitleStyle(workbook);//创建标题样式
        XSSFCellStyle contextStyle = genContextStyle(workbook);//创建文本样式

        //创建Excel
        genExcel(sheet, titleStyle, contextStyle);


        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");

        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        //刷新缓冲
        response.flushBuffer();

        //将工作薄写入文件输出流中
        workbook.write(response.getOutputStream());
        //文本文件输出流，释放资源
    }

    public static void genExcel(XSSFSheet sheet,XSSFCellStyle titleStyle,XSSFCellStyle contextStyle) {


        //设置标题位置
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row
                0, //last row
                0, //first column
                2 //last column
        ));

        XSSFRow row = sheet.createRow(0);//创建第一行，为标题，index从0开始
        XSSFCell cell;
        cell = row.createCell(0);//创建一列
        cell.setCellValue("月卡");//标题
        cell.setCellStyle(titleStyle);//设置标题样式
        row = sheet.createRow(1);//创建副标题
        for(int i = 0; i<3;i++) {
            cell = row.createCell(i);//创建列
            cell.setCellValue("姓名");//
            cell.setCellStyle(contextStyle);//设置样式
        }

		/*
			实际填充数据的时候，对可能为空的数据要进行处理，要先进行判断，否则报错
			String value = Test.getVal();
			if(StringUtils.isNotBlank(value)) {
				cell.setCellValue(value);
			} else{
			    cell.setCellValue(" ");
			}
		*/
        //从数据库取数据填充到Excel，这步省略，添加模拟数据
        for(int i = 2 ; i<15;i++){//i从2开始计数，因为上面已经创建了 0 1行
            row = sheet.createRow(i);//创建行
            for(int j = 0; j<3;j++) {
                cell = row.createCell(j);//创建列
                cell.setCellValue("姓名");//
                cell.setCellStyle(contextStyle);//设置样式
            }
            }
    }

    //设置表单，并生成表单
    public static XSSFSheet genSheet(XSSFWorkbook workbook, String sheetName){
        //生成表单
        XSSFSheet sheet = workbook.createSheet(sheetName);
        //设置表单文本居中
        sheet.setHorizontallyCenter(true);
        sheet.setFitToPage(false);
        //打印时在底部右边显示文本页信息
        Footer footer = sheet.getFooter();
        footer.setRight( "Page " + HeaderFooter.numPages()+ " Of "+ HeaderFooter.page());
        //打印时在头部右边显示Excel创建日期信息
        Header header = sheet.getHeader();
        header.setRight("Create Date " + HeaderFooter.date() + " " + HeaderFooter.time());
        //设置打印方式
        XSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(true); // true：横向打印，false：竖向打印 ，因为列数较多，推荐在打印时横向打印
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //打印尺寸大小设置为A4纸大小
        return sheet;
    }

    //创建文本样式
    public static XSSFCellStyle genContextStyle(XSSFWorkbook workbook){
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);//文本水平居中显示
        style.setVerticalAlignment(VerticalAlignment.CENTER);//文本竖直居中显示
        style.setWrapText(true);//文本自动换行

        //生成Excel表单，需要给文本添加边框样式和颜色
        /*
             CellStyle.BORDER_DOUBLE      双边线
             CellStyle.BORDER_THIN        细边线
             CellStyle.BORDER_MEDIUM      中等边线
             CellStyle.BORDER_DASHED      虚线边线
             CellStyle.BORDER_HAIR        小圆点虚线边线
             CellStyle.BORDER_THICK       粗边线
         */
        style.setBorderBottom(BorderStyle.THIN);//设置文本边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        return style;
    }

    //生成标题样式
    public static XSSFCellStyle genTitleStyle(XSSFWorkbook workbook){

        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);

        //标题居中，没有边框，所以这里没有设置边框，设置标题文字样式
        XSSFFont titleFont = workbook.createFont();
        titleFont.setBold(true);//加粗
        titleFont.setFontHeight((short)15);//文字尺寸
        titleFont.setFontHeightInPoints((short)15);
        style.setFont(titleFont);

        return style;
    }
}