package com.example.tomazwang.whitefacefilter.filterpage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.tomazwang.whitefacefilter.R;
import java.io.ByteArrayOutputStream;

public class FilterActivity extends AppCompatActivity implements FilterPageContract.View{

    private View mBtnMin;
    private View mBtnMid;
    private View mBtnMax;
    private ImageView mImgPhoto;
    private View mProgress;

    private static final String INTENT_KEY_IMAGE_URI = "INTENT_KEY_IMAGE_URI";

    private FilterPagePresenter mPresenter;

    public static Intent getIntent(Activity from, String imgUri) {
        Intent intent = new Intent(from, FilterActivity.class);
        intent.putExtra(INTENT_KEY_IMAGE_URI, imgUri);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mPresenter = new FilterPagePresenter();
        mPresenter.setView(this);
        mPresenter.setLifecycleOwner(this);

        mImgPhoto = findViewById(R.id.img_photo);

        mBtnMin = findViewById(R.id.btn_filter_min);
        mBtnMid = findViewById(R.id.btn_filter_mid);
        mBtnMax = findViewById(R.id.btn_filter_max);

        mBtnMin.setOnClickListener( v -> mPresenter.filter(40));
        mBtnMid.setOnClickListener( v -> mPresenter.filter(70));
        mBtnMax.setOnClickListener( v -> mPresenter.filter(100));

        mProgress = findViewById(R.id.progress_loading);

        Intent intent = getIntent();
        String imageUriExtra = intent.getStringExtra(INTENT_KEY_IMAGE_URI);
        mPresenter.setSourceImagePath(imageUriExtra);

    }


    @Override
    public void showImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(this).load(stream.toByteArray()).asBitmap().into(mImgPhoto);
    }

    @Override
    public void showImage(Uri imageUri) {
        if(imageUri != null){
            Glide.with(this).load(imageUri).into(mImgPhoto);
        }
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        mProgress.setVisibility(View.GONE);
    }
}
