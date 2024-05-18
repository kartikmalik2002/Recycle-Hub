package com.example.recyclehub.activities.models

import android.os.Parcel
import android.os.Parcelable

data class Qty(val qty : String, val noOfCoins : Int, var isSelected : Boolean = false)  : Parcelable{
    constructor(source: Parcel) : this(
        source.readString()!!,

        source.readInt()!!

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(qty)

        writeInt(noOfCoins)

    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Users> = object : Parcelable.Creator<Users> {
            override fun createFromParcel(source: Parcel): Users = Users(source)
            override fun newArray(size: Int): Array<Users?> = arrayOfNulls(size)
        }
    }

}
