plugins {
    id("java")
}

repositories {
    mavenLocal()
    mavenCentral()

    // PlaceholderAPI
    maven("https://repo.papermc.io/repository/maven-public/")
    // spigot-api
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation(project(":turtle-api"))

    // spigot-api
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    // placeholder-api
    compileOnly("me.clip:placeholderapi:2.11.6")
}