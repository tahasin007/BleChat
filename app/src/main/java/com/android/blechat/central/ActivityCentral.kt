package com.android.blechat.central

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.blechat.*
import com.android.blechat.central.adapter.BluetoothDeviceAdapter
import com.android.blechat.central.data.BluetoothDeviceItem
import com.android.blechat.databinding.ActivityCentralBinding

class ActivityCentral : AppCompatActivity() {
    private val TAG = "ActivityCentral:"
//    private var _binding: ActivityCentralBinding? = null
//    private val binding get() = _binding!!
    private lateinit var button: Button
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BluetoothAdapter
    private lateinit var bluetoothDeviceAdapter: BluetoothDeviceAdapter
    private val bluetoothDeviceList: MutableList<BluetoothDeviceItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_central)

        adapter = BluetoothAdapter.getDefaultAdapter()

        if (!adapter.isEnabled) {
            Log.i(TAG, "Request Enable Bluetooth")
            enableAdapter()
        }
        else {
            Log.i(TAG, "Bluetooth is enabled")
        }

        button = findViewById(R.id.scan_button)
        recyclerView = findViewById(R.id.recycler_view)

        button.setOnClickListener{
            startDiscovery()
        }

        bluetoothDeviceAdapter = BluetoothDeviceAdapter(bluetoothDeviceList)
        recyclerView.adapter = bluetoothDeviceAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun enableAdapter() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
    }

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    Log.i(TAG, "Device found")
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

                    val deviceName = device?.name ?: "N/A"
                    Log.i(TAG, "Device Name: $deviceName")

                    val bondState = device?.bondState ?: -1
                    Log.i(TAG, "Device Bond State: $bondState")

                    val type = device?.type ?: -1
                    Log.i(TAG, "Device Type: $type")

                    val deviceHardwareAddress = device?.address ?: "N/A"
                    Log.i(TAG, "Device Address: $deviceHardwareAddress")

                    val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
                    Log.i(TAG, "RSSI Value: $rssi")

                    val deviceUuids = device?.uuids ?: "N/A"
                    Log.i(TAG, "Device UUIDS: $deviceUuids")

                    val newDevice = BluetoothDeviceItem(
                        address = deviceHardwareAddress,
                        deviceName = deviceName
                    )

                    bluetoothDeviceList.add(newDevice)
                    bluetoothDeviceAdapter.notifyItemInserted(bluetoothDeviceList.size - 1)
                }
            }
        }
    }

    private fun startDiscovery() {
        Log.i(TAG, "Starting Discovery")
        adapter.startDiscovery()
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(receiver, intentFilter)
        Log.i(TAG, "Receiver registered")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        Log.i(TAG, "Receiver unregistered")
    }
}