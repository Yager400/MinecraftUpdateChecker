/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.providers;

import com.github.yager400.minecraftupdatechecker.UpdateData;
import com.github.yager400.minecraftupdatechecker.data.LoaderVersion;
import com.github.yager400.minecraftupdatechecker.data.SupportedLoaders;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public abstract class Provider {

    public abstract UpdateData getLatest(String json);
    public abstract UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, String json);
    public abstract String getApiResponse(String pluginIdentifier) throws Exception;

    public JsonObject parseJsonObject(String json) {
        JsonObject object;
        try {
            object = JsonParser.parseString(json).getAsJsonObject();
        } catch (NoSuchMethodError e) {
            // Use this if the gson version is too old
            object = new JsonParser().parse(json).getAsJsonObject();
        }
        return object;
    }

    public JsonArray parseJsonArray(String json) {
        JsonArray array;
        try {
            array = JsonParser.parseString(json).getAsJsonArray();
        } catch (NoSuchMethodError e) {
            // Use this if the gson version is too old
            array = new JsonParser().parse(json).getAsJsonArray();
        }
        return array;
    }

    public boolean iterateGameVersion(Iterator<JsonElement> iterator, LoaderVersion[] versions) {
        while (iterator.hasNext()) {
            String gameVersion = iterator.next().getAsString();
            for (LoaderVersion version : versions) {
                if (gameVersion.equals(version.getVersion())) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean iterateLoaders(Iterator<JsonElement> iterator, SupportedLoaders[] loaders) {
        while (iterator.hasNext()) {
            String gameVersion = iterator.next().getAsString();
            for (SupportedLoaders version : loaders) {
                if (gameVersion.equals(version.getLoader())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getDefaultApiResponse(String apiUrl, String pluginSlug) throws Exception {
        URL url = URI.create(String.format(apiUrl, pluginSlug)).toURL();
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }
}
