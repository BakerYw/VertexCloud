package com.vertex.cloud.app.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import com.jess.arms.utils.ArmsUtils;
import com.vertex.cloud.R;

import org.jetbrains.annotations.NotNull;

/**
 * @Author CHEESE
 * @Date 10/22/21 9:24 AM
 * @Version 1.0
 **/
public class CloudUtils {
    public static ColorStateList getColorStateList(@NotNull Context context) {
        int[] colors = new int[]{ArmsUtils.getColor(context, R.color.colorPrimary),
                ArmsUtils.getColor(context, R.color.main_Bottom)};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{-android.R.attr.state_checked};
        return new ColorStateList(states, colors);
    }
}
