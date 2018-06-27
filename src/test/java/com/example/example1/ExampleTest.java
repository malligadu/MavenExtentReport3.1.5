package com.example.example1;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExampleTest {
	WebDriver driver;
	ExtentHtmlReporter htmlreport;
	ExtentReports extent;
	ExtentTest test;
	
	
	@BeforeMethod
	public void setUp() {
		htmlreport=new ExtentHtmlReporter("./reports/"+new Date().toString()+".html");
		extent=new ExtentReports();
		extent.attachReporter(htmlreport);
		test=extent.createTest("Google test verify");
		test.log(Status.INFO, "open browser");
		test.log(Status.INFO, "open google page");
		test.log(Status.INFO, "verfiy title");
		test.log(Status.DEBUG, "debugging");
	
	}
	
	@Test
	public void test() {
		System.setProperty("webdriver.chrome.driver","/home/nagendra/Downloads/chromedriver");
		driver=new ChromeDriver();
		driver.get("http://www.google.com");
		Assert.assertTrue(false);
		
	}
	
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			String path=getScreenShot();
			test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			extent.flush();
			driver.quit();
		}
		
		
	}
public String getScreenShot() throws IOException {
	TakesScreenshot ts=(TakesScreenshot)driver;
	File src=ts.getScreenshotAs(OutputType.FILE);
	
	String path=System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
	File dest=new File(path);
	
	FileUtils.copyFile(src, dest);
	return path;
	
}
}
