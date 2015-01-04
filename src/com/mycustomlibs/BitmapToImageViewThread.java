package com.mycustomlibs;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.content.Context;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

public class BitmapToImageViewThread extends AsyncTask<Void, Void, Bitmap>{
	private WeakReference<ImageView> uiImageView;
	private WeakReference<Activity> activity;
	private WeakReference<View> progressBar;
	private String imageFileName;
	private int resourceId;
	private int reqWidth;
	private int reqHeight;
	private int progressBarId;
	private boolean isFromResource = false;
	private boolean isFromAsset = false;
	private boolean isTransparent = false;
	private int colorPattern;
	
	public BitmapToImageViewThread(ImageView imageView, Activity activity, int progressBarLayoutId) {
		this.uiImageView = new WeakReference<ImageView>(imageView);
		this.activity = new WeakReference<Activity>(activity);
		this.progressBarId = progressBarLayoutId;
	}
	
	public void setTransparencyOnBitmapBasedOnColor(boolean isTransparent, int colorPattern) {
		this.isTransparent = isTransparent;
		this.colorPattern = colorPattern;
	}
	
	public void setupBitmapFromAsset(String fileName, int reqWidth, int reqHeight) {
		if(isFromResource) {
			isFromResource = false;
		}
		
		isFromAsset = true;
		this.imageFileName = fileName;
		this.reqWidth = reqWidth;
		this.reqHeight = reqHeight;
	}
	
	public void setupBitmapFromResource(int resourceId, int reqWidth, int reqHeight) {
		if(isFromAsset) {
			isFromAsset = false;
		}
		
		isFromResource = true;
		this.resourceId = resourceId;
		this.reqWidth = reqWidth;
		this.reqHeight = reqHeight;
	}
	
	@Override
	protected Bitmap doInBackground(Void... empty) {
		this.publishProgress();
		BitmapUtilities bitmapLoader = new BitmapUtilities();
		Bitmap bitmap = null;
		
		if(isFromAsset) {
			bitmap = bitmapLoader.loadAssetBitmap(activity.get(), imageFileName, reqWidth, reqHeight);
		}
		else if(isFromResource) {
			bitmap = bitmapLoader.loadResourceBitmap(activity.get().getResources(), resourceId, reqWidth, reqHeight);
		}
			
		if(bitmap != null) {
			if(isTransparent) {
				return bitmapLoader.setTransparencyOnBitmap(bitmap, colorPattern);
			}
			else {
				return bitmap;
			}
		}
		else {
			return null;
		}
	}

	@Override
	protected void onProgressUpdate(Void... empty) {
		LayoutInflater inflater = (LayoutInflater)activity.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		progressBar = new WeakReference<View>(inflater.inflate(this.progressBarId, null));
		((ViewGroup)activity.get().findViewById(android.R.id.content)).addView(progressBar.get());
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if(bitmap != null) {
			uiImageView.get().setImageBitmap(bitmap);
		}
		((ViewGroup)activity.get().findViewById(android.R.id.content)).removeView(progressBar.get());
	}
}
