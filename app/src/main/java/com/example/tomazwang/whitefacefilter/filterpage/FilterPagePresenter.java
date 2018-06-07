package com.example.tomazwang.whitefacefilter.filterpage;

import android.net.Uri;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.example.tomazwang.whitefacefilter.work.BrightnessWork;

import static com.example.tomazwang.whitefacefilter.WhiteFaceUtils.uriOrNull;

/**
 * Created by TomazWang on 2018/06/07.
 *
 * @author Tomaz Wang
 * @since 2018/06/07
 **/

public class FilterPagePresenter implements FilterPageContract.Presenter {

    private FilterPageContract.View mView;
    private WorkManager mWorkManager;
    private Uri mImageUri;

    @Override
    public void setView(FilterPageContract.View view) {
        this.mView = view;
        this.mWorkManager = WorkManager.getInstance();
    }


    @Override
    public void setSourceImagePath(String imagePath) {
        mImageUri = uriOrNull(imagePath);
    }

    @Override
    public void filter(int strength) {
        mWorkManager.enqueue(OneTimeWorkRequest.from(BrightnessWork.class));
    }

}
