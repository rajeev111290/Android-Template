package com.roundlers.mytemplate.helper;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.util.ConcurrentModificationException;

public class GsonHelper {
    private static Gson gson;
    private static Gson dateGson;
    private static JsonParser jsonParser;

    private static synchronized Gson getInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    private static JsonParser getParser() {
        if (jsonParser == null) {
            jsonParser = new JsonParser();
        }
        return jsonParser;
    }

    public static <T> T fromJson(JsonElement source, Type type) {
        return getInstance().fromJson(source, type);
    }

    public static <T> T fromJson(JsonObject source, Type type) {
        return getInstance().fromJson(source, type);
    }

    public static <T> T fromJson(JsonPrimitive source, Type type) {
        return getInstance().fromJson(source, type);
    }

    public static <T> T fromJson(String source, Type type) {
        return getInstance().fromJson(source, type);
    }

    public static JsonElement parse(String source) {
        return getParser().parse(source);
    }

    public static String toJson(Object object) {
        try {
            return getInstance().toJson(object);
        } catch (ConcurrentModificationException e) {
            return "{}";
        }
    }

    public static JsonElement toJsonTree(Object o) {
        return gson.toJsonTree(o);
    }

}
