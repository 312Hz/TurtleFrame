plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("8.1.1")
}

dependencies {
    implementation(project(":turtle-api"))

    // afybroker
    compileOnly("net.afyer.afybroker:afybroker-server:2.5")
    // yaml
    compileOnly("org.yaml:snakeyaml:2.2")
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
        archiveFileName.set("${rootProject.name}-broker-${project.version}.jar")

        exclude("README")
        exclude("plugin.yml")
    }
}