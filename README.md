# UIAutoTest（java版）

#idea+maven+testNg+reportng+log4j+  excel数据驱动的自动化测试框架


#主要框架结构介绍

BaseParpare类
主要提供基础服务，一般@BeforeTest  @AfterTest等内容可以写在这个类，测试类直接继承即可

Page包
每个页面单独一个类，将元素都封装在类中

PageHelper包
每个页面的操作可以单独封装在一个类中

testCase包
写测试用例

ExcelDataProvider
数据提供者，读取excel测试数据


SeleniumUtil类（可继续补充）
封装selenium的常用方法


#文件夹介绍
res
浏览器驱动程序（如chromedriver.exe）

data
测试数据excel文件

log
日志文件输出目录

test-output
测试报告输出目录  （test-output----html----index.html）
