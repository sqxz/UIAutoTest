package com.webautotest.pagehelper;

import com.webautotest.pages.LoginPage;
import com.webautotest.utils.SeleniumUtil;
import org.apache.log4j.Logger;

public class LoginPageHelper {

    static SeleniumUtil seleniumUtil = new SeleniumUtil();
    static Logger logger = Logger.getLogger(LoginPageHelper.class.getName());
    /**
     * 执行登录
     */
    public static void doLogin(String userName,String password){
        seleniumUtil.inserText(LoginPage.LOGINPAGE_INPUT_LOGINNAME,userName);
        seleniumUtil.inserText(LoginPage.LOGINPAGE_INPUT_LOGINPWD,password);
        seleniumUtil.click(LoginPage.LOGINPAGE_A_SUBMITLOGIN);
        logger.info("执行登录");
    }
}
