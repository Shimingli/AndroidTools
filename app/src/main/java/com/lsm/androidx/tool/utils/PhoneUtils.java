package com.lsm.androidx.tool.utils;

import android.content.Context;
import android.os.Build;

import com.lsm.androidx.tool.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class PhoneUtils {
    private static float inchHeight = 0.0f;
    private static float mmPerPx = 5.0f;

    public static Map<String, Object> getModelMap(Context context) {
        return JsonUtils.getDataMap(getString(context.getResources().openRawResource(R.raw.model)));
    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
                sb.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static float getInchHeight(int iscreenWidth, int iscreenHeight, Context context) {
        String model = Build.MODEL;
        if (getModelMap(context) != null && getModelMap(context).containsKey(model)) {
            mmPerPx = Float.parseFloat(getModelMap(context).get(model) + "");
        }
        inchHeight = (float) Math.sqrt((double) ((float) (Math.pow((double) mmPerPx, 2.0d) / ((double) (((float) Math.pow(((double) iscreenWidth) / ((double) iscreenHeight), 2.0d)) + 1.0f)))));
        return inchHeight;
    }
}
