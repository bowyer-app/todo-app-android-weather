## todo-app-android
天気予報を表示出来るToDoアプリです。

## 開発環境
1.Android Studioをダウンロード [Android Studio](https://developer.android.com/studio/?hl=ja)4.2.1をインストールしてください

2.Java Code Styles

Projectを使用

## Flavor
### product
本番ビルド。OpenWeatherのAppIdと天気を取得したいZipCodeを入力しないと動作しない
### mock
mockビルド。天気のAPIからダミーのデータが返ってきます。

## アーキテクチャ
MVVM + Clean Architecture

## ドメインモデル
準備中
## デザインファイル
準備中
## 表示情報の取得
### 天気予報の取得
OpenWeatherのAPIを叩いています。
事前に下記に登録しAppIdを発行してください。
https://openweathermap.org/api

## 使用ライブラリ
 - Navigation:[Navigation Component](https://developer.android.google.cn/guide/navigation/navigation-getting-started?hl=ja)
 - RecyclerView layouts:[groupie](https://github.com/lisawray/groupie)
 - DB：[Room](https://developer.android.com/topic/libraries/architecture/room)
 - DI:[Dagger Hilt](https://dagger.dev/hilt/)
 - Network:[Retrofit](https://github.com/square/retrofit)
 - JSON parse:[Moshi](https://github.com/square/moshi)
 - Log:[Timber](https://github.com/JakeWharton/timber)
