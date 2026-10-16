/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.data;

import com.github.yager400.minecraftupdatechecker.providers.ModrinthProvider;
import com.github.yager400.minecraftupdatechecker.providers.Provider;

public enum PluginProviders {

    MODRINTH(new ModrinthProvider()),
    /**
     * @Experimental This plugin provider is not fully completed and might
     * be instable.<br> Proceed carefully.
     */
    HANGAR(new ModrinthProvider());

    private final Provider provider;

    PluginProviders(Provider provider) {
        this.provider = provider;
    }

    public Provider getProvider() {
        return this.provider;
    }
}
