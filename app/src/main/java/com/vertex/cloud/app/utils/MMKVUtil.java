package com.vertex.cloud.app.utils;

import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Set;

/**
 * @Author CHEESE
 * @Date 2021-10-22 22:54
 * @Version 1.0
 * @Describe
 **/
public class MMKVUtil {

    private static final String PREFERENCE_NAME = "config";

    private static MMKVUtil INSTANCE;

    private final MMKV mmkv;

    private MMKVUtil() {
        mmkv = MMKV.defaultMMKV();

//        Sp迁移到MMV
//        SharedPreferences sharedPreferences = XTApplication.getAppContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
//        if (!sharedPreferences.getAll().isEmpty()) {
//            mmkv.importFromSharedPreferences(sharedPreferences);
//            sharedPreferences.edit().clear().apply();
//        }
    }

    public static MMKVUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (PREFERENCE_NAME) {
                if (INSTANCE == null) {
                    INSTANCE = new MMKVUtil();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public <T> void putValue(String key, T object) {
        if (object instanceof String) {
            mmkv.encode(key, (String) object);
        } else if (object instanceof Integer) {
            mmkv.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mmkv.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            mmkv.encode(key, (Float) object);
        } else if (object instanceof Long) {
            mmkv.encode(key, (Long) object);
        } else if (object instanceof Double) {
            mmkv.encode(key, (Double) object);
        } else if (object instanceof Set) {
            mmkv.encode(key, (Set<String>) object);
        } else if (object instanceof Parcelable) {
            mmkv.encode(key, (Parcelable) object);
        } else {
            mmkv.encode(key, object == null ? "" : object.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public <T> T getValue(String key, T defaultObject) {
        if (defaultObject instanceof String || defaultObject == null) {
            return (T) mmkv.decodeString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return (T) Integer.valueOf(mmkv.decodeInt(key, (Integer) defaultObject));
        } else if (defaultObject instanceof Boolean) {
            return (T) Boolean.valueOf(mmkv.decodeBool(key, (Boolean) defaultObject));
        } else if (defaultObject instanceof Float) {
            return (T) Float.valueOf(mmkv.decodeFloat(key, (Float) defaultObject));
        } else if (defaultObject instanceof Long) {
            return (T) Long.valueOf(mmkv.decodeLong(key, (Long) defaultObject));
        } else if (defaultObject instanceof Double) {
            return (T) Double.valueOf(mmkv.decodeDouble(key, (Double) defaultObject));
        } else if (defaultObject instanceof Parcelable) {
            Parcelable p = (Parcelable) defaultObject;
            return (T) mmkv.decodeParcelable(key, p.getClass());
        } else {
            return null;
        }
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        mmkv.remove(key);
    }

    /**
     * 查询某个key是否已经存在
     */
    public boolean contains(String key) {
        return mmkv.contains(key);
    }

}
