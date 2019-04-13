package com.lsm.androidx.tool.views;

public class World {
    public static float dbCount = 0.0f;
    private static float lastDbCount = dbCount;
    private static float min = 0.7f;
    private static float value = 0.0f;

    public static void setDbCount(float dbValue) {
        if (dbValue > lastDbCount) {
            value = dbValue - lastDbCount > min ? dbValue - lastDbCount : min;
        } else {
            value = dbValue - lastDbCount < (-min) ? dbValue - lastDbCount : -min;
        }
        dbCount = lastDbCount + (value * 0.2f);
        lastDbCount = dbCount;
    }
}
