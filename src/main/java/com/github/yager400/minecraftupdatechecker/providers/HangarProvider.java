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

public class HangarProvider implements Provider {

    private static final String HANGAR_API_URL =
            "https://hangar.papermc.io/api/v1/projects/%s/versions";

    private UpdateData getUpdateDataFromElement(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        String name = object.get("name").getAsString();
        String version = object.get("name").getAsString();
        String versionType = object.has("channel")
                ? object.getAsJsonObject("channel").get("name").getAsString()
                : "release";
        String changelog = object.has("description")
                && !object.get("description").isJsonNull()
                ? object.get("description").getAsString()
                : "";

        return new UpdateData(name, versionType, version, changelog);
    }

    @Override
    public UpdateData getLatest(JsonArray array) {
        if (array == null || array.isEmpty()) {
            return null;
        }
        JsonObject response = array.get(0).getAsJsonObject();
        if (response.has("result")) {
            JsonArray versions = response.getAsJsonArray("result");
            return versions.isEmpty()
                    ? null
                    : getUpdateDataFromElement(versions.get(0));
        }
        return getUpdateDataFromElement(array.get(0));
    }

    @Override
    public UpdateData getLastSupportedVersion(
            LoaderVersion[] versions,
            SupportedLoaders[] loaders,
            JsonArray array
    ) {
        if (array == null) {
            return null;
        }

        for (JsonElement element : array) {
            JsonObject object = element.getAsJsonObject();
            if (!object.has("platformDependencies")
                    || object.get("platformDependencies").isJsonNull()) {
                continue;
            }
            JsonObject dependencies =
                    object.getAsJsonObject("platformDependencies");

            boolean hasVersion = false;
            boolean hasLoader = false;
            for (SupportedLoaders loader : loaders) {
                String platform = loader.getLoader().toUpperCase();

                if (!dependencies.has(platform)
                        || dependencies.get(platform).isJsonNull()) {
                    continue;
                }

                hasLoader = true;

                JsonArray gameVersions = dependencies.getAsJsonArray(platform);

                for (JsonElement gameVersion : gameVersions) {
                    String supportedVersion = gameVersion.getAsString();

                    for (LoaderVersion version : versions) {
                        if (supportedVersion.equals(version.getVersion())) {
                            hasVersion = true;
                            break;
                        }
                    }

                    if (hasVersion) {
                        break;
                    }
                }

                if (hasLoader && hasVersion) {
                    break;
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
        URL url = URI.create(
                String.format(HANGAR_API_URL, pluginSlug)
        ).toURL();

        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty(
                "Accept",
                "application/json"
        );

        StringBuilder response = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream(),
                        StandardCharsets.UTF_8
                )
        )) {
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }
}