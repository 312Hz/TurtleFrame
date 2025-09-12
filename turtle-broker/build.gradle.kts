plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("8.1.1")
}

dependencies {
    implementation(project(":turtle-api"))

    // logger
    compileOnly("me.xiaoying:logger:1.0.1")

    // broker
    compileOnly("me.xiaoying.moebroker:broker-api:1.0.0")
    implementation("me.xiaoying.moebroker:broker-server:1.0.0")
    implementation("me.xiaoying.moebroker:broker-server-bootstrap-api:1.0.0")

    // sqlfactory
    implementation("me.xiaoying:sqlfactory:1.0.0:all")

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
        filesMatching("plugin.yml") {
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
    }
}