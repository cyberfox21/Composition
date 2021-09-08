package com.tatyanashkolnik.composer.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
* или
* если есть плагин в build Gradle
*
* plugins {
*    id 'com.android.application'
*    id 'kotlin-android'
*    id 'kotlin-parcelize' - вот этот
* }
*
* то можно не париться и написать так
*/

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPersentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Parcelable

// или можно упороться и сделать, чтобы студия сгенерировала за нас реализацию :)

//data class GameSettings (
//    val maxSumValue: Int,
//    val minCountOfRightAnswers: Int,
//    val minPersentOfRightAnswers: Int,
//    val gameTimeInSeconds: Int
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readInt(),
//        parcel.readInt(),
//        parcel.readInt()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(maxSumValue)
//        parcel.writeInt(minCountOfRightAnswers)
//        parcel.writeInt(minPersentOfRightAnswers)
//        parcel.writeInt(gameTimeInSeconds)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<GameSettings> {
//        override fun createFromParcel(parcel: Parcel): GameSettings {
//            return GameSettings(parcel)
//        }
//
//        override fun newArray(size: Int): Array<GameSettings?> {
//            return arrayOfNulls(size)
//        }
//    }
//}