package com.testbase;

import com.util.Util;

public class Lauchurl extends Baseclass {
	
	Util util = new Util();
	
	public void lauchgoogle() throws Exception {
		init();
		driver.get(util.getPropertydata("baseurl"));
	}

}
