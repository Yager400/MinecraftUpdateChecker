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

public interface Provider {
    UpdateData getLatest(JsonArray array);
    UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, JsonArray array);
    String getApiResponse(String pluginSlug) throws Exception;
}
