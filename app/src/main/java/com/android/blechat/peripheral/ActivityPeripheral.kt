package com.android.blechat.peripheral

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.blechat.R
import com.android.blechat.REQUEST_ADVERTISE_BT

class ActivityPeripheral : AppCompatActivity() {
    private val TAG = "ActivityPeripheral:"
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peripheral)

        button = findViewById(R.id.button3)

        button.setOnClickListener{
            startAdvertise()
        }
    }

    private fun startAdvertise() {
        Log.i(TAG, "startAdvertise")
        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        }
        startActivityForResult(discoverableIntent, REQUEST_ADVERTISE_BT)
    }
}