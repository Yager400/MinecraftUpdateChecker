# MinecraftUpdateChecker

**MinecraftUpdateChecker** is a lightweight library that makes it easy to notify server owners when a new version of a plugin they use is available.

##Key Features
- Java 8 compatible: The library is compiled for Java 8, making it compatible with plugins and mods that run on Java 8 or later.
- Minecraft-independent: It does not rely on any Minecraft-specific APIs, so it can also be used in standalone applications.
- Minimal boilerplate: Developers only need to use a single class to get started.
- Multiple plugin platforms: It supports various plugin hosting platforms, such as Modrinth and Hangar.

## Installation
First implement the library into your project like this:

---
### On maven add this into your pom.xml
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.Yager400</groupId>
        <artifactId>MinecraftUpdateChecker</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```
---
### On gradle add this at the end of settings.gradle
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
### And this in the build.gradle
```groovy
dependencies {
    implementation 'com.github.Yager400:MinecraftUpdateChecker:1.0.0'
}
```
---
<a href="docs/GettingStarted.md"><button style="width: 100%; height: 40px; border-radius: 10px; background-color: gray; color: white;">Click here for getting started</button></a>
