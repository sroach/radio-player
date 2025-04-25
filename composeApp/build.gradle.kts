import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_19)
        }
    }

    // Read API key from local.properties
    val localPropertiesFile = project.rootProject.file("local.properties")
    val apiKey = if (localPropertiesFile.exists()) {
        val properties = localPropertiesFile.readLines()
            .filter { it.startsWith("stations.api.key=") }
            .map { it.split("=")[1].trim() }
            .firstOrNull() ?: ""
        properties
    } else {
        ""
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

            // Pass API key to Info.plist
            embedBitcode = org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode.DISABLE
            freeCompilerArgs += listOf(
                "-Xbinary=bundleId=gy.roach.radio",
                "-Xbinary=bundleVersion=1.0",
                "-Xbinary=bundleShortVersionString=1.0",
                "-Xbinary=infoPlist=${project.file("src/iosMain/Info.plist").absolutePath}",
                "-Xbinary=infoPlistKey=STATIONS_API_KEY=$apiKey"
            )
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    // Task to generate config.js file with API key for WASM/Web
    tasks.register("generateWebConfig") {
        doLast {
            // Read API key from local.properties
            val localPropertiesFile = project.rootProject.file("local.properties")
            val apiKey = if (localPropertiesFile.exists()) {
                val properties = localPropertiesFile.readLines()
                    .filter { it.startsWith("stations.api.key=") }
                    .map { it.split("=")[1].trim() }
                    .firstOrNull() ?: ""
                properties
            } else {
                ""
            }

            // Create the config directory if it doesn't exist
            val configDir = project.file("src/wasmJsMain/resources/config")
            configDir.mkdirs()

            // Create the config.js file
            val configFile = project.file("src/wasmJsMain/resources/config/config.js")
            configFile.writeText("""
                // This file is generated during build. Do not edit manually.
                window.APP_CONFIG = {
                    STATIONS_API_KEY: "$apiKey"
                };
            """.trimIndent())

            println("Generated config.js with API key for WASM/Web")
        }
    }

    // Make sure the config file is generated before the WASM/Web build
    tasks.named("wasmJsBrowserDevelopmentWebpack") {
        dependsOn("generateWebConfig")
    }

    tasks.named("wasmJsBrowserProductionWebpack") {
        dependsOn("generateWebConfig")
    }

    sourceSets {
        val desktopMain by getting
        val iosMain by creating
        val wasmJsMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("org.jetbrains.compose.material:material-icons-extended:${libs.versions.compose.multiplatform.get()}")
            implementation(libs.cupertino)
            implementation(libs.materialKolor)
            implementation(libs.kotlinx.serialization.json)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.jlayer)
            implementation(libs.ktor.client.okhttp)
            // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
            implementation("org.slf4j:slf4j-api:2.0.17")
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

android {
    namespace = "gy.roach.radio"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "gy.roach.radio"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        // Read API key from local.properties
        val isWindows = System.getProperty("os.name").lowercase().contains("windows")

        // Read API key from local.properties
        val localPropertiesFile = project.rootProject.file("local.properties")
        val apiKey = if (localPropertiesFile.exists()) {
            val properties = localPropertiesFile.readLines()
                .filter { it.startsWith("stations.api.key=") }
                .map { it.split("=")[1].trim() }
                .firstOrNull() ?: ""
            properties
        } else {
            ""
        }
        buildConfigField("String", "STATIONS_API_KEY", "\"$apiKey\"")
    }

    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "gy.roach.radio.MainKt"

        // Set the local.properties path as a system property
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            jvmArgs("-Dlocal.properties.path=${localPropertiesFile.absolutePath}")
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "gy.roach.radio"
            packageVersion = "1.0.0"
        }
    }
}
