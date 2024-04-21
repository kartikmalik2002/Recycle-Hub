package com.example.recyclehub.activities.models

import android.os.Parcel
import android.os.Parcelable

data class Users(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val coins : Int =0
) : Parcelable{
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readInt()!!

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(email)
        writeInt(coins)

    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Users> = object : Parcelable.Creator<Users> {
            override fun createFromParcel(source: Parcel): Users = Users(source)
            override fun newArray(size: Int): Array<Users?> = arrayOfNulls(size)
        }
    }
}