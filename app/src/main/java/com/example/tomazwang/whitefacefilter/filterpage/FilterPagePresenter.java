package com.example.tomazwang.whitefacefilter.filterpage;

import android.net.Uri;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import com.example.tomazwang.whitefacefilter.Constant;
import com.example.tomazwang.whitefacefilter.work.BrightnessWork;
import com.example.tomazwang.whitefacefilter.work.CleanUpWork;

import static com.example.tomazwang.whitefacefilter.WhiteFaceUtils.uriOrNull;

/**
 * Created by TomazWang on 2018/06/07.
 *
 * @author Tomaz Wang
 * @since 2018/06/07
 **/

public class FilterPagePresenter implements FilterPageContract.Presenter {

    private static final String TAG = FilterPagePresenter.class.getSimpleName();

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
        mView.showImage(mImageUri);
    }

    @Override
    public void filter(int strength) {

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BrightnessWork.class)
                .setInputData(createInputDataForUri())
                .build();

        WorkContinuation continuation = mWorkManager.beginUniqueWork(
                Constant.WORKNAME_FILTER,
                ExistingWorkPolicy.REPLACE,
                request
        );
        
        continuation = continuation.then(OneTimeWorkRequest.from(CleanUpWork.class));
        continuation.enqueue();
    }

    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        if (mImageUri != null) {
            builder.putString(Constant.KEY_WORK_DATA_IMAGE_URI, mImageUri.toString());
        }
        return builder.build();
    }


}
