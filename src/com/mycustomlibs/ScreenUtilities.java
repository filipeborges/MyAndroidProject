package com.mycustomlibs;

import android.util.DisplayMetrics;
import android.content.Context;
import android.view.WindowManager;

public class ScreenUtilities {

	/*Return: [0] - DPI, [1] - WIDTH, [2] - HEIGHT*/
	public static int[] getScreenProperties(Context context) {
		if(context == null) {
			return null;
		}
		else {
			DisplayMetrics metrics = new DisplayMetrics();
			((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
			int[] screenProperties = new int[3];
			screenProperties[0] = metrics.densityDpi;
			screenProperties[1] = metrics.widthPixels;
			screenProperties[2] = metrics.heightPixels;
			return screenProperties;
		}
	}
	
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
