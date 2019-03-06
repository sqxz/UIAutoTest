package com.webautotest.utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelDataProvider implements Iterator<Object[]> {
    public String path = null;
    public InputStream inputStream = null;
    public Workbook book = null;
    public Sheet sheet = null;
    public int columnNum = 0;
    public int rowNum = 0;
    public String[] columnnName;
    public int currentRowNo = 0; //当前行
    public static Logger logger = Logger.getLogger(ExcelDataProvider.class.getName());

    public ExcelDataProvider(String moduleName, String casenum){
        try {
            path = "data/" + moduleName + ".xls";
            inputStream = new FileInputStream(path);
            book = Workbook.getWorkbook(inputStream); //读取excel文件对象
            sheet  = book.getSheet(casenum);//根据用户编号读取对应的sheet对象
            rowNum = sheet.getRows();
            Cell[] cells = sheet.getRow(0); //获取第一行的单元格数
            columnNum = cells.length;  //将单元格的长度赋值给列，即为列数
            columnnName = new String[columnNum]; //创建一个数组，用来保存第一行的列名

            for (int i = 0; i < cells.length; i++) {
                columnnName[i] = cells[i].getContents().toString();//分别将第一行的值转为字符串类型保存到列名的数组中
            }
            this.currentRowNo++;   //从第二行开始读取数据

        } catch (FileNotFoundException e) {
            logger.error("没有找到指定的文件："+path);
        } catch (Exception e) {
            logger.error("不能读取文件："+path);
        }

    }

    @Override
    public boolean hasNext() {
        int j = 0;
        if (this.rowNum == 0 || this.currentRowNo >= this.rowNum) {   //行数为0 或者当前行大于等于总行数
            try {
                inputStream.close();
                book.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }else {
            for (int i = 0; i < columnnName.length; i++) {
                if ((sheet.getRow(currentRowNo))[i].getContents() != "") {  //判断当前行的单元格项是否全部为空
                    j = j+1;
                }
            }
            if (j != 0 ) {
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public Object[] next() {
        Cell[] c = sheet.getRow(this.currentRowNo); //读取当前行的的所有单元格保存至一个数组中
        Map<String, String> data = new HashMap<String,String>();
        for (int i = 0; i < columnNum; i++) {
            String temp = "";
            try {
                temp = c[i].getContents().toString();  //转义某个单元格的数据成字符串
            } catch (ArrayIndexOutOfBoundsException e) {
                temp = "";
            }
            data.put(this.columnnName[i], temp);	//将列名和对应的单元格数据保存到Map键值对
        }
        Object object[] = new Object[1];  //实例化Object数组
        object[0] = data;   //将Map键值对保存在object数组中
        this.currentRowNo++;
        return object;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove unsupported.");
    }
}
