package com.example.tomazwang.whitefacefilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by TomazWang on 2018/06/06.
 *
 * @author Tomaz Wang
 * @since 2018/06/06
 **/

public final class WhiteFaceUtils {

    private static final String TAG = WhiteFaceUtils.class.getSimpleName();

    /**
     * Crank up the brightness of the input Bitmap
     *
     * @param original source image as bitmap
     * @param strength the strength of bright from 0 ~ 100
     */
    public static Bitmap whiteFilter(Bitmap original, int strength) {
        Log.d(TAG, "whiteFilter: ");

        Bitmap bitmap = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        int brightness = (int) (((float) strength / 100f) * 180) + 50;

        PorterDuffColorFilter pdf =
                new PorterDuffColorFilter(Color.argb(brightness, 255, 255, 255), PorterDuff.Mode.SRC_OVER);
        paint.setColorFilter(pdf);

        canvas.drawBitmap(original, 0, 0, paint);

        return bitmap;
    }

    /**
     * Get a test image from resource
     */
    public static Bitmap getImageFromResource(Context context) {
        Bitmap picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.some_guy);
        return picture;
    }

    /**
     * Convert a string to uri. If the string is null or empty, returns null
     */
    public static Uri uriOrNull(String uriString) {
        if (!TextUtils.isEmpty(uriString)) {
            return Uri.parse(uriString);
        }
        return null;
    }
}