package com.testbase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Baseclass {

	public static WebDriver driver; // To invoke Webdriver in Selenium
	// public static IOSDriver<MobileElement> driver; To invoke iOS driver in Appium
	



	public Baseclass() {
		// TODO Auto-generated constructor stub
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

	}

	
}
