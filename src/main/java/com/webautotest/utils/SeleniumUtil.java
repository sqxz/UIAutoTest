package com.webautotest.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


/**
 * @author shenzhenghuan
 * @description 封装selenium的api方法，简化用例中的代码量
 */
public class SeleniumUtil {
	static Logger logger = Logger.getLogger(SeleniumUtil.class.getName());
	public static WebDriver driver = null;

	
	/***
	 * 启动浏览器并打开页面
	 * */
	public void launchBrowser(String webUrl,int timeOut) {
		logger.info("开始加载浏览器了");
		SelectBrowser select = new SelectBrowser();
		driver = select.getDriver();
		try {
			maxWindow();
			waitForPageLoading(timeOut);
			get(webUrl);
		} catch (TimeoutException e) {
			logger.warn("注意：页面没有完全加载出来，刷新重试！！");
			refresh();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			String status= (String)js.executeScript("return document.readyState");		
			logger.info("打印状态："+status);
		}

	}


	/**
	 * 页面刷新
	 */
	public void refresh() {
		driver.navigate().refresh();
		logger.info("页面刷新成功");	
	}
	
	/**
	 * 后退方法包装
	 * */
	public void back() {
		driver.navigate().back();
		logger.info("返回上一页");
	}

	/**
	 * 前进方法包装
	 * */
	public void forward() {
		driver.navigate().forward();
	}


	/**
	 * 打开测试页面
	 * @param webUrl
	 */
	public void get(String webUrl) {
		driver.get(webUrl);
		logger.info("打开测试页面：" + webUrl);
	}

	/**
	 * 最大化浏览器窗口

	 */
	public void maxWindow() {
		driver.manage().window().maximize();
		logger.info("浏览器最大化成功");
		
	}
	
	public void quit() {
		driver.quit();
		logger.info("浏览器退出成功");
	}
	
	/**
	 * input中插入数据
	 * @param element
	 * @param text
	 */
	public void inserText(By element,String text) {
		driver.findElement(element).sendKeys(text);
		logger.info("向元素："+element+"中填入数据"+text);
	}

	public void click(By ButtonLogin) {
		driver.findElement(ButtonLogin).click();	
		logger.info("点击元素："+ButtonLogin);
	}
	
	/**
	 * 执行button的click事件，达到点击效果
	 * @param ButtonClick
	 */
	public void clickByJs(WebElement ButtonClick) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",ButtonClick);
		logger.info("执行元素："+ ButtonClick +"click事件");
	}
	
	
	public void click(WebElement element) {
		element.click();	
		logger.info("点击元素："+element);
	}

	public void clear(By inputElement) {
		driver.findElement(inputElement).clear();
		logger.info("清空输入框元素：" + inputElement);
	}
	
	
	/**
	 * 判断实际文本和期望文本是否相同
	 * @return
	 */
	public void isTextCorrect(String actual, String expect) {
		try {
			Assert.assertEquals(actual, expect);
		} catch (AssertionError e) {
			logger.error("期望的文字是：" + expect +";实际值是：" + actual);
			Assert.fail("期望的文字是：" + expect +";实际值是：" + actual);   //实际值和期望值不符，用例执行失败
		}
		logger.info("找到了期望的文字: [" + expect + "]");		
	}
	
	/**
	 * 获得元素文本
	 * @return
	 */
	public String getText(By by) {
		return driver.findElement(by).getText().trim();
	}
	
	
	public String getText(WebElement element) {
		return element.getText().trim();
	}
	
	/**
	 * 暂停等待
	 * @param seconds
	 */
	public void pause(int seconds) {
		logger.info("线程等待时间：" + seconds + "秒");
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行JavaScript 方法
	 * */
	public void executeJS(String js) {
		((JavascriptExecutor) driver).executeScript(js);
		logger.info("执行JavaScript语句：[" + js + "]");
	}
	
	/**
	 * 关闭alert弹框
	 */
	public void closeAlert() {		
		driver.switchTo().alert().accept();
		logger.info("关闭alert弹窗");
	}
	
	/**
	 * 获取alert提示文本
	 */
	public String getAlertText() {		
		String text = driver.switchTo().alert().getText();
		logger.info("获得alert弹窗文本:" + text);
		return text;		
	}
	
	
	/**
	 * 根据iframe在当前页面的顺序进行定位
	 * @param index  
	 */
	public void inframe(int index) {
		driver.switchTo().frame(index);
	}
	
	
	/**
	 * 根据iframe的name或者id进行定位
	 * @param nameOrId
	 */
	public void inframe(String nameOrId) {
		driver.switchTo().frame(nameOrId);	
	}
	
	
	/**
	 * 按照元素方式进入iframe
	 * @param element
	 */
	public void inframe(WebElement element) {
		logger.info("正在跳转进入iframe:" + element);
		try {
			driver.switchTo().frame(element);
		} catch (NoSuchFrameException e) {
			logger.info("跳转进入iframe:" + element + "失败");
			Assert.fail("跳转进入iframe:" + element + "失败");
		}
		
		logger.info("跳转进入iframe成功");
	}
	
	/**
	 * 按照By对象方式进入iframe
	 */
	public void inframe(By by) {
		logger.info("正在跳转进入iframe:" + getElement(by));
		try {
			driver.switchTo().frame(getElement(by));
		} catch (NoSuchFrameException e) {
			logger.info("跳转进入iframe:" + getElement(by) + "失败");
			Assert.fail("跳转进入iframe:" + getElement(by) + "失败");
		}
		
		logger.info("跳转进入iframe成功");
	}
	
	
	
	/** 跳出frame */
	public void outFrame() {
		driver.switchTo().defaultContent();
	}
	
	public void switchToActiveElement() {
		driver.switchTo().activeElement();
	}
	
	
	
	/**
	 * 根据by对象返回webelement对象
	 * @return
	 */
	public WebElement getElement(By by) {
		return driver.findElement(by);		
	}
	
	/**
	 * 判断元素是否存在
	 * @param by
	 * @return
	 */
	public boolean isElementExit(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * 选择下拉选项 -根据value
	 * */
	public void selectByValue(By by, String value) {
		Select s = new Select(driver.findElement(by));
		s.selectByValue(value);
	}

	/**
	 * 选择下拉选项 -根据index角标
	 * */
	public void selectByIndex(By by, int index) {
		Select s = new Select(driver.findElement(by));
		s.selectByIndex(index);
	}

	
	/**
	 * 上传文件，需要点击弹出上传照片的窗口才行
	 *
	 *            使用的浏览器名称
	 * @param file
	 *            需要上传的文件及文件名
	 */
	public void handleUpload(String browser, File file) {
		String filePath = file.getAbsolutePath();
		String executeFile = "res/script/autoit/upload.exe";
		String cmd = "\"" + executeFile + "\"" + " " + "\"" + browser + "\"" + " " + "\"" + filePath + "\"";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在给定的时间内去查找元素，如果没找到则超时，抛出异常
	 * */
	public void waitForElementToLoad(int timeOut, final By By) {
		logger.info("开始查找元素[" + By + "]");
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver driver) {
					WebElement element = driver.findElement(By);
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			logger.error("超时!! " + timeOut + " 秒之后还没找到元素 [" + By + "]");
			Assert.fail("超时!! " + timeOut + " 秒之后还没找到元素 [" + By + "]");

		}
		logger.info("找到了元素 [" + By + "]");
	}
	
	public void implicitlyWait(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}
	
	public void setScriptTimeout(int timeOut) {
		driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.SECONDS);
	}
	
	public void waitForPageLoading(int timeOut) {
		logger.info("等待页面加载完成");
		driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
	}		
	
	/**
	 * 等待alert出现
	 * */
	public Alert switchToPromptedAlertAfterWait(long waitMillisecondsForAlert) throws NoAlertPresentException {
		final int ONE_ROUND_WAIT = 1;
		NoAlertPresentException lastException = null;

		long endTime = System.currentTimeMillis() + waitMillisecondsForAlert;

		for (long i = 0; i < waitMillisecondsForAlert; i += ONE_ROUND_WAIT) {  //在等待时间内每1秒钟进行一次轮询

			try {
				Alert alert = driver.switchTo().alert();
				return alert;
			} catch (NoAlertPresentException e) {
				lastException = e;
			}
			pause(ONE_ROUND_WAIT);

			if (System.currentTimeMillis() > endTime) {   //超过设置的等待时间，则跳出循环，抛出异常
				break;
			}
		}
		throw lastException;
	}
		
	/**
	 * input标签没有value属性时，取得input的值
	 * @param type
	 * @param typevalue
	 * @return
	 *//*
	public String getInputValue(String type, String typevalue) {
		String value = null;
		switch (type.toLowerCase()) {
		case "name":
			String jsname = "return document.getElementsByName('" + typevalue + "')[0].value;";
			value = (String)((JavascriptExecutor) driver).executeScript(jsname);
			break;

		case "id":
			String jsid = "return document.getElementById('"+typevalue+"').value;"; //把JS执行的值 返回出去
			value = (String)((JavascriptExecutor) driver).executeScript(jsid);
			break;
			
		default:
			logger.error("未定义的chose:"+type);
			Assert.fail("未定义的chose:"+type);			
		}
		return value;
	}
	*/
	
	/**
	 * 拖动元素，一般用于滚动条拖动
	 * @param by
	 */
	public void dragAndDrop(By by) {
		Actions builder = new Actions(driver);
		builder.dragAndDropBy(getElement(by), 0, 300).perform();
	}
	
	public void changeElementStyle(String js) {
		((JavascriptExecutor) driver).executeScript(js);
	}
	
	/**
	 * 模拟鼠标双击
	 * @param by
	 */
	public void doubleClick(By by) {
		Actions builder = new Actions(driver);
		builder.doubleClick(getElement(by));
	}
	
	
	/**
	 * 滑动滚动条到指定元素位置
	 * @param by
	 */
	public void scrollToElement(By by) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", getElement(by));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", getElement(by));
	}
	
	
	
	public void scrollToFoot() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	
	/**
	 * 模拟鼠标移动到元素上
	 * @param by
	 */
	public void moveToElement(By by) {
		Actions builder = new Actions(driver);
		builder.moveToElement(getElement(by)).perform();
		logger.info("鼠标移动到：" + by);
	}
	
	/**
	 * 模拟鼠标移动到元素上
	 * @param element
	 */
	public void moveToElement(WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).perform();
		logger.info("鼠标移动到：" + element);
	}
	
	
	
	/**
	 * 模拟鼠标右键点击
	 * @param by
	 */
	public void rightClickElement(By by) {
		Actions builder = new Actions(driver);
		builder.contextClick(getElement(by)).perform();
		logger.info("鼠标右键：" + by);
	}
	
	/**
	 * 模拟键盘回车操作
	 */
	public void enterAction() {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ENTER).perform();
		logger.info("按下回车键");
	}
	
	
	/**
	 * 模拟鼠标拖拽元素
	 * @param source  被拖拽的元素
	 * @param target  释放位置的元素
	 */
	public void dragAction(WebElement source, WebElement target) {
		Actions actions = new Actions(driver);
		actions.dragAndDrop(source, target);
		logger.info("将元素：" + source + "拖拽到" + target);
	}
	
	
	/**
	 * 判断实际文本是否包含期望文本
	 * @param actual
	 * @param expect
	 */
	public void isContainsText(String actual, String expect) {
		try {
			Assert.assertTrue(actual.contains(expect));
		} catch (AssertionError e) {
			logger.error(actual + "中没有找到期望元素：" + expect);
			Assert.fail(actual + "中没有找到期望元素：" + expect);
		}
		logger.info(actual + "中找到期望元素：" + expect);
	}	
	
	
	/**
	 * 判断元素是否显示
	 * @param by
	 * @return
	 */
	public boolean isDisplay(By by) {
		boolean isDisplay = false;
		WebElement element = driver.findElement(by);
		if (element.isDisplayed()) {
			logger.info("元素" + by + "isDisplay");
			isDisplay = true;
			return isDisplay;
		}else {
			logger.warn("元素" + by + "not display");
			return isDisplay;
		}	
	}
	
	/**
	 * 判断元素是否被选中
	 * @param by
	 * @return
	 */
	public boolean isSelected(By by) {
		boolean isSelected = false;
		WebElement element = driver.findElement(by);
		if (element.isSelected()) {
			logger.info("元素" + by + "已经被选中");
			isSelected = true;
			return isSelected;
		}else {
			logger.info("元素" + by + "没有被选中");
			return isSelected;
		}
	}
	
	/**
	 * 判断元素是否有效
	 * @param by
	 * @return
	 */
	public boolean isEnable(By by) {
		boolean isEnable = false;
		WebElement element = driver.findElement(by);
		if (element.isEnabled()) {
			logger.info("元素" + by + "为有效元素");
			isEnable = true;
			return isEnable;
		}else {
			logger.info("元素" + by + "为无效元素");
			return isEnable;
		}
	}	
	
	/**
	 * 获取元素的指定css属性值
	 * @param by
	 * @param cssPropertyName
	 * @return
	 */
	public String getCssValue(By by, String cssPropertyName) {
		WebElement element = driver.findElement(by);
		String cssValue = element.getCssValue(cssPropertyName);
		return cssValue;
	}
	
	/**
	 * 获取一组元素，返回list集合
	 * @param by
	 * @return
	 */
	public List<WebElement> getElements(By by) {
		List<WebElement> list = null;
		list = driver.findElements(by);
		return list;
	}
	
	/**
	 * 
	 * 遍历一组元素，判断是否存在关键字
	 * @param list
	 * @param expect
	 * @return
	 */
	public boolean isElementsTrue(List<WebElement> list, String expect) {
		WebElement element = null;
		Iterator<WebElement> it = list.iterator();
		boolean result = true;
		while (it.hasNext()) {
			element = it.next();
			if (!element.getText().contains(expect)) {
				result = false;
				logger.warn("列表元素存在和期望值" + expect + "不一致");
				break;
			}else {
				
			}			
		}
		return result;
	}
	
	/**
	 * 跳转到新开窗口
	 * 
	 * driver更换成新窗口的driver,可能造成原窗口driver失效，慎用
	 */
	public void switchToNewWindow() {
		String currentHandle = driver.getWindowHandle();
//		WebDriver newDriver = null;
		
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> handle = handles.iterator();
		
		while (handle.hasNext()) {
			if (handle.next() == currentHandle) {
				continue;
			}else {
		//		driver.close();
				driver = driver.switchTo().window(handle.next());
			}			
		}
	}
	
	/**
	 * 等待加载或者查询背景元素消失
	 */
	public void waitElementDisappear(By by) {
		while (isElementExit(by)) {
			pause(1);
		}
	}
	
}
