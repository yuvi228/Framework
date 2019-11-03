package com.testbase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Baseclass {

	public static WebDriver driver; // To invoke Webdriver in Selenium
	// public static IOSDriver<MobileElement> driver; To invoke iOS driver in Appium

	public void init() {
		// TODO Auto-generated constructor stub
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

	}

	
}
