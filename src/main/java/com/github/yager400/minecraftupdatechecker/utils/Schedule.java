/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.utils;

import com.github.yager400.minecraftupdatechecker.MinecraftUpdateChecker;
import com.github.yager400.minecraftupdatechecker.UpdateData;
import com.github.yager400.minecraftupdatechecker.data.LoaderVersion;
import com.github.yager400.minecraftupdatechecker.data.SupportedLoaders;
import com.github.yager400.minecraftupdatechecker.providers.Provider;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Schedule {

    private CompletableFuture<Void> schedule;
    // Use this as fallback if this schedule doesn't stop
    private boolean stopFallBack = false;

    public void start(MinecraftUpdateChecker updateChecker) {
        if (updateChecker.noUpdate == null) {
            updateChecker.noUpdate = () -> {};
        }
        if (updateChecker.onError == null) {
            updateChecker.onError = err -> {};
        }
        this.startSchedule(updateChecker);
    }

    private void startSchedule(MinecraftUpdateChecker updateChecker) {
        if (this.schedule != null) {
            throw new RuntimeException("Another schedule is already running!. Do schedule.stop() before.");
        }
        this.schedule = CompletableFuture.runAsync(() -> {
            if (!updateChecker.checkNow) {
                try {
                    TimeUnit.HOURS.sleep(updateChecker.checkEveryXHours);
                } catch (InterruptedException e) {throw new RuntimeException(e);}
            }

            while (true) {
                if (stopFallBack) {
                    return;
                }
                try {
                    String response = updateChecker.provider.getApiResponse(updateChecker.pluginIdentifier);
                    UpdateData updateData = updateChecker.provider.getLastSupportedVersion(
                            updateChecker.loaderVersionRange,
                            updateChecker.supportedLoaders,
                            response
                    );
                    if (updateData == null) {
                        throw new NullPointerException("UpdateData is null!");
                    }
                    if (!updateData.getNewVersion().equals(updateChecker.thisVersion)) {
                        updateChecker.updatePresent.accept(updateData);
                    } else {
                        updateChecker.noUpdate.run();
                    }

                    TimeUnit.HOURS.sleep(updateChecker.checkEveryXHours);
                } catch (Exception e) {
                    updateChecker.onError.accept(new ScheduleError(e, this));
                    try {
                        TimeUnit.HOURS.sleep(updateChecker.checkEveryXHours);
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
        this.schedule = null;
    }

    public CompletableFuture<Void> getFuture() {
        return this.schedule;
    }

}
