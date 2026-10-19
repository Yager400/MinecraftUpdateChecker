/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker;

import com.github.yager400.minecraftupdatechecker.data.MinecraftVersion;
import com.github.yager400.minecraftupdatechecker.data.PluginProvider;
import com.github.yager400.minecraftupdatechecker.data.Loader;
import com.github.yager400.minecraftupdatechecker.providers.Provider;
import com.github.yager400.minecraftupdatechecker.utils.Schedule;
import com.github.yager400.minecraftupdatechecker.utils.ScheduleError;

import java.util.Objects;
import java.util.function.Consumer;

public class MinecraftUpdateChecker {

    protected MinecraftVersion[] minecraftVersionRange;
    protected Loader[] loaders;
    protected Provider provider;
    protected String thisVersion;
    protected String pluginIdentifier;
    protected Consumer<UpdateData> updatePresent;
    protected Runnable noUpdate;
    protected Consumer<ScheduleError> onError;
    protected int checkEveryXHours = 1;
    protected boolean checkNow = true;

    /**
     * Sets the plugin/mod supported versions.
     * @param from The oldest version your plugin/mod supports
     * @param to The newest version your plugin/mod supports.<br>
     *           You can also use LoaderVersion.LATEST if you are sure that your plugin/mod will work for the next versions
     * @return This instance
     * @since 1.0.0
     * @Required this field is required
     */
    public MinecraftUpdateChecker setMinecraftVersionRange(MinecraftVersion from, MinecraftVersion to) {
        this.minecraftVersionRange = MinecraftVersion.getRange(from, to);
        return this;
    }

    /**
     * Sets the loaders for your plugin/mod.
     * @param loaders The plugin/mod loader. If the plugin provider supports it, you will get every update for your loader (eg only for velocity)
     * @return This instance
     * @since 1.0.0
     * @Required this field is required
     */
    public MinecraftUpdateChecker setLoaders(Loader... loaders) {
        this.loaders = loaders;
        return this;
    }

    /**
     * Sets the place where your plugin/mod is hosted
     * @param provider The plugin/mod provider (eg Modrinth, Hangar or Spigot)
     * @return This instance
     * @since 1.0.0
     * @Required this field is required
     */
    public MinecraftUpdateChecker setPluginProvider(PluginProvider provider) {
        this.provider = provider.getProvider();
        return this;
    }

    /**
     * Set a custom plugin/mod provider, likely one that you made
     * @param provider Your plugin/mod provider instance
     * @see MinecraftUpdateChecker#setPluginProvider(PluginProvider)  If you want to use an already existing provider (racomanded)
     * @return This instance
     * @since 1.0.0
     */
    public MinecraftUpdateChecker setCustomPluginProvider(Provider provider) {
        this.provider = provider;
        return this;
    }

    /**
     * Set this plugin/mod version that will be compared from the newer/older ones
     * @param version Your plugin/mod version as string
     * @return This instance
     * @since 1.0.0
     * @Required this field is required
     */
    public MinecraftUpdateChecker setVersion(String version) {
        this.thisVersion = version;
        return this;
    }

    /**
     * Sets the plugin/mod identifier.<br> This can be different based of your plugin provider (eg for Modrinth if the slug, Hangar the name and Spigot the id)
     * @param pluginIdentifier The plugin/mod identifier as string
     * @return This instance
     * @since 1.0.0
     * @Required this field is required
     */
    public MinecraftUpdateChecker setPluginIdentifier(String pluginIdentifier) {
        this.pluginIdentifier = pluginIdentifier;
        return this;
    }

    /**
     * Sets a consumer that will be run when a new update is present
     * @param updatePresent Your consumer
     * @return This instance
     * @since 1.0.0
     * @Required this field is required
     */
    public MinecraftUpdateChecker onUpdatePresent(Consumer<UpdateData> updatePresent) {
        this.updatePresent = updatePresent;
        return this;
    }

    /**
     * Run this runnable if there is no update
     * @param noUpdate Your runnable
     * @return This instance
     * @since 1.0.0
     */
    public MinecraftUpdateChecker onNoUpdatePresent(Runnable noUpdate) {
        this.noUpdate = noUpdate;
        return this;
    }

    /**
     * Sets a consumer that will run when an error is thrown during the update check
     * @param onError Your consumer
     * @return This instance
     * @since 1.0.0
     */
    public MinecraftUpdateChecker onError(Consumer<ScheduleError> onError) {
        this.onError = onError;
        return this;
    }

    /**
     * Sets the interval (in hours) while checking for a new update<br>
     * <b>If an error is thrown, the next check will still happen after X hours</b><br>
     * <b>By default, is 1</b>
     * @param hours Hours to wait between every check
     * @return This instance
     * @since 1.0.0
     */
    public MinecraftUpdateChecker checkEveryXHours(int hours) {
        this.checkEveryXHours = hours;
        return this;
    }

    /**
     * Should the update check being done instantly when the plugin/mod starts.<br>
     * <b>By default, is true</b>
     * @param checkNow True to check now, otherwise false
     * @return This instance
     * @since 1.0.0
     */
    public MinecraftUpdateChecker checkNow(boolean checkNow) {
        this.checkNow = checkNow;
        return this;
    }

    /**
     * Stars the update async schedule
     * @return The schedule instance
     */
    public Schedule start() {
        Objects.requireNonNull(minecraftVersionRange,  "The loader version range cannot be null!");
        Objects.requireNonNull(loaders,    "The supported loaders cannot be null!");
        Objects.requireNonNull(provider,            "The plugin provider cannot be null!");
        Objects.requireNonNull(thisVersion,         "The actual version of this plugin cannot be null!");
        Objects.requireNonNull(pluginIdentifier,    "The plugin identifier cannot be null!");
        Objects.requireNonNull(updatePresent,       "The update present's consumer cannot be null!");

        Schedule schedule = new Schedule();
        schedule.start(new UpdateSettings(this));
        return schedule;
    }

}
