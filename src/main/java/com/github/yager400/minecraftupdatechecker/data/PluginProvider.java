/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.data;

import com.github.yager400.minecraftupdatechecker.providers.HangarProvider;
import com.github.yager400.minecraftupdatechecker.providers.ModrinthProvider;
import com.github.yager400.minecraftupdatechecker.providers.Provider;
import com.github.yager400.minecraftupdatechecker.providers.SpigotProvider;

public enum PluginProvider {

    MODRINTH    (new ModrinthProvider()),
    HANGAR      (new HangarProvider()),
    SPIGOT      (new SpigotProvider());

    private final Provider provider;

    PluginProvider(Provider provider) {
        this.provider = provider;
    }

    public Provider getProvider() {
        return this.provider;
    }
}
