package com.mycustomlib.loadbitmaptest;

import java.lang.ref.WeakReference;

import com.example.loadbitmaptest.R;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.content.Context;

public class LoaderThread extends AsyncTask<Integer, Void, Bitmap>{
	private WeakReference<ImageView> uiImageView;
	private Context context;
	private String imageFileName;
	
	public LoaderThread(ImageView imageView, String imageFileName, Context context) {
		this.uiImageView = new WeakReference<ImageView>(imageView);
		this.imageFileName = imageFileName;
		this.context = context;
	}
	
	//Recebe como "params": reqWidth e reqHeight.
	@Override
	protected Bitmap doInBackground(Integer... params) {
		BitmapLoader bitmapLoader = new BitmapLoader(context);
		Bitmap bitmap = bitmapLoader.loadAssetBitmap(imageFileName, params[0], params[1]);
		
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
