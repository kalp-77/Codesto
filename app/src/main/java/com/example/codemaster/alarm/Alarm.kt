package com.example.codemaster.alarm

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class Alarm(
    val id: Int,
    val name: String?,
    val description: String?,
    val time: Long,
    var isEnabled: Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeInt(id)
        p0.writeString(name)
        p0.writeString(description)
        p0.writeLong(time)
        p0.writeBoolean(isEnabled)
    }

    companion object CREATOR : Parcelable.Creator<Alarm> {
        override fun createFromParcel(parcel: Parcel): Alarm {
            return Alarm(parcel)
        }

        override fun newArray(size: Int): Array<Alarm?> {
            return arrayOfNulls(size)
        }
    }
}
//fun Parcel.writeBoolean(value: Boolean) {
//    writeInt(if (value) 1 else 0)
//}
