package hk.xhy.android_common_sample.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import hk.xhy.android.common.utils.ImageUtils;
import hk.xhy.android_common_sample.AppConfig;
import hk.xhy.android_common_sample.R;
import hk.xhy.android_common_sample.ui.MainActivity;

/**
 * Created by xuhaoyang on 2017/11/24.
 */

public class Utils {

    public static void showNotification(int notifyId, String title, String content, Class<?> objectCls) {
        Context mContext = hk.xhy.android.common.utils.Utils.getContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.
                Builder(mContext).
                setSmallIcon(R.mipmap.ic_launcher).
                setContentTitle(title).setContentText(content);
        mBuilder.setAutoCancel(true).
                setLargeIcon(ImageUtils.getBitmap(R.mipmap.ic_launcher));

        int num = AppConfig.getInt(notifyId + objectCls.getSimpleName(), 0);
        mBuilder.setNumber(num);
        num++;
        AppConfig.putInt(notifyId + objectCls.getSimpleName(), num);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, objectCls);
        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notifyId, mBuilder.build());
    }
}
