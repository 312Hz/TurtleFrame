plugins {
    id("java")
}

allprojects {
    group = "me.xiaoying.turtleframe"
    version = "1.0.0"
}

subprojects {
    apply(plugin = "java")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
        // AfyBroker
        compileOnly("net.afyer.afybroker:afybroker-core:2.5")

        // sqlfactory
        compileOnly("me.xiaoying:sqlfactory:1.0.0")

        // lombok
        compileOnly ("org.projectlombok:lombok:1.18.30")
        annotationProcessor ("org.projectlombok:lombok:1.18.30")
        testCompileOnly ("org.projectlombok:lombok:1.18.30")
        testAnnotationProcessor ("org.projectlombok:lombok:1.18.30")
    }
}