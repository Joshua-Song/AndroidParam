package com.joshua.androidparam.utils;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

import com.joshua.androidparam.bean.DisplayBean;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class DisplayUtil {
	public static DisplayBean getDisplayBean(Activity mActivity) {
		DisplayBean displayMode = new DisplayBean();
		DisplayMetrics metric = new DisplayMetrics();
		Display display = mActivity.getWindowManager().getDefaultDisplay();
		display.getMetrics(metric);
		float xdpi = metric.xdpi;//120 160 240 320
		float ydpi = metric.ydpi;
		displayMode.setXdpi(xdpi);
		displayMode.setYdpi(ydpi);

		float density = metric.density;// 0.75 / 1.0 / 1.5 =densityDpi/160
		int densityDpi = metric.densityDpi;// 120 / 160 / 240
		displayMode.setDensity(density);
		displayMode.setDensityDpi(densityDpi);

		int heightPixels = metric.heightPixels;
		int widthPixels = metric.widthPixels;
		float scaledDensity = metric.scaledDensity;  //= density
		displayMode.setHeightPixels(heightPixels);
		displayMode.setWidthPixels(widthPixels);
		displayMode.setScaledDensity(scaledDensity);

		
		int height = display.getHeight();
		int width = display.getWidth();
		displayMode.setHeight(height);
		displayMode.setWidth(width);
		Point point = new Point();
		display.getSize(point);
		String displaySize = point.toString();
		displayMode.setDisplaySize(displaySize);

		return displayMode;
	}
	public static DisplayBean getDisplayBean2(Activity mActivity) {
		DisplayBean displayMode = new DisplayBean();
		DisplayMetrics metric = mActivity.getResources().getDisplayMetrics();
		float xdpi = metric.xdpi;
		float ydpi = metric.ydpi;
		displayMode.setXdpi(xdpi);
		displayMode.setYdpi(ydpi);

		float density = metric.density;
		int densityDpi = metric.densityDpi;
		displayMode.setDensity(density);
		displayMode.setDensityDpi(densityDpi);

		int heightPixels = metric.heightPixels;
		float scaledDensity = metric.scaledDensity;
		int widthPixels = metric.widthPixels;
		displayMode.setHeightPixels(heightPixels);
		displayMode.setScaledDensity(scaledDensity);
		displayMode.setWidthPixels(widthPixels);

		return displayMode;
	}



}
