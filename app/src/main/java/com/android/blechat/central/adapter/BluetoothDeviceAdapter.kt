package com.android.blechat.central.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.blechat.R
import com.android.blechat.central.data.BluetoothDeviceItem

class BluetoothDeviceAdapter(
    var devices: List<BluetoothDeviceItem>
) : RecyclerView.Adapter<BluetoothDeviceAdapter.BluetoothItemHolder>() {
    inner class BluetoothItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.text_view_device_name)
        val textViewAddress: TextView = view.findViewById(R.id.text_view_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_item, parent, false)
        return BluetoothItemHolder(view)
    }

    override fun onBindViewHolder(holder: BluetoothItemHolder, position: Int) {
        holder.textViewName.text = devices[position].deviceName
        holder.textViewAddress.text = devices[position].address
    }

    override fun getItemCount() = devices.size
}