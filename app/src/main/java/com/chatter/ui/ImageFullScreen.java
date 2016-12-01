package com.chatter.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chatter.R;

public class ImageFullScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_image_full_screen);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String filePath = bundle.getString("filePath");

        ImageView view = (ImageView) findViewById(R.id.fullScreenImage);
        view.setImageURI(Uri.parse(filePath));

        getWindow().getDecorView().setBackgroundColor(Color.BLACK);


    }
}
