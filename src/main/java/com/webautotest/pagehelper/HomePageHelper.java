package com.webautotest.pagehelper;

import com.webautotest.pages.HomePage;
import com.webautotest.pages.LoginPage;
import com.webautotest.utils.SeleniumUtil;
import org.apache.log4j.Logger;

public class HomePageHelper {

   static SeleniumUtil seleniumUtil = new SeleniumUtil();
    static Logger logger = Logger.getLogger(HomePageHelper.class.getName());

    /**
     * 跳转到登录页面
     */
    public static void switchToLoginPage(){
        seleniumUtil.click(HomePage.HOMEPAGE_A_LOGIN);
        logger.info("跳转进入登录页面");
    }


}
