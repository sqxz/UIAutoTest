package com.webautotest.pages;

import org.openqa.selenium.By;

public class LoginPage {

    public static By LOGINPAGE_INPUT_LOGINNAME = By.xpath("//input[contains(@name,'login_name')]");
    public static By LOGINPAGE_INPUT_LOGINPWD = By.xpath("//input[contains(@name,'login_pwd')]");
    public static By LOGINPAGE_A_SUBMITLOGIN = By.xpath("//a[contains(@class,'do_login')]");
}
