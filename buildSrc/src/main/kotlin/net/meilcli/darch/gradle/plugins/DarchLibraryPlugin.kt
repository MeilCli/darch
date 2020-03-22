package net.meilcli.darch.gradle.plugins

import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import net.meilcli.darch.gradle.Libraries
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DarchLibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.findPlugin(LibraryPlugin::class.java)?.apply {
            extension.compileSdkVersion(29)

            extension.defaultConfig.minSdkVersion(21)
            extension.defaultConfig.targetSdkVersion(29)
            extension.defaultConfig.versionCode = 1
            extension.defaultConfig.versionName = "1.0"
            extension.defaultConfig.testInstrumentationRunner =
                "androidx.test.runner.AndroidJUnitRunner"
            extension.defaultConfig.consumerProguardFile("consumer-rules.pro")

            (extension.buildTypes.findByName("release")
                ?: extension.buildTypes.create("release")).apply {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt", project),
                    "proguard-rules.pro"
                )
            }

            extension.sourceSets.all {
                java.srcDir("src/${name}/kotlin")
            }
        }

        project.dependencies {
            implementation(Libraries.Kotlin.stdlib)

            testImplementation(Libraries.Junit.junit4)

            androidTestImplementation(Libraries.Android.junit)
            androidTestImplementation(Libraries.Android.espresso)
        }
    }
}