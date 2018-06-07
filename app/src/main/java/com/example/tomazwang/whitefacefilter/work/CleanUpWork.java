package com.example.tomazwang.whitefacefilter.work;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import androidx.work.Worker;
import com.example.tomazwang.whitefacefilter.Constant;
import com.example.tomazwang.whitefacefilter.WhiteFaceUtils;
import java.io.File;

/**
 * Created by TomazWang on 2018/06/07.
 *
 * @author Tomaz Wang
 * @since 2018/06/07
 **/

public class CleanUpWork extends Worker {

    private static final String TAG = CleanUpWork.class.getSimpleName();

    @NonNull
    @Override
    public WorkerResult doWork() {
        Context context = getApplicationContext();

        try {
            File outputDirectory = new File(context.getFilesDir(), Constant.WORK_DATA_OUTPUT_PATH);
            if (outputDirectory.exists()) {
                File[] entries = outputDirectory.listFiles();
                if (entries != null && entries.length > 0) {
                    for (File entry : entries) {
                        String name = entry.getName();
                        if (!TextUtils.isEmpty(name) && name.endsWith(".png")) {
                            boolean deleted = entry.delete();
                            Log.i(TAG, String.format("Deleted %s - %s", name, deleted));
                        }
                    }
                }
            }

            WhiteFaceUtils.makeNotification("All clean up!", context);
            return WorkerResult.SUCCESS;
        } catch (Exception exception) {
            Log.e(TAG, "Error cleaning up", exception);
            return WorkerResult.FAILURE;
        }
    }
}
