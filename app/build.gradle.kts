plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.codemaster"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.codemaster"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(platform(libs.compose.bom))
    implementation(platform(libs.compose.bom))
//    implementation(libs.firebase.auth.ktx)
//    implementation(libs.firebase.database.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    // ui preview
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)

    // icon
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)

    // graph
    implementation("com.github.madrapps:plot:0.1.2")

    //retrofit
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // coil glide shimmer
    implementation ("com.valentinilk.shimmer:compose-shimmer:1.0.4")
    implementation ("io.coil-kt:coil-compose:2.3.0")
    implementation ("com.github.skydoves:landscapist-glide:2.1.10")

    implementation("com.google.firebase:firebase-auth-ktx:21.3.0")

    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database-ktx:20.2.0")
    implementation("androidx.webkit:webkit:1.6.1")

    //accompanist
    implementation("com.google.accompanist:accompanist-pager:0.27.1")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.27.1")
    implementation("com.google.android.material:material:1.5.0-alpha01")
    implementation ("com.google.accompanist:accompanist-insets-ui:0.31.1-alpha")


    //collapsing toolbar
    implementation("me.onebone:toolbar-compose:2.3.5")

    //charty
    implementation("com.github.madrapps:plot:0.1.1")
    implementation("com.github.furkanaskin:ClickablePieChart:1.0.6")

    //horizontal pager
    implementation ("androidx.compose.foundation:foundation:1.4.3")

    /** Bar charts  **/
    // Includes the core logic for charts and other elements.
    implementation("com.patrykandpatrick.vico:core:1.6.5")
    // For Jetpack Compose.
    implementation("com.patrykandpatrick.vico:compose:1.6.5")
    // For the view system.
    implementation("com.patrykandpatrick.vico:views:1.6.5")
    // For `compose`. Creates a `ChartStyle` based on an M2 Material Theme.
    implementation("com.patrykandpatrick.vico:compose-m2:1.6.5")
    // For `compose`. Creates a `ChartStyle` based on an M3 Material Theme.
    implementation("com.patrykandpatrick.vico:compose-m3:1.6.5")

    //constraint layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha09")

    // Room Database
    implementation("androidx.room:room-runtime:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.3")
    annotationProcessor("androidx.room:room-compiler:2.5.1")
    kapt("androidx.room:room-compiler:2.5.1")

    implementation("me.saket.swipe:swipe:1.0.0")

}
