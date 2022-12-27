package com.android.blechat.central.data

data class BluetoothDeviceItem (
    val address: String,
    val bondState: Int = 0,
    val deviceName: String = "",
    val rssi: Int = 0,
    val deviceType: Int = 0
)