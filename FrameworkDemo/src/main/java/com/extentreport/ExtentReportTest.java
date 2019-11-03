package com.extentreport;

import java.io.File;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.testbase.Baseclass;
import com.util.Util;

public class ExtentReportTest extends Baseclass {

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTest parenttest;
	public static ExtentTest childtest;

	@BeforeSuite
	public void setUp() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\extentreport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("OS", "Windows 7");
		extent.setSystemInfo("Host Name", "Yuvraj");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User Name", "Yuvraj Rajput");

		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName("Automation Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
	}

	@BeforeTest
	public void parentTestname() {
		parenttest = extent.createTest(getClass().getName());
	}

	@BeforeMethod
	public void childTestname(Method method) {
		childtest = parenttest.createNode(method.getName());
	}

	public void testStep(String status, String stepdesc) {

		if (status.equalsIgnoreCase("INFO")) {

			childtest.log(Status.INFO, MarkupHelper.createLabel(stepdesc, ExtentColor.YELLOW));

		} else if (status.equalsIgnoreCase("ERROR")) {
			childtest.log(Status.ERROR, MarkupHelper.createLabel(stepdesc, ExtentColor.PURPLE));
		} else if (status.equalsIgnoreCase("FAIL")) {
			childtest.log(Status.FAIL, MarkupHelper.createLabel(stepdesc, ExtentColor.RED));
		} else if (status.equalsIgnoreCase("FATAL")) {
			childtest.log(Status.FATAL, MarkupHelper.createLabel(stepdesc, ExtentColor.ORANGE));
		} else if (status.equalsIgnoreCase("PASS")) {
			childtest.log(Status.PASS, MarkupHelper.createLabel(stepdesc, ExtentColor.GREEN));
		} else if (status.equalsIgnoreCase("SKIP")) {
			childtest.log(Status.SKIP, MarkupHelper.createLabel(stepdesc, ExtentColor.BLUE));
		} else if (status.equalsIgnoreCase("UNKNOWN")) {
			childtest.log(Status.UNKNOWN, MarkupHelper.createLabel(stepdesc, ExtentColor.LIME));
		} else if (status.equalsIgnoreCase("WARNING")) {
			childtest.log(Status.WARNING, MarkupHelper.createLabel(stepdesc, ExtentColor.BLACK));
		}

	}

	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			childtest.log(Status.FAIL, MarkupHelper
					.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
			childtest.fail(result.getThrowable());
			String screenshotPath = ExtentReportTest.getScreenshot(driver, result.getName());
			childtest.addScreenCaptureFromPath(screenshotPath);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			childtest.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
			String screenshotPath = ExtentReportTest.getScreenshot(driver, result.getName());
			childtest.addScreenCaptureFromPath(screenshotPath);
		} else {
			childtest.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			childtest.skip(result.getThrowable());
		}
		
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = System.getProperty("user.dir") + "\\FailedTestsScreenshots\\" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@AfterSuite
	public void tearDown() {
		extent.flush();
		Util util = new Util();
		util.sendEmail();
	}

}
