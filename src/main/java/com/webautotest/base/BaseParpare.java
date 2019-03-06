package com.webautotest.base;

import com.webautotest.utils.ExcelDataProvider;
import com.webautotest.utils.ReadProperties;
import com.webautotest.utils.SeleniumUtil;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.util.Iterator;

public class BaseParpare {

    public static SeleniumUtil seleniumUtil = null;
    public String webUrl = "";
    public int timeOut = 0;
    public long waitMillisecondsForAlert = 0;

    @BeforeTest
    public void beforeTest(){
        seleniumUtil = new SeleniumUtil();
        webUrl = ReadProperties.getBundle("application").getString("webUrl");
        timeOut = Integer.parseInt(ReadProperties.getBundle("application").getString("timeOut"));
        seleniumUtil.launchBrowser(webUrl,5);

    }

    @AfterTest
    public void afterTest(){
        seleniumUtil.quit();
    }

    @DataProvider(name = "testdata",parallel=false)
    public Iterator<Object[]> dataForTestMethod() {
        String moduleName = null;  //模块名称
        String casenum = null;  //用例编号
        String className = this.getClass().getName();//当前测试类名称
        moduleName = className.substring(className.lastIndexOf(".")+1,className.indexOf("_"));
        casenum = className.substring(className.indexOf("_")+1);
        //将模块名称和用例的编号传给 ExcelDataProvider ，然后进行读取excel数据
        return new ExcelDataProvider(moduleName,casenum);
    }

}
