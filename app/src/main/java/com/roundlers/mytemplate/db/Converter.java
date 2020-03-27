package com.roundlers.mytemplate.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.roundlers.mytemplate.helper.GsonHelper;

import org.w3c.dom.Comment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;


public class Converter {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return GsonHelper.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        return GsonHelper.toJson(list);
    }

    @TypeConverter
    public static JsonObject fromStr(String value) {
        if (value == null || value.equals("null")) {
            return null;
        }
        return GsonHelper.fromJson(value, JsonObject.class);
    }

    @TypeConverter
    public static String fromJson(JsonObject jsonObject) {
        return GsonHelper.toJson(jsonObject);
    }

    @TypeConverter
    public static Comment getCommentFromString(String value) {
        return GsonHelper.fromJson(value, Comment.class);
    }

    @TypeConverter
    public static String fromCommentJson(Comment comment) {
        return GsonHelper.toJson(comment);
    }

    @TypeConverter
    public static Integer[] getIntegerArrayFromString(String str) {
        Type listType = new TypeToken<Integer[]>() {
        }.getType();
        return GsonHelper.fromJson(str, listType);
    }

    @TypeConverter
    public static String fromIntegerListJson(Integer[] integers) {
        return GsonHelper.toJson(integers);
    }

    @TypeConverter
    public static int[] getPrimitiveIntegerArrayFromString(String str) {
        Type listType = new TypeToken<int[]>() {
        }.getType();
        return GsonHelper.fromJson(str, listType);
    }

    @TypeConverter
    public static String fromPrimitiveIntegerListJson(int[] integers) {
        return GsonHelper.toJson(integers);
    }

//    @TypeConverter
//    public static FeedItem getFeedItemFromString(String str) {
//        return GsonHelper.fromJson(str, FeedItem.class);
//    }


    @TypeConverter
    public static String intArraylistToStr(ArrayList<Integer> list) {
        return GsonHelper.toJson(list);
    }

    @TypeConverter
    public static ArrayList<Integer> strToIntArraylist(String commentUserMentions) {
        return GsonHelper.fromJson(commentUserMentions, new TypeToken<ArrayList<Integer>>() {
        }.getType());
    }

    @TypeConverter
    public static String stringhashMapExam(HashMap<String, String> examHashMap) {
        return GsonHelper.toJson(examHashMap);
    }

    @TypeConverter
    public static HashMap<String, String> strToHashMapExam(String examHashMap) {
        Type listType = new TypeToken<HashMap<String, String>>() {
        }.getType();
        return GsonHelper.fromJson(examHashMap, listType);
    }

//
//    @TypeConverter
//    public static ArrayList<Exam> fromExamString(String value) {
//        Type listType = new TypeToken<ArrayList<Exam>>() {
//        }.getType();
//        return GsonHelper.fromJson(value, listType);
//    }

    @TypeConverter
    public static String stringIntegereHashMap(HashMap<String, Integer> hashMap) {
        return GsonHelper.toJson(hashMap);
    }

    @TypeConverter
    public static HashMap<String, Integer> strIntegerToHashmap(String hashMap) {
        Type listType = new TypeToken<HashMap<String, Integer>>() {
        }.getType();
        return GsonHelper.fromJson(hashMap, listType);
    }

    @TypeConverter
    public static String strArray(String[] stringArray) {
        String stringified = "";
        if (stringArray == null) {
            return stringified;
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < stringArray.length; i++) {
            sb.append(stringArray[i]).append(",,");

        }
        return sb.toString();
    }

    @TypeConverter
    public static String[] strToArray(String stringified) {
        String[] stringArray;
        stringArray = stringified.split(",,");
        return stringArray;
    }
}
