package com.mycustomlibs;

public class ScreenUtilities {

	public int getPxFromDp(int dpUnits, int screenDensity) {
		if(dpUnits <= 0 || screenDensity <= 0) {
			return -1;
		}
		else {
			return dpUnits * (screenDensity/160);
		}
	}
}
