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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public class ModrinthProvider extends Provider {

    private static final String MODRINTH_API_URL =
            "https://api.modrinth.com/v2/project/%s/version";

    private UpdateData getUpdateDataFromElement(JsonElement element) {
        return new UpdateData(
                element.getAsJsonObject().get("name").getAsString(),
                element.getAsJsonObject().get("version_type").getAsString(),
                element.getAsJsonObject().get("version_number").getAsString(),
                element.getAsJsonObject().get("changelog").getAsString()
        );
    }

    @Override
    public UpdateData getLatest(JsonObject jsonObject) {
        if (jsonObject == null || jsonObject.getAsJsonArray().isEmpty()) {
            return null;
        }
        JsonElement element = jsonObject.getAsJsonArray().get(0);
        return getUpdateDataFromElement(element);
    }

    @Override
    public UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, JsonObject jsonObject) {
        if (jsonObject == null || jsonObject.isEmpty()) {
            return null;
        }

        for (JsonElement element : jsonObject.getAsJsonArray()) {
            boolean hasVersion;
            boolean hasLoader;

            Iterator<JsonElement> gameVersions = element.getAsJsonObject()
                    .get("game_versions").getAsJsonArray()
                    .iterator();
            hasVersion = this.iterateGameVersion(gameVersions, versions);

            Iterator<JsonElement> supportedLoaders = element.getAsJsonObject()
                    .get("loaders").getAsJsonArray()
                    .iterator();
            hasLoader = this.iterateLoaders(supportedLoaders, loaders);

            if (hasVersion && hasLoader) {
                return getUpdateDataFromElement(element);
            }
        }
        return null;
    }

    @Override
    public String getApiResponse(String pluginSlug) throws Exception {
        return this.getDefaultApiResponse(MODRINTH_API_URL, pluginSlug);
    }
}
