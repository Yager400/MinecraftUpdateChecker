# MinecraftUpdateChecker

**MinecraftUpdateChecker** is a small library that makes easy to inform server owners that uses your plugin to know if a new version was released.

## Key features
The library is compiled with **Java 8** so it's 100% compatible with your plugin/mod.<br>
It also uses **no java api related to minecraft**, meaning that you can implement this even in a separated application. <br>
It has **very little boilerplate**, the developer just needs to use one class. <br>
It supports **various plugin provider** (aka places where you can download plugins) such as Modrinth and Hangar.

## How to use it
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

