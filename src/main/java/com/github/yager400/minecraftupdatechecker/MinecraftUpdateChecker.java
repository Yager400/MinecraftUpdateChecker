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

    private LoaderVersion[] loaderVersionRange;
    private SupportedLoaders[] supportedLoaders;
    private Provider provider;
    private String thisVersion;
    private String pluginSlug;
    private Consumer<UpdateData> updatePresent;
    private Runnable noUpdate;
    private Consumer<ScheduleError> onError;
    private int checkEveryXHours = 1;
    private boolean checkNow = true;

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

    public MinecraftUpdateChecker setPluginSlug(String pluginSlug) {
        this.pluginSlug = pluginSlug;
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
        try {
            Objects.requireNonNull(loaderVersionRange);
            Objects.requireNonNull(supportedLoaders);
            Objects.requireNonNull(provider);
            Objects.requireNonNull(thisVersion);
            Objects.requireNonNull(pluginSlug);
            Objects.requireNonNull(updatePresent);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Schedule schedule = new Schedule();
        schedule.start(loaderVersionRange, supportedLoaders, provider, thisVersion, pluginSlug,
                       updatePresent, noUpdate, onError, checkEveryXHours, checkNow);
        return schedule;
    }

}
