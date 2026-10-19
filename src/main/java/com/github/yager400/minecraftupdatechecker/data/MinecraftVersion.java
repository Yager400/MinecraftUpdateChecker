/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.data;

import java.util.Arrays;

public enum MinecraftVersion {

    LATEST      ("26.3"),
    V26_3       ("26.3"),
    V26_2       ("26.2"),
    V26_1_2     ("26.1.2"),
    V26_1_1     ("26.1.1"),
    V26_1       ("26.1"),
    V1_21_11    ("1.21.11"),
    V1_21_10    ("1.21.10"),
    V1_21_9     ("1.21.9"),
    V1_21_8     ("1.21.8"),
    V1_21_7     ("1.21.7"),
    V1_21_6     ("1.21.6"),
    V1_21_5     ("1.21.5"),
    V1_21_4     ("1.21.4"),
    V1_21_3     ("1.21.3"),
    V1_21_2     ("1.21.2"),
    V1_21_1     ("1.21.1"),
    V1_21       ("1.21"),
    V1_20_6     ("1.20.6"),
    V1_20_5     ("1.20.5"),
    V1_20_4     ("1.20.4"),
    V1_20_3     ("1.20.3"),
    V1_20_2     ("1.20.2"),
    V1_20_1     ("1.20.1"),
    V1_20       ("1.20"),
    V1_19_4     ("1.19.4"),
    V1_19_3     ("1.19.3"),
    V1_19_2     ("1.19.2"),
    V1_19_1     ("1.19.1"),
    V1_19       ("1.19"),
    V1_18_2     ("1.18.2"),
    V1_18_1     ("1.18.1"),
    V1_18       ("1.18"),
    V1_17_1     ("1.17.1"),
    V1_17       ("1.17"),
    V1_16_5     ("1.16.5"),
    V1_16_4     ("1.16.4"),
    V1_16_3     ("1.16.3"),
    V1_16_2     ("1.16.2"),
    V1_16_1     ("1.16.1"),
    V1_16       ("1.16"),
    V1_15_2     ("1.15.2"),
    V1_15_1     ("1.15.1"),
    V1_15       ("1.15"),
    V1_14_4     ("1.14.4"),
    V1_14_3     ("1.14.3"),
    V1_14_2     ("1.14.2"),
    V1_14_1     ("1.14.1"),
    V1_14       ("1.14"),
    V1_13_2     ("1.13.2"),
    V1_13_1     ("1.13.1"),
    V1_13       ("1.13"),
    V1_12_2     ("1.12.2"),
    V1_12_1     ("1.12.1"),
    V1_12       ("1.12"),
    V1_11_2     ("1.11.2"),
    V1_11_1     ("1.11.1"),
    V1_11       ("1.11"),
    V1_10_2     ("1.10.2"),
    V1_10_1     ("1.10.1"),
    V1_10       ("1.10"),
    V1_9_4      ("1.9.4"),
    V1_9_3      ("1.9.3"),
    V1_9_2      ("1.9.2"),
    V1_9_1      ("1.9.1"),
    V1_9        ("1.9"),
    V1_8_9      ("1.8.9"),
    V1_8_8      ("1.8.8"),
    V1_8_7      ("1.8.7"),
    V1_8_6      ("1.8.6"),
    V1_8_5      ("1.8.5"),
    V1_8_4      ("1.8.4"),
    V1_8_3      ("1.8.3"),
    V1_8_2      ("1.8.2"),
    V1_8_1      ("1.8.1"),
    V1_8        ("1.8"),
    V1_7_10     ("1.7.10");

    public final String version;

    MinecraftVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public static MinecraftVersion[] getRange(
            MinecraftVersion from,
            MinecraftVersion to
    ) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Versions cannot be null");
        }
        if (from.ordinal() < to.ordinal()) {
            throw new IllegalArgumentException(
                    "'from' must be <= 'to'"
            );
        }
        MinecraftVersion[] versions = MinecraftVersion.values();
        MinecraftVersion[] range = Arrays.copyOfRange(
                versions,
                to.ordinal(),
                from.ordinal() + 1
        );
        for (int i = 0; i < range.length / 2; i++) {
            MinecraftVersion temp = range[i];
            range[i] = range[range.length - 1 - i];
            range[range.length - 1 - i] = temp;
        }

        return range;
    }
}
