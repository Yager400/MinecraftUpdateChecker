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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Iterator;

public class SpigotProvider extends Provider {
    private static final String SPIGOT_API_URL =
            "https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=%s";

    private UpdateData getUpdateDataFromObject(JsonObject object) {
        return new UpdateData(
                object.get("title").getAsString(),
                "release",
                object.get("current_version").getAsString(),
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
        JsonObject object = jsonObject.getAsJsonArray().get(0).getAsJsonObject();
        return getUpdateDataFromObject(object);
    }

    @Override
    public UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, String json) {
        JsonObject jsonObject = this.parse(json);
        if (jsonObject == null || jsonObject.isEmpty()) {
            return null;
        }

        boolean hasVersion;

        Iterator<JsonElement> gameVersions = jsonObject.get("supported_minecraft_versions").getAsJsonArray()
                .iterator();
        hasVersion = this.iterateGameVersion(gameVersions, versions);

        if (hasVersion) {
            return getUpdateDataFromObject(jsonObject);
        }

        return null;
    }

    @Override
    public String getApiResponse(String pluginIdentifier) throws Exception {
        return this.getDefaultApiResponse(SPIGOT_API_URL, pluginIdentifier);
    }
}
