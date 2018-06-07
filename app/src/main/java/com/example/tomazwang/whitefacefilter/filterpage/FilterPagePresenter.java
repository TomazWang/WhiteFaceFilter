package com.example.tomazwang.whitefacefilter.filterpage;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.net.Uri;
import android.support.annotation.Nullable;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;
import com.example.tomazwang.whitefacefilter.Constant;
import com.example.tomazwang.whitefacefilter.work.BrightnessWork;
import com.example.tomazwang.whitefacefilter.work.CleanUpWork;
import java.util.List;

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
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {

        mWorkManager.getStatusesForUniqueWork(Constant.WORKNAME_FILTER).observe(lifecycleOwner,
                new Observer<List<WorkStatus>>() {
                    @Override
                    public void onChanged(@Nullable List<WorkStatus> workStatuses) {
                        if (workStatuses == null || workStatuses.isEmpty()) {
                            return;
                        }

                        WorkStatus workStatus = workStatuses.get(0);
                        if (workStatus.getState().isFinished()) {
                            mView.dismissProgress();
                        } else {
                            mView.showProgress();
                        }
                    }
                });
    }

    @Override
    public void setSourceImagePath(String imagePath) {
        mImageUri = uriOrNull(imagePath);
        mView.showImage(mImageUri);
    }

    @Override
    public void filter(int strength) {

        OneTimeWorkRequest filterWork = new OneTimeWorkRequest.Builder(BrightnessWork.class)
                .setInputData(createInputDataForUri())
                .build();

        WorkContinuation continuation = mWorkManager.beginUniqueWork(
                Constant.WORKNAME_FILTER,
                ExistingWorkPolicy.REPLACE,
                filterWork
        );

        // Create charging constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest cleanUpWork = new OneTimeWorkRequest.Builder(CleanUpWork.class)
                .setConstraints(constraints)
                .build();

        continuation = continuation.then(cleanUpWork);
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
