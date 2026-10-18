/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker;

import com.github.yager400.minecraftupdatechecker.data.VersionType;

public final class UpdateData {

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

    /**
     * Get the plugin/mod version name
     * @return Plugin/Mod version name
     * @since 1.0.0
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the plugin/mod version type (if available)
     * @return The version type of this version (eg RELEASE, BETA and ALPHA)
     * @since 1.0.0
     */
    public VersionType getVersionType() {
        return this.versionType;
    }

    /**
     * Get the new plugin/mod version
     * @return Plugin/Mod version
     * @since 1.0.0
     */
    public String getNewVersion() {
        return this.newVersion;
    }

    /**
     * Get the changelogs/description of the plugin/mod version
     * @return Changelogs/description of the version
     * @since 1.0.0
     */
    public String getChangelogs() {
        return this.changelogs;
    }
}
