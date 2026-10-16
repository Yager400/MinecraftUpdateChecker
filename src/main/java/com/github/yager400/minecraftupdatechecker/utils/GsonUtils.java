/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtils {

    protected static JsonObject parseJsonObject(String responseBody) {
        JsonObject array;
        try {
            array = JsonParser.parseString(responseBody).getAsJsonObject();
        } catch (NoSuchMethodError e) {
            // Use this if the gson version is too old
            array = new JsonParser().parse(responseBody).getAsJsonObject();
        }
        return array;
    }
}
