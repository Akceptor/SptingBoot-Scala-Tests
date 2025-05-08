plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("scala")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-scala_3")
    implementation("org.scala-lang:scala3-library_3:3.6.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.scalatest:scalatest_3:3.2.19")
    testImplementation("org.scalatestplus:junit-5-10_3:3.2.18.0")

}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveFileName.set("app.jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf("-deprecation", "-feature", "-unchecked")
}

tasks.named("bootRun") {
    dependsOn("build")
}

sourceSets {
    test {
        scala.srcDir("src/test/scala")
        resources.srcDir("src/test/resources")
    }
}

tasks.named<Copy>("processTestResources") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


tasks.test {
    useJUnitPlatform()
    testLogging {
        events("started", "passed", "skipped", "failed")
        showStandardStreams = true
    }
}

