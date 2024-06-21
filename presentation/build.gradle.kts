plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")   //kapt 에서 KSP 로 이전(Room DB)
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.finebyme.presentation"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(project(":domain")) // Domain Layer에 대한 의존성

    // Splash
    implementation ("androidx.core:core-splashscreen:1.0.1")

    //coroutines, retrofit2, okhttp3(api 통신)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //KTX 종속성(수명 주기 인식 구성요소 코루틴 사용)
    //ViewModelScope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    //LifecycleScope
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    //liveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")

    //Glide(이미지)
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //cardView
    implementation ("androidx.cardview:cardview:1.0.0")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //hilt
    //version 2.44 로 설정시
    //Caused by: java.lang.ClassNotFoundException: Didn't find class "com.example.hiltapp.Hilt_HiltApplication" on path: DexPathList 오류 발생
    val hiltVersion = "2.51"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")
    ksp("androidx.hilt:hilt-compiler:1.2.0")


    implementation("androidx.fragment:fragment-ktx:1.8.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}