/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker;

import com.github.yager400.minecraftupdatechecker.data.VersionType;

public class UpdateData {

    private final String name;
    private final VersionType versionType;
    private final String newVersion;
    private final String changelogs;

    public UpdateData(String name, String versionType,
                      String newVersion,
                      String changelogs) {
        this.name = name;
        this.versionType = VersionType.valueOf(versionType.toUpperCase());
        this.newVersion = newVersion;
        this.changelogs = changelogs;
    }

    public String getName() {
        return this.name;
    }

    public VersionType getVersionType() {
        return this.versionType;
    }

    public String getNewVersion() {
        return this.newVersion;
    }

    public String getChangelogs() {
        return this.changelogs;
    }
}
