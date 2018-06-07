package com.example.tomazwang.whitefacefilter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

    private static final String NOTIFICATION_CHANNEL_NAME = "WhiteFace notification" ;
    private static final String NOTIFICATION_CHANNEL_DESCRIPTION = "show some notification when things happend";
    private static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID";
    private static final String NOTIFICATION_TITLE = "White Face Filter";
    private static final int NOTIFICATION_ID = 9487;

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


    /**
     * Create a Notification.
     *
     * @param message Message shown on the notification
     * @param context Context needed to create Toast
     */
    public static void makeNotification(String message, Context context) {

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = NOTIFICATION_CHANNEL_NAME;
            String description = NOTIFICATION_CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Add the channel
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[0]);

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build());
    }
}
