#Pronto

![alt tag](https://raw.githubusercontent.com/mozadeh/pronto/master/new_logo1.jpg)

## Award

This was a prototype app presented at UC Berkeley Play Conference Hackathon 2014 ```https://www.facebook.com/playconference/```


The app won 1st prize + Audience prize at the hackathon

The team that won second place in Play 2014, Xendit, was accepted into Y-Combinator

![alt tag](https://raw.githubusercontent.com/mozadeh/pronto/master/award.jpg)

## Description

Pronto is an hourly deals app that would enable the small business owners to get rid of their expiring inventory quickly.

At the same time, we realized this could be a great channel for small business owners to get customers to come to their brick and mortar shops.

## Libraries / Tutorials / APIs used

- Android Push Notification Tutorial ```https://www.tutorialspoint.com/android/android_push_notification.htm```

## Key Features

- Push notification

## File structure

```
/
├── LICENSE
├── Pronto
│   ├── AndroidManifest.xml
│   ├── assets
│   │   ├── Roboto-Bold.ttf
│   │   ├── Roboto-BoldCondensed.ttf
│   │   ├── Roboto-BoldCondensedItalic.ttf
│   │   ├── Roboto-Condensed.ttf
│   │   └── Roboto-CondensedItalic.ttf
│   ├── bin
│   │   ├── AndroidManifest.xml
│   │   ├── Pronto.apk
│   │   ├── R.txt
│   │   ├── classes
│   │   │   └── com
│   │   │       ├── google
│   │   │       │   └── android
│   │   │       │       └── gms
│   │   │       │           ├── R$attr.class
│   │   │       │           ├── R$color.class
│   │   │       │           ├── R$drawable.class
│   │   │       │           ├── R$id.class
│   │   │       │           ├── R$string.class
│   │   │       │           ├── R$styleable.class
│   │   │       │           └── R.class
│   │   │       └── hackathon
│   │   │           └── pronto
│   │   │               ├── BuildConfig.class
│   │   │               ├── Config.class
│   │   │               ├── Controller$1.class
│   │   │               ├── Controller.class
│   │   │               ├── GCMIntentService.class
│   │   │               ├── JSONParser.class
│   │   │               ├── MainActivity$1.class
│   │   │               ├── MainActivity$2.class
│   │   │               ├── MainActivity.class
│   │   │               ├── Manifest$permission.class
│   │   │               ├── Manifest.class
│   │   │               ├── R$attr.class
│   │   │               ├── R$color.class
│   │   │               ├── R$dimen.class
│   │   │               ├── R$drawable.class
│   │   │               ├── R$id.class
│   │   │               ├── R$layout.class
│   │   │               ├── R$raw.class
│   │   │               ├── R$string.class
│   │   │               ├── R$style.class
│   │   │               ├── R$styleable.class
│   │   │               ├── R.class
│   │   │               ├── RegisterActivity$1.class
│   │   │               ├── RegisterActivity$2.class
│   │   │               ├── RegisterActivity.class
│   │   │               ├── discountscreenactivity$1.class
│   │   │               ├── discountscreenactivity$2.class
│   │   │               ├── discountscreenactivity$3.class
│   │   │               ├── discountscreenactivity$StoreClick.class
│   │   │               ├── discountscreenactivity$UpdateOffer$1.class
│   │   │               ├── discountscreenactivity$UpdateOffer.class
│   │   │               └── discountscreenactivity.class
│   │   ├── classes.dex
│   │   ├── dexedLibs
│   │   │   ├── android-support-v4-ad244bdcc1ea2fbd4eda49290cd159c8.jar
│   │   │   ├── gcm-da2fc6ec725be968deffc4db3ba57960.jar
│   │   │   ├── google-play-services-7957bec2fa2523cb6c249312f55b749f.jar
│   │   │   └── google-play-services_lib-09460d2c78024ed825633ac26cbc5957.jar
│   │   ├── jarlist.cache
│   │   ├── res
│   │   │   └── crunch
│   │   │       ├── drawable
│   │   │       │   └── navigate.png
│   │   │       ├── drawable-hdpi
│   │   │       │   ├── fail.png
│   │   │       │   ├── gcm_logo.png
│   │   │       │   ├── ic_action_search.png
│   │   │       │   ├── ic_launcher.png
│   │   │       │   └── success.png
│   │   │       ├── drawable-mdpi
│   │   │       │   └── ic_launcher.png
│   │   │       ├── drawable-xhdpi
│   │   │       │   └── ic_launcher.png
│   │   │       └── drawable-xxhdpi
│   │   │           └── ic_launcher.png
│   │   └── resources.ap_
│   ├── gen
│   │   └── com
│   │       ├── google
│   │       │   └── android
│   │       │       └── gms
│   │       │           └── R.java
│   │       └── hackathon
│   │           └── pronto
│   │               ├── BuildConfig.java
│   │               ├── Manifest.java
│   │               └── R.java
│   ├── ic_launcher-web.png
│   ├── libs
│   │   ├── android-support-v4.jar
│   │   └── gcm.jar
│   ├── lint.xml
│   ├── proguard-project.txt
│   ├── project.properties
│   ├── res
│   │   ├── drawable
│   │   │   └── navigate.png
│   │   ├── drawable-hdpi
│   │   │   ├── fail.png
│   │   │   ├── gcm_logo.png
│   │   │   ├── ic_action_search.png
│   │   │   ├── ic_launcher.png
│   │   │   └── success.png
│   │   ├── drawable-ldpi
│   │   ├── drawable-mdpi
│   │   │   └── ic_launcher.png
│   │   ├── drawable-xhdpi
│   │   │   └── ic_launcher.png
│   │   ├── drawable-xxhdpi
│   │   │   └── ic_launcher.png
│   │   ├── layout
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_register.xml
│   │   │   └── discountscreen.xml
│   │   ├── menu
│   │   ├── raw
│   │   │   ├── delete.mp3
│   │   │   ├── sent.mp3
│   │   │   └── soundeffect.mp3
│   │   ├── values
│   │   │   ├── dimens.xml
│   │   │   ├── strings.xml
│   │   │   └── styles.xml
│   │   ├── values-v11
│   │   └── values-v14
│   └── src
│       └── com
│           └── hackathon
│               └── pronto
│                   ├── Config.java
│                   ├── Controller.java
│                   ├── GCMIntentService.java
│                   ├── JSONParser.java
│                   ├── MainActivity.java
│                   ├── RegisterActivity.java
│                   └── discountscreenactivity.java
└── README.md
```

