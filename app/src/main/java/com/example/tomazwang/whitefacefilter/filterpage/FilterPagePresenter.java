package com.example.tomazwang.whitefacefilter.filterpage;

import android.graphics.Bitmap;
import android.net.Uri;
import com.example.tomazwang.whitefacefilter.WhiteFaceUtils;
import com.example.tomazwang.whitefacefilter.app.App;

import static com.example.tomazwang.whitefacefilter.WhiteFaceUtils.uriOrNull;

/**
 * Created by TomazWang on 2018/06/07.
 *
 * @author Tomaz Wang
 * @since 2018/06/07
 **/

public class FilterPagePresenter implements FilterPageContract.Presenter {

    private FilterPageContract.View mView;
    private Uri mImageUri;

    @Override
    public void setView(FilterPageContract.View view) {
        this.mView = view;
    }


    @Override
    public void setSourceImagePath(String imagePath) {
        mImageUri = uriOrNull(imagePath);
    }

    @Override
    public void filter(int strength) {
        Bitmap sourceImage = getImageBitmap();
        Bitmap result = WhiteFaceUtils.whiteFilter(sourceImage, strength);
        mView.showImage(result);
    }

    private Bitmap getImageBitmap() {
        return WhiteFaceUtils.getImageFromResource(App.get());
    }
}
