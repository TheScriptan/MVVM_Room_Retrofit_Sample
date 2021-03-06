# MVVM_Room_Retrofit_Sample

Project is for learning purpose. Goal is to combine architecture components and libraries into one efficient project that can be a starting point for using MVVM pattern. Project idea is to retrieve simple data from Github API.

## Getting Started

To get started simply clone or fork the project and open it with Android studio.

### Prerequisites

* Android Studio 3.3+
* Java 8 Features
* Knowledge about MVVM
* Be familiar with Room and ViewModel/LiveData
* Be familiar with Retrofit, ButterKnife, Glide

## Built With

* [MVVM](https://developer.android.com/jetpack/docs/guide) - Guide to app architecture
* [Room](https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0) - Room persistence library
* [Retrofit2](https://square.github.io/retrofit/) - REST client library
* [Glide](https://github.com/bumptech/glide) - HTTP Image fetching library
* [ButterKnife](http://jakewharton.github.io/butterknife/) - Library that lets you bind near Views to reduce findViewById LOC.
* [JSON->POJO](http://www.jsonschema2pojo.org/) - Great tool to generate POJO from JSON

## Implementations in Gradle

```
    //Room
    def room_version = "2.0.0"
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"

    //ViewModel & LiveData
    def lifecycle_version = "2.0.0"
    implementation "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"

    //Retrofit2
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'

    //Glide
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'

    //ButterKnife
    implementation 'com.jakewharton:butterknife:10.1.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:10.1.0'

    //Recyclerview
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
```    
