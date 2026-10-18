/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.utils;

public final class ScheduleError {

    private final Throwable throwable;
    private final Schedule schedule;

    public ScheduleError(Throwable throwable, Schedule schedule) {
        this.throwable = throwable;
        this.schedule = schedule;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

}
