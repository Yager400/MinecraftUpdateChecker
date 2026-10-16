/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.utils;

public class ScheduleError {

    private final Exception exception;
    private final Schedule schedule;

    public ScheduleError(Exception exception, Schedule schedule) {
        this.exception = exception;
        this.schedule = schedule;
    }

    public Exception getException() {
        return this.exception;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

}
