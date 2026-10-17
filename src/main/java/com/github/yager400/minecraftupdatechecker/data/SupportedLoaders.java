/*
Copyright (c) 2026 Yager400

This file is part of this project, released under the terms of
the MIT License.
See the LICENSE file for details.
 */
package com.github.yager400.minecraftupdatechecker.data;

public enum SupportedLoaders {

    BUKKIT      ("bukkit"),
    SPIGOT      ("spigot"),
    PAPER       ("paper"),
    PURPUR      ("purpur"),
    FOLIA       ("folia"),
    BUNGEECORD  ("bungeecord"),
    VELOCITY    ("velocity"),
    WATERFALL   ("waterfall"),
    FABRIC      ("fabric"),
    FORGE       ("forge"),
    NEOFORGE    ("neoforge"),
    QUILT       ("quilt");

    private final String loader;

    SupportedLoaders(String loader) {
        this.loader = loader;
    }

    public String getLoader() {
        return this.loader;
    }
}
