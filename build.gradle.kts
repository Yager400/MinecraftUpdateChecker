plugins {
    id("java")
    `java-library`
    `maven-publish`
}

group = "com.github.yager400.minecraftupdatechecker"
version = "1.0.0"
java {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.google.code.gson:gson:2.10.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}