/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.utils;

import com.github.yager400.minecraftupdatechecker.UpdateData;
import com.github.yager400.minecraftupdatechecker.UpdateSettings;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Schedule {

    private static final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor(runnable -> {
                Thread t = new Thread(runnable, "MinecraftUpdateChecker");
                t.setDaemon(true);
                return t;
            });

    private volatile ScheduledFuture<?> future;

    public synchronized void start(UpdateSettings updateSettings) {
        if (this.future != null) {
            throw new IllegalStateException("Already started. Call stop() first.");
        }
        this.future = scheduler.scheduleWithFixedDelay(
                () -> check(updateSettings),
                updateSettings.isCheckNow() ? 0 : updateSettings.getCheckEveryXHours(),
                updateSettings.getCheckEveryXHours(),
                TimeUnit.HOURS
        );
    }

    private void check(UpdateSettings updateSettings) {
        try {
            String response = updateSettings.getProvider().getApiResponse(updateSettings.getPluginIdentifier());
            UpdateData updateData = updateSettings.getProvider().getLastSupportedVersion(
                    updateSettings.getLoaderVersionRange(),
                    updateSettings.getSupportedLoaders(),
                    response
            );
            if (updateData == null) {
                // The UpdateData is null when no update is found (even old ones), should we throw an exception or run the noUpdate runnable?
                //throw new NullPointerException("UpdateData is null! (No version was found)");
                updateSettings.getNoUpdate().run();
                return;
            }
            if (!updateData.getNewVersion().equals(updateSettings.getThisVersion())) {
                updateSettings.getUpdatePresent().accept(updateData);
            } else {
                updateSettings.getNoUpdate().run();
            }
        } catch (Throwable e) {
            try {
                updateSettings.getOnError().accept(new ScheduleError(e, this));
            } catch (Throwable ignored) {}
        }
    }


    public synchronized void stop() {
        ScheduledFuture<?> f = this.future;
        if (f != null) {
            f.cancel(false);
            this.future = null;
        }
    }

}
