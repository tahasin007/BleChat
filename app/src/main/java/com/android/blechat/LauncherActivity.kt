package com.android.blechat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.android.blechat.peripheral.ActivityPeripheral
import com.android.blechat.central.ActivityCentral
import com.android.blechat.databinding.ActivityMainBinding

class LauncherActivity : AppCompatActivity() {

    private val TAG = "LauncherActivity:"
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false
    private var isBluetoothConnectPermissionGranted = false
    private var isBluetoothScanPermissionGranted = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(TAG, "onCreate()")

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                isLocationPermissionGranted =
                    it[Manifest.permission.ACCESS_FINE_LOCATION] ?: isLocationPermissionGranted
                isBluetoothConnectPermissionGranted =
                    it[Manifest.permission.BLUETOOTH_CONNECT] ?: isBluetoothConnectPermissionGranted
                isBluetoothScanPermissionGranted =
                    it[Manifest.permission.BLUETOOTH_SCAN] ?: isBluetoothScanPermissionGranted
            }
        requestPermissions()

        binding.btnCentral.setOnClickListener {
            startActivity(Intent(applicationContext, ActivityCentral::class.java))
        }
        binding.btnPeripheral.setOnClickListener {
            startActivity(Intent(applicationContext, ActivityPeripheral::class.java))
        }
    }

    private fun requestPermissions() {
        Log.i(TAG, "requestPermissions()")

        isLocationPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        isBluetoothConnectPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED

        isBluetoothScanPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_SCAN
        ) == PackageManager.PERMISSION_GRANTED

        val permissionRequest: MutableList<String> = ArrayList()

        if (!isLocationPermissionGranted) {
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (!isBluetoothConnectPermissionGranted) {
            permissionRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        if (!isBluetoothScanPermissionGranted) {
            permissionRequest.add(Manifest.permission.BLUETOOTH_SCAN)
        }

        if (permissionRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }
}