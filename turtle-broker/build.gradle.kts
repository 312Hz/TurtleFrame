plugins {
    id("java")
}

dependencies {
    implementation(project(":turtle-api"))

    // afybroker
    compileOnly("net.afyer.afybroker:afybroker-server:2.5")
}