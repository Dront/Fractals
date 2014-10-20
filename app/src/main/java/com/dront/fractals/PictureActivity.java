package com.dront.fractals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


public class PictureActivity extends Activity {

    private static final int UI_UPDATE_DELAY = 500;

    private int count = 1;
    private int picNum = 0;

    private ImageView imgViewMain;
    private Handler h;
    private Runnable pictureUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        getInterfaceResources();

        Intent i = getIntent();
        count = i.getIntExtra("count", 1);

        h = new Handler();
        pictureUpdate = new Runnable() {
            @Override
            public void run() {
                imgViewMain.setImageBitmap(MainActivity.pic[picNum++ % count]);
                h.postDelayed(this, UI_UPDATE_DELAY);
            }
        };
    }


    private void getInterfaceResources(){
        imgViewMain = (ImageView) findViewById(R.id.imgViewMain);
    }


    @Override
    protected void onResume() {
        super.onResume();
        h.postDelayed(pictureUpdate, UI_UPDATE_DELAY);
    }

    @Override
    protected void onPause() {
        h.removeCallbacks(pictureUpdate);
        super.onPause();
    }
}
