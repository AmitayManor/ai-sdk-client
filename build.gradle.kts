// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    id("maven-publish")
}

allprojects {
    group = "com.github.AmitayManor"
    version = "1.0.0"
}