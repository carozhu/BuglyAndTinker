package com.ckr.upgrade.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

import static com.ckr.upgrade.util.UpgradeLog.Logd;

/**
 * Created by ckr on 2018/11/12.
 */

public class ApkUtil {
	private static final String TAG = "ApkUtil";

	/**
	 * 获取apk名
	 *
	 * @param apkUrl apk下载链接
	 * @return
	 */
	@NonNull
	public static String getApkName(String apkUrl) {
		if (TextUtils.isEmpty(apkUrl)) {
			return null;
		}
		int beginIndex = apkUrl.lastIndexOf("/") + 1;
		return apkUrl.substring(beginIndex, apkUrl.length());
	}

	/**
	 * 获取apk存储路径
	 *
	 * @param apkUrl  apk下载路径
	 * @param context
	 * @return
	 */
	@NonNull
	public static String getApkPath(String apkUrl, @NonNull Context context) {
		String apkName = getApkName(apkUrl);
		if (TextUtils.isEmpty(apkName)) {
			return null;
		}
		return context.getExternalFilesDir(null).getAbsolutePath() + File.separator + apkName;
	}

	/**
	 * 安装apk
	 *
	 * @param path apk路径
	 */
	public static boolean installApk(String path, @NonNull Context context) {
		Logd(TAG, "installApk: path:" + path);
		File apkFile = new File(path);
		if (!apkFile.exists()) {
			return false;
		}
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri uri = parUri(apkFile, context);
			Logd(TAG, "installApk: uri:" + uri);
			intent.setDataAndType(uri, "application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return true;
		} catch (Exception e) {
			Logd(TAG, "installApk: exception");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取文件Uri
	 *
	 * @param file
	 * @param context
	 * @return
	 */
	public static Uri parUri(File file, @NonNull Context context) {
		Uri imageUri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
		} else {
			imageUri = Uri.fromFile(file);
		}
		return imageUri;
	}
}
