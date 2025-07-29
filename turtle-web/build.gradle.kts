plugins {
    java

    id("org.springframework.boot") version "3.4.7"
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    //spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    //mybatis
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4")

    compileOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.4")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    //jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")
}