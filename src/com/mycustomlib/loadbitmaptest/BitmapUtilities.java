package com.mycustomlib.loadbitmaptest;

import java.io.InputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.content.Context;
import android.content.res.Resources;

public class BitmapUtilities {
	
	public BitmapUtilities() {}
	
	public Bitmap setTransparencyOnBitmap(Bitmap bitmap, int colorPattern) {
		if(bitmap == null) {
			return null;
		}
		else {
			final int HEIGHT = bitmap.getHeight();
			final int WIDTH = bitmap.getWidth();
		
			Bitmap bitmapWithAlpha = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
		
			for(int y = 0; y < HEIGHT; y++) {
				for(int x = 0; x < WIDTH; x++) {
					if(bitmap.getPixel(x, y) == colorPattern) {
						bitmapWithAlpha.setPixel(x, y, Color.TRANSPARENT);
					}
					else {
						bitmapWithAlpha.setPixel(x, y, bitmap.getPixel(x, y));
					}
				}
			}
			return bitmapWithAlpha;
		}
	}
	
	/*Target resolution should be equal or less the resolution from the image to open.*/
	public Bitmap loadResourceBitmap(Resources res, int resourceId, int reqWidth, int reqHeight) {
		if(reqWidth == 0 || reqHeight == 0 || res == null) {
			return null;
		}
		else {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(res, resourceId, options);
		
			if(reqWidth > options.outWidth || reqHeight > options.outHeight) {
				return null;
			}
			else {
				options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
				options.inJustDecodeBounds = false;
		
				return BitmapFactory.decodeResource(res, resourceId, options);
			}
		}
	}
	
	/*Target resolution should be equal or less the resolution from the image to open.*/
	public Bitmap loadAssetBitmap(Context context, String fileName, int reqWidth, int reqHeight) {
		if(reqWidth == 0 || reqHeight == 0) {
			return null;
		}
		else {
			BitmapFactory.Options options = new BitmapFactory.Options();
		
			options.inJustDecodeBounds = true;
			
			byte[] bufferData = createByteArrayFromAsset(context, fileName);
		
			if(bufferData != null) {
				BitmapFactory.decodeByteArray(bufferData, 0, bufferData.length, options);
			
				if(reqWidth > options.outWidth || reqHeight > options.outHeight) {
					return null;
				}
				else {
					options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
					options.inJustDecodeBounds = false;
					return BitmapFactory.decodeByteArray(bufferData, 0, bufferData.length, options);
				}
			}
			else {
				return null;
			}
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
	
	private byte[] createByteArrayFromAsset(Context context, String fileName) {
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
