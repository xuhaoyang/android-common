package hk.xhy.android.common.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.util.ArrayMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by xuhaoyang on 3/24/16.
 */
public class ActivityUtils {

    protected static Stack<Activity> activityStack;

    public ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断 Activity 是否存在
     *
     * @param packageName 包名
     * @param className   activity 全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(@NonNull final String packageName,
                                           @NonNull final String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(Utils.getApp().getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(Utils.getApp().getPackageManager()) == null ||
                Utils.getApp().getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }


    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
            }
        }
        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 获取launcher activity
     *
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(@NonNull final String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = Utils.getContext().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo aInfo : info) {
            if (aInfo.activityInfo.packageName.equals(packageName)) {
                return aInfo.activityInfo.name;
            }
        }
        return "no " + packageName;
    }

    /**
     * 获取栈顶Activity
     *
     * @return 栈顶Activity
     */
    public static Activity getTopActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = null;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                activities = (HashMap) activitiesField.get(activityThread);
            } else {
                activities = (ArrayMap) activitiesField.get(activityThread);
            }
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退出应用程序
     */
    public static void appExit(Context context) {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 启动Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param cls      activity类
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> cls) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param activity  activity
     * @param cls       activity类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<?> cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, activity.getPackageName(), cls.getName(), null);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 启动Activity
     *
     * @param extras    extras
     * @param activity  activity
     * @param cls       activity类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<?> cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName(), null);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

//    /**
//     * 启动Activity
//     *
//     * @param activity activity
//     * @param cls      activity类
//     * @param options  跳转动画
//     */
//    public static void startActivity(@NonNull final Activity activity,
//                                     @NonNull final Class<?> cls,
//                                     @NonNull final Bundle options) {
//        startActivity(activity, null, activity.getPackageName(), cls.getName(), options);
//    }

    /**
     * 启动Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param cls      activity类
     * @param options  跳转动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     final Class<?> cls,
                                     @NonNull final Bundle options) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName(), options);
    }

    /**
     * 启动Activity
     *
     * @param pkg 包名
     * @param cls 全类名
     */
    public static void startActivity(@NonNull final String pkg, @NonNull final String cls) {
        startActivity(Utils.getContext(), null, pkg, cls, null);
    }

    /**
     * 启动Activity
     *
     * @param extras extras
     * @param pkg    包名
     * @param cls    全类名
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(Utils.getContext(), extras, pkg, cls, extras);
    }

    /**
     * 启动Activity
     *
     * @param pkg     包名
     * @param cls     全类名
     * @param options 动画
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(Utils.getContext(), null, pkg, cls, options);
    }

    /**
     * 启动Activity
     *
     * @param extras  extras
     * @param pkg     包名
     * @param cls     全类名
     * @param options 动画
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @NonNull final Bundle options) {
        startActivity(Utils.getContext(), extras, pkg, cls, options);
    }

    private static void startActivity(final Context context,
                                      final Bundle extras,
                                      final String pkg,
                                      final String cls,
                                      final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent);
        } else {
            context.startActivity(intent, options);
        }
    }

    public static <T> void startActivityByContext(Context context, Class<T> clazz) {
        startActivityByContext(context, clazz, new HashMap<String, Object>());
    }

    public static <T> void startActivityByContext(Context context, Class<T> clazz, HashMap<String, Object> params) {
        Bundle bundle = new Bundle();
        for (String name : params.keySet()) {
            Object val = params.get(name);
            if (val instanceof String) {
                bundle.putString(name, (String) val);
            } else if (val instanceof Integer) {
                bundle.putInt(name, (Integer) val);
            } else if (val instanceof Long) {
                bundle.putLong(name, (Long) val);
            } else if (val instanceof Short) {
                bundle.putShort(name, (Short) val);
            } else if (val instanceof Float) {
                bundle.putFloat(name, (Float) val);
            } else if (val instanceof Double) {
                bundle.putDouble(name, (Double) val);
            }
        }
        startActivityByContext(context, clazz, bundle);
    }

    public static <T> void startActivityByContext(Context context, Class<T> clazz, Bundle params) {

        Intent intent = new Intent(context, clazz);
        intent.putExtras(params);
        context.startActivity(intent);
    }


    public static <T> void startActivity(Activity activity, Class<T> clazz, boolean killOlder, Bundle params, int requestCode, int flags) {
        //
        if (killOlder) activity.finish();
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(params);
        // Flags
        if (flags != -1) {
            intent.addFlags(flags);
        }
        //
        if (requestCode != -1) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz, boolean killOlder, Bundle params) {
        startActivity(activity, clazz, killOlder, params, -1, -1);
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz, Bundle params, int requestCode) {
        startActivity(activity, clazz, false, params, requestCode, -1);
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz, Bundle params) {
        startActivity(activity, clazz, false, params, -1, -1);
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz, boolean killOlder, HashMap<String, Object> params, int requestCode, int flags) {
        Bundle bundle = new Bundle();
        for (String name : params.keySet()) {
            Object val = params.get(name);
            if (val instanceof String) {
                bundle.putString(name, (String) val);
            } else if (val instanceof Integer) {
                bundle.putInt(name, (Integer) val);
            } else if (val instanceof Long) {
                bundle.putLong(name, (Long) val);
            } else if (val instanceof Short) {
                bundle.putShort(name, (Short) val);
            } else if (val instanceof Float) {
                bundle.putFloat(name, (Float) val);
            } else if (val instanceof Double) {
                bundle.putDouble(name, (Double) val);
            }
        }
        startActivity(activity, clazz, killOlder, bundle, requestCode, flags);
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz, boolean killOlder, HashMap<String, Object> params) {
        startActivity(activity, clazz, killOlder, params, -1, -1);
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz, HashMap<String, Object> params, int requestCode) {
        startActivity(activity, clazz, false, params, requestCode, -1);
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz, HashMap<String, Object> params) {
        startActivity(activity, clazz, false, params, -1, -1);
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz, boolean killOlder) {
        startActivity(activity, clazz, killOlder, new HashMap<String, Object>(), -1, -1);
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz, int flags) {
        startActivity(activity, clazz, false, new HashMap<String, Object>(), -1, flags);
    }

    public static <T> void startActivity(Activity activity, Class<T> clazz) {
        startActivity(activity, clazz, false, new HashMap<String, Object>(), -1, -1);
    }

    public static <T> void goHome(Activity activity, Class<T> clazz) {
        int flags = Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP;
        startActivity(activity, clazz, true, new HashMap<String, Object>(), -1, flags);
    }

}
