package com.example.recyclehub.activities.models

import android.os.Parcel
import android.os.Parcelable

data class Qty(val item : String , val qty : String, val noOfCoins : Int, var isSelected : Boolean = false)  : Parcelable{
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readInt()!!

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(qty)
        writeString(qty)
        writeInt(noOfCoins)

    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Qty> = object : Parcelable.Creator<Qty> {
            override fun createFromParcel(source: Parcel): Qty = Qty(source)
            override fun newArray(size: Int): Array<Qty?> = arrayOfNulls(size)
        }
    }

}
