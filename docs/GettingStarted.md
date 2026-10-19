# Getting started

## Implementing the api
You can implement the api in your project by simply adding the dependency in your build script or maven manifest:

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

## Relocating and bundling the api
Since this api is not a plugin, you will need those 2 things:
1. Having Gson in your classpath (you should be able to skip this step if you are using a modding api for minecraft)
2. Bundling the api in your plugin/mod using the gradleup or maven shade plugin
Relocating and implementing the api:

---
### On maven 
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.6.2</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <relocations>
                                <relocation>
                                    <pattern>com.github.yager400.minecraftupdatechecker</pattern>
                                    <shadedPattern>your.plugin.libs.minecraftupdatechecker</shadedPattern>
                                </relocation>
                            </relocations>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
---
### On gradle
```groovy
plugins {
    id 'com.gradleup.shadow' version '9.4.0'
}
```
And add this task:
```groovy
tasks.named('shadowJar') {
    archiveClassifier = 'all'

    relocate(
            'com.github.yager400.minecraftupdatechecker', 
            'your.plugin.libs.minecraftupdatechecker'
    )
}
```
---
<a href="CheckForUpdates.md"><button style="width: 100%; height: 40px; border-radius: 10px; background-color: gray; color: white;">Click here for learning how to use the api</button></a>
