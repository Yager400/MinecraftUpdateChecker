/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker;

import com.github.yager400.minecraftupdatechecker.data.MinecraftVersion;
import com.github.yager400.minecraftupdatechecker.data.Loader;
import com.github.yager400.minecraftupdatechecker.providers.Provider;
import com.github.yager400.minecraftupdatechecker.utils.ScheduleError;

import java.util.function.Consumer;

/**
 * Get every data from a MinecraftUpdateChecker and transform those into getters
 */
public final class UpdateSettings {

    private final MinecraftVersion[] minecraftVersionRange;
    private final Loader[] loaders;
    private final Provider provider;
    private final String thisVersion;
    private final String pluginIdentifier;
    private final Consumer<UpdateData> updatePresent;
    private final Runnable noUpdate;
    private final Consumer<ScheduleError> onError;
    private final int checkEveryXHours;
    private final boolean checkNow;

    public UpdateSettings(MinecraftUpdateChecker updateChecker) {
        this.minecraftVersionRange = updateChecker.minecraftVersionRange;
        this.loaders = updateChecker.loaders;
        this.provider = updateChecker.provider;
        this.thisVersion = updateChecker.thisVersion;
        this.pluginIdentifier = updateChecker.pluginIdentifier;
        this.updatePresent = updateChecker.updatePresent;
        this.checkEveryXHours = updateChecker.checkEveryXHours;
        this.checkNow = updateChecker.checkNow;

        this.noUpdate = updateChecker.noUpdate != null
                ? updateChecker.noUpdate
                : () -> {};

        this.onError = updateChecker.onError != null
                ? updateChecker.onError
                : err -> {};
    }

    public MinecraftVersion[] getLoaderVersionRange() {
        return this.minecraftVersionRange;
    }

    public Loader[] getSupportedLoaders() {
        return this.loaders;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public String getThisVersion() {
        return this.thisVersion;
    }

    public String getPluginIdentifier() {
        return this.pluginIdentifier;
    }

    public Consumer<UpdateData> getUpdatePresent() {
        return this.updatePresent;
    }

    public Runnable getNoUpdate() {
        return this.noUpdate;
    }

    public Consumer<ScheduleError> getOnError() {
        return this.onError;
    }

    public int getCheckEveryXHours() {
        return this.checkEveryXHours;
    }

    public boolean isCheckNow() {
        return this.checkNow;
    }
}
