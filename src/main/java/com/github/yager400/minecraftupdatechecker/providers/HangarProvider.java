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
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class HangarProvider extends Provider {

    private static final String HANGAR_API_URL =
            "https://hangar.papermc.io/api/v1/projects/%s/versions";

    private UpdateData getUpdateDataFromElement(JsonObject object) {
        return new UpdateData(
                object.get("name").getAsString(),
                object.get("channel").getAsJsonObject().get("name").getAsString(),
                object.get("name").getAsString(),
                object.get("description").getAsString()
        );
    }

    public JsonObject parse(String json) {
        return this.parseJsonObject(json);
    }

    @Override
    public UpdateData getLatest(String json) {
        JsonObject jsonObject = this.parse(json);
        if (jsonObject == null || jsonObject.getAsJsonArray().isEmpty()) {
            return null;
        }
        JsonElement element = jsonObject.get("result").getAsJsonArray().get(0);
        return getUpdateDataFromElement(element.getAsJsonObject());
    }

    @Override
    public UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, String json) {
        JsonObject jsonObject = this.parse(json);
        if (jsonObject == null) {
            return null;
        }
        JsonArray resultArray = jsonObject.get("result").getAsJsonArray();
        for (JsonElement element : resultArray) {
            boolean hasVersion;
            boolean hasLoader = false;

            Iterator<JsonElement> gameVersions = element.getAsJsonObject()
                    .get("platformDependencies").getAsJsonObject()
                    .get("PAPER").getAsJsonArray()
                    .iterator();
            hasVersion = this.iterateGameVersion(gameVersions, versions);

            Set<String> supportedLoaders = element.getAsJsonObject()
                    .get("platformDependenciesFormatted").getAsJsonObject().keySet();
            for (String loaderVersion : supportedLoaders) {
                for (SupportedLoaders version : loaders) {
                    if (loaderVersion.equals(version.getLoader().toUpperCase())) {
                        hasLoader = true;
                        break;
                    }
                }
            }

            if (hasVersion && hasLoader) {
                return getUpdateDataFromElement(element.getAsJsonObject());
            }
        }

        return null;
    }

    @Override
    public String getApiResponse(String pluginIdentifier) throws Exception {
        return this.getDefaultApiResponse(HANGAR_API_URL, pluginIdentifier);
    }
}