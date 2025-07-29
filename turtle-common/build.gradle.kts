plugins {
    id("java")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":turtle-api"))
}