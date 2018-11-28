package com.ckr.upgrade.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ckr.upgrade.dialog.UpgradeDialogFragment;

/**
 * Created by ckr on 2018/11/10.
 */

public class AppTracker implements Application.ActivityLifecycleCallbacks, UpgradeDialogFragment.OnDialogClickListener {
    private static final String TAG = "AppTracker";
    private boolean canShow;
    private Activity activity;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void showDialog(boolean isShow) {
        UpgradeLog.Logd(TAG, "showDialog: isShow:" + isShow + ",activity:" + activity);
        canShow = isShow;
        if (!canShow) {
            return;
        }
        if (activity == null) {
            return;
        }
        canShow = false;
        UpgradeDialogFragment dialogFragment = new UpgradeDialogFragment.Builder()
                .setOnDialogClickListener(this)
                .build();
        if (activity instanceof FragmentActivity) {
            dialogFragment.showAllowingStateLoss(((FragmentActivity) activity).getSupportFragmentManager(), UpgradeDialogFragment.class.getSimpleName());
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
//        if (activity instanceof MainActivity) {
//            Logd(TAG, "onActivityResumed: ");
//            this.activity = activity;
//            showDialog(canShow);
//        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
//        if (activity instanceof MainActivity) {
//            Logd(TAG, "onActivityPaused: ");
//            this.activity = null;
//        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onPositiveClick() {
//        if (activity != null) {
//            Intent intent = new Intent(activity, DownLoadService.class);
//            activity.startService(intent);
//        }
    }
}