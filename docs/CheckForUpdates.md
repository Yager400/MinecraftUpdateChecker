# Check for updates

<p>Use this guide to check whether your plugin or mod has any available updates.<br></p>
<b>Required configuration:</b> Steps marked with an asterisk (<b style="color: red">*</b>) must be completed before starting the update checker. The checker may not work correctly if any required setting is missing.

---
### Create the update checker
<b style="color: red">*</b>
Create a MinecraftUpdateChecker instance to configure the update checker.
```java
MinecraftUpdateChecker checker = new MinecraftUpdateChecker();
```
---
### Set the project identifier
<b style="color: red">*</b>
Set your project identifier. The required value depends on the provider:

1. **Modrinth:** Project slug, which may differ from the project's display name.
2. **Hangar:** Project name.
3. **Spigot:** Resource ID.
```java
checker.setPluginIdentifier("YourPluginIdentifier");
```
---
### Set the current version
<b style="color: red">*</b>
Set your plugin or mod version. Depending on the platform or modding API you use, you can retrieve the version dynamically as a string.
```java
checker.setVersion("1.0.0");
```
---
### Configure supported Minecraft versions
<b style="color: red">*</b>
Specify the Minecraft version range supported by your plugin or mod.<br>The API supports Minecraft versions as far back as 1.7.10.
```java
checker.setMinecraftVersionRange(MinecraftVersion.V1_7_10, MinecraftVersion.V26_3);
```
---
### Configure loaders
<b style="color: red">*</b>
Specify your plugin or mod loader.<br>The API will attempt to find the latest release compatible with your Minecraft version and loader.
```java
checker.setLoaders(Loader.SPIGOT, Loader.PAPER, Loader.FABRIC, Loader.VELOCITY);
```
---
### Select a provider
<b style="color: red">*</b>
Select the platform where your project is published.<br> The following providers are supported::
1. Modrinth
2. Hangar
3. Spigot
```java
checker.setPluginProvider(PluginProvider.MODRINTH);
```
---
### Handle update notifications
<b style="color: red">*</b>
Register a callback that runs when a new update is found.<br> The callback receives an UpdateData instance containing information about the available update.
```java
checker.onUpdatePresent(updateData -> {
    LoggingUtils.getLogger().info("A new update is present!!! (version " + updateData.getNewVersion() + ")");
    LoggingUtils.getLogger().info("Here are the news: " + updateData.getChangelogs());
});
```
---
Register a callback that runs when no update is available.
```java
checker.onNoUpdatePresent(() -> {
    LoggingUtils.getLogger().info("No update was found.");
});
```
---
### Handle errors
Register a callback to handle errors that occur while checking for updates.
```java
checker.onError(scheduleError -> {
    // Print the stack trace of the error
    scheduleError.getThrowable().printStackTrace();
});
```
---
### Configure the checking interval
Configure how often the update checker runs.
```java
// Check every 2 hours
checker.checkEveryXHours(2);
```
---
To wait for the configured interval before performing the first update check, use:
```java
checker.checkNow(false);
```
---
### Start the schedule
<b style="color: red">*</b>
Start the update-checking schedule by calling start().<br> The method returns a Schedule instance that you can use to manage the schedule, including stopping it when necessary.
```java
Schedule schedule = checker.start();
```
---

# Example
Here's a complete example for EssentialsX on Bukkit demonstrating the available configuration options and callbacks:
```java
Schedule schedule = new MinecraftUpdateChecker()
        .setPluginIdentifier("essentialsx")
        .setVersion("1.0.0")
        .setMinecraftVersionRange(MinecraftVersion.V1_8_8, MinecraftVersion.V26_1_2)
        .setLoaders(Loader.SPIGOT, Loader.PAPER, Loader.PURPUR)
        .setPluginProvider(PluginProvider.MODRINTH)
        .onUpdatePresent(updateData -> {
            getLogger().info("A new update is available (version " + updateData.getNewVersion() + ")");
        })
        .onNoUpdatePresent(() -> {
            getLogger().info("No update was found, you are using the latest version.");
        })
        .onError(scheduleError -> {
            getLogger().severe("An error was thrown while checking for a new update. Stopping the schedule");
            scheduleError.getSchedule().stop();
        })
        .checkEveryXHours(1) // By default, it's already 1
        .checkNow(true) // By default, it's already true
        .start();
```
