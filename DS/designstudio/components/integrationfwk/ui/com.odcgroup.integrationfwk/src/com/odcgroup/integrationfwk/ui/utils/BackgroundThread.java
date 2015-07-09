package com.odcgroup.integrationfwk.ui.utils;

import java.sql.Date;

public class BackgroundThread implements Runnable {

	public void run() {

		Date d = new Date(0);
		System.out.println("Now is" + d.getTime());
	}

}
