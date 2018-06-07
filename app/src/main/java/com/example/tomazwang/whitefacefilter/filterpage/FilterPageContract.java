package com.example.tomazwang.whitefacefilter.filterpage;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by TomazWang on 2018/06/07.
 *
 * @author Tomaz Wang
 * @since 2018/06/07
 **/

public interface FilterPageContract {

    interface View {

        void showImage(Bitmap bitmap);

        void showImage(Uri imageUri);

        void showProgress();

        void dismissProgress();

    }

    interface Presenter {

        void setView(View view);

        void setSourceImagePath(String imageUri);

        void filter(int strength);

    }
}
