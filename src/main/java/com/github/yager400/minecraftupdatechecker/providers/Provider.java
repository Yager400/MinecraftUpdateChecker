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

/*
public interface Provider {
    UpdateData getLatest(JsonObject array);
    UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, JsonObject array);
    String getApiResponse(String pluginSlug) throws Exception;
}
 */

public abstract class Provider {

    public abstract UpdateData getLatest(JsonObject jsonObject);
    public abstract UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, JsonObject jsonObject);
    public abstract String getApiResponse(String pluginSlug) throws Exception;

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
}
