plugins {
    id("java")
}

dependencies {
    // implementation
    implementation("me.xiaoying.moebroker:broker-api:1.0.0")
    implementation("me.xiaoying.moebroker:broker-client:1.0.0")

    // spigot-api
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    // placeholder-api
    compileOnly("me.clip:placeholderapi:2.11.6")
}