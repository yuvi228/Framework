package test.demo;

import org.testng.Assert;

import org.testng.annotations.Test;

import com.extentreport.ExtentReportTest;
import com.testbase.Lauchurl;

public class Demo extends ExtentReportTest {

	Lauchurl url = new Lauchurl();

	@Test(priority = 0, description = "This is to launch google")
	public void googleRedirect() throws Exception {

		url.lauchgoogle();

		testStep("fail", "Launching URL");

		Assert.assertEquals(false, true);
	}

	@Test(priority = 1, description = "This is to launch yahoo")
	public void googleRedirect1() throws Exception {

		url.lauchgoogle();
		testStep("pass", "Launching URL");

		Assert.assertEquals(true, true);
	}
}
