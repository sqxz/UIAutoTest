package com.webautotest.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class SelectBrowser {
    public WebDriver getDriver() {

        String browserName =  ReadProperties.getBundle("application").getString("browserName");

        if (browserName.equalsIgnoreCase("chrome")) {

            String chromedriverPath = ReadProperties.getBundle("driver").getString("chrome");
            System.setProperty("webdriver.chrome.driver", chromedriverPath);
            return new ChromeDriver();

        }else if (browserName.equalsIgnoreCase("firefox")) {

            String firefoxdriverPath = ReadProperties.getBundle("driver").getString("firefox");
            System.setProperty("webdriver.gecko.driver", firefoxdriverPath);
            return new FirefoxDriver();

        }else if (browserName.equalsIgnoreCase("ie")) { //ie启动要修改注册表，IEdriver需要使用32位的，internet选项关闭保护模式

            String iedriverPath =ReadProperties.getBundle("driver").getString("ie");
            System.setProperty("webdriver.ie.driver", iedriverPath);
            DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
            dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            dc.setCapability("ignoreProtectedModeSettings", true);
            return new InternetExplorerDriver();
        }

        else {

            return null;
        }
    }
}
