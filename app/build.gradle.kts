plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.finebyme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finebyme"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "UNSPLASH_ACCESS_KEY", "\"${property("UNSPLASH_ACCESS_KEY")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    // Splash
    implementation ("androidx.core:core-splashscreen:1.0.1")

    //coroutines, retrofit2, okhttp3(api 통신)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //KTX 종속성(수명 주기 인식 구성요소 코루틴 사용)
    //ViewModelScope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    //LifecycleScope
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    //liveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")

    //Glide(이미지)
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //cardView
    implementation ("androidx.cardview:cardview:1.0.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}