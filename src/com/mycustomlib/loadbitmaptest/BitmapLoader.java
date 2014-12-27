package com.mycustomlib.loadbitmaptest;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.content.res.Resources;

public class BitmapLoader {
	
	private Context context;
	
	public BitmapLoader(Context context) {
		this.context = context;
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
	public Bitmap loadAssetBitmap(String fileName, int reqWidth, int reqHeight) {
		if(reqWidth == 0 || reqHeight == 0) {
			return null;
		}
		else {
			BitmapFactory.Options options = new BitmapFactory.Options();
		
			options.inJustDecodeBounds = true;
			byte[] bufferData = createByteArrayFromAsset(fileName);
		
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
	
	private byte[] createByteArrayFromAsset(String fileName) {
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
