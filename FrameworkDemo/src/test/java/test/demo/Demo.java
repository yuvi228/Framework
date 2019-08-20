package test.demo;

import org.testng.Assert;


import org.testng.annotations.Test;

import com.extentreport.ExtentReportTest;
import com.testbase.Lauchurl;

public class Demo extends ExtentReportTest {
	
	Lauchurl url = new Lauchurl();

	@Test
	public void googleRedirect() throws Exception {
		parenttest = extent.createTest("Club owner Flow");
		childtest = parenttest.createNode("Login");
		url.lauchgoogle();
		Assert.assertEquals(false,true);
	}


	
	}
