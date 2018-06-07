package com.example.tomazwang.whitefacefilter.work;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import com.example.tomazwang.whitefacefilter.Constant;
import com.example.tomazwang.whitefacefilter.WhiteFaceUtils;
import java.io.FileNotFoundException;

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

        // get input
        String resourceUri = getInputData().getString(Constant.KEY_WORK_DATA_IMAGE_URI, null);



        // get work form resource
        // Bitmap source = WhiteFaceUtils.getImageFromResource(context);
        ContentResolver resolver = context.getContentResolver();
        Bitmap source = null;
        try {

            source = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return WorkerResult.FAILURE;
        }

        Bitmap result = WhiteFaceUtils.whiteFilter(source, 2);

        Uri outputUri;
        try {
            outputUri = WhiteFaceUtils.writeBitmapToFile(context, result);
            setOutputData(new Data.Builder().putString(Constant.KEY_WORK_DATA_IMAGE_URI, outputUri.toString()).build());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return WorkerResult.FAILURE;
        }

        //WhiteFaceUtils.makeNotification("Jobs done!", context);
        return WorkerResult.SUCCESS;
    }
}
