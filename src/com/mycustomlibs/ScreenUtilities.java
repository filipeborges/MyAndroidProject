package com.mycustomlibs;

public class ScreenUtilities {

	public static int getPxFromDp(int dpUnits, int screenDensity) {
		if(dpUnits <= 0 || screenDensity <= 0) {
			return -1;
		}
		else {
			float result = dpUnits * ((float)screenDensity/(float)160);
			return (int)result;
		}
	}
}
