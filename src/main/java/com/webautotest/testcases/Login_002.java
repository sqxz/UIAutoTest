package com.webautotest.testcases;

import com.webautotest.base.BaseParpare;
import com.webautotest.pagehelper.HomePageHelper;
import com.webautotest.pagehelper.LoginPageHelper;
import com.webautotest.pages.ClubHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class Login_002 extends BaseParpare {
    @Test(dataProvider = "testdata")

    public void loginSuccess(Map<String,String> data){
        HomePageHelper.switchToLoginPage();
        LoginPageHelper.doLogin(data.get("userName"),data.get("password"));
        seleniumUtil.pause(3);
        Assert.assertEquals(data.get("expected"), seleniumUtil.getText(ClubHomePage.CLUBHOMEPAGE_H2_VERSION));
    }

}
