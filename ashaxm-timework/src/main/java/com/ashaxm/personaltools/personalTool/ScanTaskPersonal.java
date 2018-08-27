package com.ashaxm.personaltools.personalTool;

import java.util.TimerTask;


public class ScanTaskPersonal extends TimerTask{

	@Override
	public void run() {
		PersonalDown.scan();
	}

}
