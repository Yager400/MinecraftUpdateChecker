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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public class ModrinthProvider implements Provider {

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
    public UpdateData getLatest(JsonArray array) {
        if (array == null || array.isEmpty()) {
            return null;
        }
        JsonElement element = array.get(0);
        return getUpdateDataFromElement(element);
    }

    @Override
    public UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, JsonArray array) {
        if (array == null || array.isEmpty()) {
            return null;
        }

        for (JsonElement element : array) {
            boolean hasVersion = false;
            boolean hasLoader = false;

            Iterator<JsonElement> gameVersions = element.getAsJsonObject()
                    .get("game_versions")
                    .getAsJsonArray()
                    .iterator();
            while (gameVersions.hasNext()) {
                String gameVersion = gameVersions.next().getAsString();
                for (LoaderVersion version : versions) {
                    if (gameVersion.equals(version.getVersion())) {
                        hasVersion = true;
                    }
                }
            }

            Iterator<JsonElement> supportedLoaders = element.getAsJsonObject()
                    .get("loaders")
                    .getAsJsonArray()
                    .iterator();
            while (supportedLoaders.hasNext()) {
                String supportedLoader = supportedLoaders.next().getAsString();
                for (SupportedLoaders loader : loaders) {
                    if (supportedLoader.equals(loader.getLoader())) {
                        hasLoader = true;
                    }
                }
            }

            if (hasVersion && hasLoader) {
                return getUpdateDataFromElement(element);
            }
        }
        return null;
    }

    @Override
    public String getApiResponse(String pluginSlug) throws Exception {
        URL url = URI.create(String.format(MODRINTH_API_URL, pluginSlug)).toURL();
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
