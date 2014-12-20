package com.mycustomlib.loadbitmaptest;

import com.example.loadbitmaptest.R;

import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.EditText;

public class MainActivity extends Activity {

	//320x180
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LoaderThread loaderThread = new LoaderThread((ImageView)findViewById(R.id.imageViewCenter), "image.jpg", this);
        loaderThread.execute(320, 180);
        
        LoaderThread loaderThread2 = new LoaderThread((ImageView)findViewById(R.id.imageViewTop), "spider-man.jpg", this);
        loaderThread2.execute(320, 320);
        LoaderThread loaderThread3 = new LoaderThread((ImageView)findViewById(R.id.imageViewTop), "mk4.jpg", this);
        loaderThread3.execute(120, 120);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
