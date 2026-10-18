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

import java.util.Iterator;

public class ModrinthProvider extends Provider {

    private static final String MODRINTH_API_URL =
            "https://api.modrinth.com/v2/project/%s/version";

    private UpdateData getUpdateDataFromElement(JsonObject object) {
        return new UpdateData(
                object.get("name").getAsString(),
                object.get("version_type").getAsString(),
                object.get("version_number").getAsString(),
                object.get("changelog").getAsString()
        );
    }

    public JsonArray parse(String json) {
        return this.parseJsonArray(json);
    }

    @Override
    public UpdateData getLatest(String json) {
        JsonArray jsonArray = this.parse(json);
        if (jsonArray == null || jsonArray.getAsJsonArray().isEmpty()) {
            return null;
        }
        JsonElement element = jsonArray.get(0);
        return getUpdateDataFromElement(element.getAsJsonObject());
    }

    @Override
    public UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, String json) {
        JsonArray jsonArray = this.parse(json);
        if (jsonArray == null || jsonArray.isEmpty()) {
            return null;
        }

        for (JsonElement element : jsonArray) {
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
                return getUpdateDataFromElement(element.getAsJsonObject());
            }
        }
        return null;
    }

    @Override
    public String getApiResponse(String pluginIdentifier) throws Exception {
        return this.getDefaultApiResponse(MODRINTH_API_URL, pluginIdentifier);
    }
}
