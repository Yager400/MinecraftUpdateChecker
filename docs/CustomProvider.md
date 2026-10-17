## Custom plugin provider
A custom plugin provider is none other than api for your version.<br>
Let's say that you have your own website where you share your plugin 
or you didn't publish it anywhere else that github.<br>
If so, you can follow those simple rules:

# Use the pre built-in custom provider
## 1.
Create a json file that is readable from the internet, this means that it must be public and not in a private host/github repository

## 2.
Create a structure like this:

```json
{
  "gameVersion": [
    "26.3",
    "26.2",
    "26.1.2",
    ...
  ],
  "loaders": [
    "bukkit",
    "spigot",
    "paper",
    ...
  ],
  "name": "MyPlugin",
  "version": "2.0.0",
  "changelogs": "New features: ..."
}
```

## 3.
In your MinecraftCheckerInstance, do this:
```java
minecraftCheckerUpdate.setCustomUrl("https://raw.githubusercontent.com/MyUsername/MyPlugin/refs/heads/main/updates.json");
```
If you have a custom url that uses your plugin name, do this:
```java
// The %s will be replaced with your plugin slug
minecraftCheckerUpdate.setCustomUrl("https://mydomain.com/api/%s/updates.json");
```

# Create a new provider
A custom provider can be usefull if you need to get more (or less) data than the custom provider.<br>
Here's how to create one.
## 1.

Create a class in your plugin, you can call it `CustomProvider` and extend it to `Provider`, like this:
```java
import com.github.yager400.minecraftupdatechecker.providers.Provider;

public class CustomProvider extends Provider {
    ...
}
```

## .2

Make sure you have Gson(it should be included in every api), proceed with adding this simple 4 methods and this final variable to your class:
```java
public class CustomProvider extends Provider {

    private static final String CUSTOM_API_URL =
            "https://https://mydomain.com/api/%s/updates.json";

    private UpdateData getUpdateDataFromElement(JsonElement element) {
        ...
    }

    @Override
    public UpdateData getLatest(JsonObject jsonObject) {
        ...
    }
    
    @Override
    public UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, JsonObject jsonObject) {
        ...
    }
    
    @Override
    public String getApiResponse(String pluginSlug) throws Exception {
        ...
    }
}
```

## .3
Now let's populate those methods, let's start with `getUpdateDataFromElement`:
```java
private UpdateData getUpdateDataFromElement(JsonElement element) {
    // Here you are gonna build the UpdateData containing information about
     // the new update.
    // Get those updates from the JsonElement
    return new UpdateData(
            /* name */,
            /* version type (release, beta or alpha) */,
            /* version */,
            /* changelogs */
    );
}
```
Now the method `getLatest`, this is really simple but a normal plugin will never use this, this is how to implement it:
```java
@Override
public UpdateData getLatest(JsonObject jsonObject) {
    // Check if the jsonObject is not null
    if (jsonObject == null || jsonObject.getAsJsonArray().isEmpty()) {
        return null;
    }
    // Get the first element of the json array
    JsonElement element = jsonObject.getAsJsonArray().get(0);
    // Convert the data into an UpdateData
    return getUpdateDataFromElement(element);
}
```
Now the method `getLastSupportedVersion`, this is probably the hardest to implement, you need to get the latest version available for the correct minecraft version and correct loader:<br>
This is an example with modrinth, your custom provider might not be the same
```java
@Override
public UpdateData getLastSupportedVersion(LoaderVersion[] versions, SupportedLoaders[] loaders, JsonObject jsonObject) {
    // Always check if the jsonObject is null
    if (jsonObject == null || jsonObject.isEmpty()) {
        return null;
    }

    // Parse every available version in the json
    for (JsonElement element : jsonObject.getAsJsonArray()) {
        // This 2 boolean represent if this version is eligible for this configuration
        boolean hasVersion;
        boolean hasLoader;

        // Get an Iterator<JsonElemet> for every gameVersion
        Iterator<JsonElement> gameVersions = element.getAsJsonObject()
                .get("game_versions").getAsJsonArray()
                .iterator();
        // Check if this iterator has the minecraft's version your plugin supports
        // You can use the integrated method this.iterateGameVersion
        hasVersion = this.iterateGameVersion(gameVersions, versions);

        // Get an Iterator<JsonElement> for every supported loader
        Iterator<JsonElement> supportedLoaders = element.getAsJsonObject()
                .get("loaders").getAsJsonArray()
                .iterator();
        // Check if this iterator contains your plugin's loader
        // You can use the integrated method this.iterateLoaders
        hasLoader = this.iterateLoaders(supportedLoaders, loaders);

        // If this version has the correct minecraft's version and correct loader, 
         // convert it into an UpdateData
        if (hasVersion && hasLoader) {
            return getUpdateDataFromElement(element);
        }
    }
    return null;
}
```
And now the last method which is `getApiResponse`, this is common method that you can implement in 2 different ways:<br>
```java
public String getApiResponse(String pluginSlug) throws Exception {
    // If your api doesn't require anything special nor any authentication, you can use the built-in method
    return this.getDefaultApiResponse(CUSTOM_API_URL, pluginSlug);
    
    // If you need more, for example authentication via post request, you can do it, you just need to return the json string at the end
}
```

## .4
When you have your provider, in your **minecraftUpdateChecker** instance do this:
```java
minecraftUpdateChecker.setProvider(new MyCustomProvider());
```