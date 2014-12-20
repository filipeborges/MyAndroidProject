package com.mycustomlib.loadbitmaptest;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;

public class BitmapLoader {
	
	private Context context;
	
	public BitmapLoader(Context context) {
		this.context = context;
	}
	
	public Bitmap loadBitmapUsingPercentScreen(String fileName, int screenPercent, int screenWidth, int screenHeight) {
		
		return null;
	}
	
	public Bitmap loadBitmap(String fileName, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = null;
		
		options.inJustDecodeBounds = true;
		byte[] bufferData = createByteArrayFromFile(fileName);
		
		if(bufferData != null) {
			bitmap = BitmapFactory.decodeByteArray(bufferData, 0, bufferData.length, options);
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
			options.inJustDecodeBounds = false;
			
			return BitmapFactory.decodeByteArray(bufferData, 0, bufferData.length, options);
		}
		else {
			return null;
		}
	}
	
	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int originalWidth = options.outWidth;
		final int originalHeight = options.outHeight;
		int inSampleSize = 1;
		
		while( (originalWidth/inSampleSize) > reqWidth || (originalHeight/inSampleSize) > reqHeight) {
			inSampleSize *= 2;
		}
		
		return inSampleSize;
	}
	
	private byte[] createByteArrayFromFile(String fileName) {
		int totalOfBytes = 0;
		byte[] buffer = null;
		
		try {
			InputStream input = context.getAssets().open(fileName);
			
			if(input.markSupported()) {
				input.mark(Integer.MAX_VALUE);
				while(input.read() != -1) { 
					totalOfBytes += 1;
				}
				buffer = new byte[totalOfBytes];
				input.reset();
				for(int i = 0; i < totalOfBytes; i++) {
					buffer[i] = (byte)input.read();
				}
			}
			else {
				return null;
			}
		}catch(IOException ioe) {
			return null;
		}

		return buffer;
	}
}
