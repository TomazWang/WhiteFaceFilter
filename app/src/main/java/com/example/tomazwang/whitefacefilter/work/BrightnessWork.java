package com.example.tomazwang.whitefacefilter.work;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import androidx.work.Worker;
import com.example.tomazwang.whitefacefilter.WhiteFaceUtils;

/**
 * Created by TomazWang on 2018/06/07.
 *
 * @author Tomaz Wang
 * @since 2018/06/07
 **/

public class BrightnessWork extends Worker {

    @NonNull
    @Override
    public WorkerResult doWork() {

        // things happened here will run in background thread (worker thread)

        Context context = getApplicationContext();

        // get work form resource
        Bitmap source = WhiteFaceUtils.getImageFromResource(context);
        Bitmap result = WhiteFaceUtils.whiteFilter(source, 2);

        WhiteFaceUtils.makeNotification("Jobs done!", context);

        return WorkerResult.SUCCESS;
    }
}
