/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker;

import com.github.yager400.minecraftupdatechecker.data.LoaderVersion;
import com.github.yager400.minecraftupdatechecker.data.PluginProviders;
import com.github.yager400.minecraftupdatechecker.data.SupportedLoaders;
import com.github.yager400.minecraftupdatechecker.providers.Provider;
import com.github.yager400.minecraftupdatechecker.utils.Schedule;
import com.github.yager400.minecraftupdatechecker.utils.ScheduleError;

import java.util.Objects;
import java.util.function.Consumer;

public class MinecraftUpdateChecker {

    public LoaderVersion[] loaderVersionRange;
    public SupportedLoaders[] supportedLoaders;
    public Provider provider;
    public String thisVersion;
    public String pluginIdentifier;
    public Consumer<UpdateData> updatePresent;
    public Runnable noUpdate;
    public Consumer<ScheduleError> onError;
    public int checkEveryXHours = 1;
    public boolean checkNow = true;

    public MinecraftUpdateChecker setLoaderVersionRange(LoaderVersion from, LoaderVersion to) {
        this.loaderVersionRange = LoaderVersion.getRange(from, to);
        return this;
    }

    public MinecraftUpdateChecker setSupportedLoader(SupportedLoaders... loaders) {
        this.supportedLoaders = loaders;
        return this;
    }

    public MinecraftUpdateChecker setPluginProvider(PluginProviders provider) {
        this.provider = provider.getProvider();
        return this;
    }

    public MinecraftUpdateChecker setVersion(String version) {
        this.thisVersion = version;
        return this;
    }

    public MinecraftUpdateChecker setPluginIdentifier(String pluginIdentifier) {
        this.pluginIdentifier = pluginIdentifier;
        return this;
    }

    public MinecraftUpdateChecker onUpdatePresent(Consumer<UpdateData> updatePresent) {
        this.updatePresent = updatePresent;
        return this;
    }

    public MinecraftUpdateChecker onNoUpdatePresent(Runnable noUpdate) {
        this.noUpdate = noUpdate;
        return this;
    }

    public MinecraftUpdateChecker onError(Consumer<ScheduleError> onError) {
        this.onError = onError;
        return this;
    }

    public MinecraftUpdateChecker checkEveryXHours(int hours) {
        this.checkEveryXHours = hours;
        return this;
    }

    public MinecraftUpdateChecker checkNow(boolean checkNow) {
        this.checkNow = checkNow;
        return this;
    }

    public Schedule start() {
        Objects.requireNonNull(loaderVersionRange,  "The loader version range cannot be null!");
        Objects.requireNonNull(supportedLoaders,    "The supported loaders cannot be null!");
        Objects.requireNonNull(provider,            "The plugin provider cannot be null!");
        Objects.requireNonNull(thisVersion,         "The actual version of this plugin cannot be null!");
        Objects.requireNonNull(pluginIdentifier,    "The plugin identifier cannot be null!");
        Objects.requireNonNull(updatePresent,       "The update present's consumer cannot be null!");

        Schedule schedule = new Schedule();
        schedule.start(this);
        return schedule;
    }

}
