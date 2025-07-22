plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("8.1.1")
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

    // AfyBroker
    compileOnly("net.afyer.afybroker:afybroker-core:2.5")
    compileOnly("net.afyer.afybroker:afybroker-client:2.5")
    // spigot-api
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    // placeholder-api
    compileOnly("me.clip:placeholderapi:2.11.6")
}

tasks {
    jar {
        enabled = false
    }

    processResources {
        val props = mapOf(
            "version" to project.version
        )
        inputs.properties(props)
        filesMatching("broker.yml") {
            expand(props)
        }
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set("${rootProject.name}-bukkit-${project.version}.jar")
    }
}