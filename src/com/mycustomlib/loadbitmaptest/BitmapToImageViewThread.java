package com.mycustomlib.loadbitmaptest;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.content.Context;
import android.content.res.Resources;

public class BitmapToImageViewThread extends AsyncTask<Void, Void, Bitmap>{
	private WeakReference<ImageView> uiImageView;
	private WeakReference<Context> context;
	private WeakReference<Resources> res;
	private String imageFileName;
	private int resourceId;
	private int reqWidth;
	private int reqHeight;
	private boolean isFromResource = false;
	private boolean isFromAsset = false;
	
	public BitmapToImageViewThread(ImageView imageView) {
		this.uiImageView = new WeakReference<ImageView>(imageView);
	}
	
	public void setupBitmapFromAsset(Context context, String fileName, int reqWidth, int reqHeight) {
		if(isFromResource) {
			isFromResource = false;
		}
		else {
			isFromAsset = true;
			this.context = new WeakReference<Context>(context);
			this.imageFileName = fileName;
			this.reqWidth = reqWidth;
			this.reqHeight = reqHeight;
		}
	}
	
	public void setupBitmapFromResource(Resources res, int resourceId, int reqWidth, int reqHeight) {
		if(isFromAsset) {
			isFromAsset = false;
		}
		else {
			isFromResource = true;
			this.res = new WeakReference<Resources>(res);
			this.resourceId = resourceId;
			this.reqWidth = reqWidth;
			this.reqHeight = reqHeight;
		}
	}
	
	@Override
	protected Bitmap doInBackground(Void... empty) {
		BitmapLoader bitmapLoader = new BitmapLoader();
		Bitmap bitmap = null;
		
		if(isFromAsset) {
			bitmap = bitmapLoader.loadAssetBitmap(context.get(), imageFileName, reqWidth, reqHeight);
		}
		else if(isFromResource) {
			bitmap = bitmapLoader.loadResourceBitmap(res.get(), resourceId, reqWidth, reqHeight);
		}
			
		if(bitmap != null) {
			return bitmap;
		}
		else {
			return null;
		}
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if(bitmap != null) {
			uiImageView.get().setImageBitmap(bitmap);
		}
	}
}
