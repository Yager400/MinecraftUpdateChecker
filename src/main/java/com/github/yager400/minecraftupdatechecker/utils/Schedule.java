/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.utils;

import com.github.yager400.minecraftupdatechecker.UpdateData;
import com.github.yager400.minecraftupdatechecker.data.LoaderVersion;
import com.github.yager400.minecraftupdatechecker.data.SupportedLoaders;
import com.github.yager400.minecraftupdatechecker.providers.Provider;
import com.google.gson.JsonArray;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Schedule {

    private CompletableFuture<Void> schedule;
    // Use this as fallback if this schedule doesn't stop
    private boolean stopFallBack = false;

    public void start(
            LoaderVersion[] loaderVersionRange,
            SupportedLoaders[] supportedLoaders,
            Provider provider,
            String thisVersion,
            String pluginSlug,
            Consumer<UpdateData> updatePresent,
            Runnable noUpdate,
            Consumer<ScheduleError> onError,
            int checkEveryXHours,
            boolean checkNow
    ) {
        if (noUpdate == null) {
            noUpdate = () -> {};
        }
        if (onError == null) {
            onError = err -> {};
        }
        this.startSchedule(loaderVersionRange, supportedLoaders, provider, thisVersion, pluginSlug,
                            updatePresent, noUpdate, onError, checkEveryXHours, checkNow);
    }

    private void startSchedule(
            LoaderVersion[] loaderVersionRange,
            SupportedLoaders[] supportedLoaders,
            Provider provider,
            String thisVersion,
            String pluginSlug,
            Consumer<UpdateData> updatePresent,
            Runnable noUpdate,
            Consumer<ScheduleError> onError,
            int checkEveryXHours,
            boolean checkNow
    ) {
        if (this.schedule != null) {
            throw new RuntimeException("Another schedule is already running!. Do schedule.stop() before.");
        }
        this.schedule = CompletableFuture.runAsync(() -> {
            if (!checkNow) {
                try {
                    TimeUnit.HOURS.sleep(checkEveryXHours);
                } catch (InterruptedException e) {throw new RuntimeException(e);}
            }

            while (true) {
                if (stopFallBack) {
                    return;
                }
                try {
                    String response = provider.getApiResponse(pluginSlug);
                    JsonArray array = GsonUtils.parseJsonArray(response);
                    UpdateData updateData = provider.getLastSupportedVersion(
                            loaderVersionRange,
                            supportedLoaders,
                            array
                    );
                    if (!updateData.getNewVersion().equals(thisVersion)) {
                        updatePresent.accept(updateData);
                    } else {
                        noUpdate.run();
                    }

                    TimeUnit.HOURS.sleep(checkEveryXHours);
                } catch (Exception e) {
                    onError.accept(new ScheduleError(e, this));
                    try {
                        TimeUnit.HOURS.sleep(checkEveryXHours);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            }
        });
    }

    public void stop() {
        this.schedule.cancel(true);
        this.stopFallBack = true;
    }

    public CompletableFuture<Void> getFuture() {
        return this.schedule;
    }

}
