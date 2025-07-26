plugins {
    id("java")
}

dependencies {
    // AfyBroker
    compileOnly("net.afyer.afybroker:afybroker-core:2.5")
    compileOnly("net.afyer.afybroker:afybroker-client:2.5")
    // spigot-api
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
}