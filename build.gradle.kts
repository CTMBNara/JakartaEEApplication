import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.30"
}

group = "me.danil"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")

    implementation("org.postgresql:postgresql:42.2.24.jre7")

    val tomcatVersion = "10.0.12"
    implementation("org.apache.tomcat.embed:tomcat-embed-core:$tomcatVersion")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:$tomcatVersion")
    implementation("org.apache.tomcat:tomcat-jasper:$tomcatVersion")
    implementation("org.apache.tomcat:tomcat-jasper-el:$tomcatVersion")
    implementation("org.apache.tomcat:tomcat-jsp-api:$tomcatVersion")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:2.0.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
