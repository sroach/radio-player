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
            jvmTarget.set(JvmTarget.JVM_21)
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
            freeCompilerArgs += listOf(
                "-Xbinary=bundleId=gy.roach.radio",
                "-Xbinary=bundleVersion=1.2",
                "-Xbinary=bundleShortVersionString=1.2",
                "-Xbinary=infoPlist=${project.file("src/iosMain/Info.plist").absolutePath}",
                "-Xbinary=infoPlistKey=STATIONS_API_KEY=$apiKey"
            )
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                // Add specific configuration for Skia WASM compatibility
                experiments += "asyncWebAssembly"
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
    tasks.register("copyComposeResources") {
        doLast {
            copy {
                from("src/commonMain/composeResources")
                into("build/processedResources/wasmJs/main")
            }
        }
    }

    tasks.register<Copy>("assembleWebDist") {
        dependsOn("wasmJsBrowserProductionWebpack", "generateWebConfig")

        // Set duplicate handling strategy
        duplicatesStrategy = DuplicatesStrategy.WARN

        // Clear destination first
        doFirst {
            delete("build/dist/wasmJs/productionExecutable")
        }

        // 1. Copy webpack output first (WASM files and JS) - highest priority
        from("build/kotlin-webpack/wasmJs/productionExecutable") {
            include("*.js", "*.wasm", "*.LICENSE.txt")
        }

        // 2. Copy processed resources (including composeResources) - medium priority
        from("build/processedResources/wasmJs/main")

        // 3. Copy source resources last - lowest priority (will be overwritten if duplicates)
        from("src/wasmJsMain/resources") {
            exclude("**/*.kt")
        }

        into("build/dist/wasmJs/productionExecutable")

        doLast {
            println("Web distribution ready at: build/dist/wasmJs/productionExecutable/")
        }
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
            configFile.writeText(
                """
                // This file is generated during build. Do not edit manually.
                window.APP_CONFIG = {
                    STATIONS_API_KEY: "$apiKey"
                };

                // Cache-busting wrapper for dynamically loaded .wasm/.mjs (Skiko) assets
                (function() {
                  try {
                    var versionTag = 'v=' + (window.APP_BUILD_ID || Date.now());
                    var origFetch = window.fetch;
                    if (typeof origFetch === 'function') {
                      window.fetch = function(input, init) {
                        try {
                          var url = (typeof input === 'string') ? input : (input && input.url);
                          if (url) {
                            var u = new URL(url, window.location.href);
                            if (u.pathname.endsWith('.wasm') || u.pathname.endsWith('.mjs') || u.pathname.indexOf('skiko') !== -1) {
                              if (!u.searchParams.has('v')) {
                                u.searchParams.set('v', versionTag);
                              }
                              input = u.toString();
                            }
                          }
                        } catch (e) { }
                        return origFetch.call(this, input, init);
                      };
                    }
                  } catch (e) { }
                })();

                // Global handler to avoid Unhandled Promise Rejection logs for known media/autoplay issues
                window.addEventListener('unhandledrejection', function (event) {
                  try {
                    var reason = event && event.reason;
                    var msg = reason && (reason.message || String(reason));
                    var isMediaError = msg && (
                      msg.indexOf('play() failed') !== -1 ||
                      msg.indexOf('The play() request was interrupted') !== -1 ||
                      msg.indexOf('NotAllowedError') !== -1 ||
                      msg.indexOf('AbortError') !== -1 ||
                      msg.indexOf('NotSupportedError') !== -1
                    );
                    if (!msg || isMediaError || (reason && reason.name === 'WebAssembly.Exception')) {
                      console.warn('[Handled rejection]', reason);
                      event.preventDefault();
                      return;
                    }
                  } catch (e) { }
                });
            """.trimIndent()
            )

            println("Generated config.js with API key for WASM/Web")
        }
    }

    // Make sure the config file is generated before the WASM/Web build
    tasks.named("wasmJsBrowserDevelopmentWebpack") {
        dependsOn("generateWebConfig")
        dependsOn("generateComposeResClass")
    }

    tasks.named("wasmJsBrowserProductionWebpack") {
        dependsOn("generateWebConfig")
        dependsOn("generateComposeResClass")
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
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.kotlinx.serialization.json)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            // icon extended
            implementation(libs.material.icons.core)
            implementation(libs.material.icons.extended)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.jlayer)
            implementation(libs.ktor.client.okhttp)
            // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
            implementation(libs.slf4j.api)
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
        versionCode = 2
        versionName = "1.2"

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
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
            packageVersion = "1.2.0"
            // Set the icon paths for different OS distributions
            macOS {
                iconFile.set(project.file("launcher-icons/icon.icns"))
            }
            windows {
                iconFile.set(project.file("launcher-icons/icon.ico"))
            }
            linux {
                iconFile.set(project.file("launcher-icons/icon.png"))
            }
        }
    }
}

