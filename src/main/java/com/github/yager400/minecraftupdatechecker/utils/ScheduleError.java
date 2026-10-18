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

    /**
     * Get the thrown error
     * @return The error thrown as Throwable
     * @since 1.0.0
     */
    public Throwable getThrowable() {
        return this.throwable;
    }

    /**
     * Returns the schedule instance
     * @return The schedule instance that caused this error
     * @since 1.0.0
     */
    public Schedule getSchedule() {
        return this.schedule;
    }

}
