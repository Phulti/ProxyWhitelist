plugins {
    java
    checkstyle
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

group = "de.philippulti"
version = "1.0-SNAPSHOT"

tasks.create<Copy>("copyHooks") {
    from(file("./hooks/"))
    into(file("./.git/hooks/"))
}
tasks.getByPath("prepareKotlinBuildScriptModel").dependsOn("copyHooks")

subprojects {

    apply(plugin="java")
    apply(plugin="checkstyle")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.20")
        annotationProcessor("org.projectlombok:lombok:1.18.20")
        implementation("org.jetbrains:annotations:16.0.2")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    checkstyle {
        toolVersion = "8.41"
        config = project.resources.text.fromUri("https://paraalgo.informatik.uni-bremen.de/kram/checkstyle.xml")
    }

    tasks.test {
        useJUnit()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs = listOfNotNull("-parameters")
    }

}
