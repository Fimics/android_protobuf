plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.noetix.demo"
    defaultConfig {
        applicationId = "com.noetix.robotics"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
        multiDexEnabled = true
        ndk {
              abiFilters.addAll(arrayOf("arm64-v8a"))
//            abiFilters.addAll(arrayOf("armeabi-v7a"))
        }
    }

    signingConfigs {
        create("keyStore") {
            storeFile = file("../app/platform.jks")
            keyPassword = "android"
            keyAlias = "androidplatformkey"
            storePassword = "android"
        }
    }

    buildTypes {
        val signConfig = signingConfigs.getByName("keyStore")

        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signConfig
        }
    }


//    packagingOptions {
//        exclude("**/*.so")
//    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("libs")
            assets {
                srcDirs("src/main/assets")
            }
        }
    }
    buildFeatures {
        viewBinding = true
    }


}

dependencies {
    implementation(fileTree(mapOf("includes" to listOf("*.aar", "*.jar"), "dir" to "libs")))
    implementation(files("libs/libNoetix-debug.aar"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.multidex:multidex:2.0.1")

//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.commons:commons-csv:1.9.0")
    api("io.github.jeremyliao:live-event-bus-x:1.8.0")

}
