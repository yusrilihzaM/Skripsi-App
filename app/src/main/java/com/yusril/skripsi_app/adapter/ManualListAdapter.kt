package com.yusril.skripsi_app.adapter

import android.R.attr.data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusril.skripsi_app.databinding.ItemDataTouristBinding
import com.yusril.skripsi_app.databinding.ItemManualBinding
import com.yusril.skripsi_app.response.DataManualItem
import com.yusril.skripsi_app.response.DataTouristItem


class ManualListAdapter(private val listData: ArrayList<DataManualItem>): RecyclerView.Adapter<ManualListAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    fun clear() {
        val size: Int = listData.size
        listData.clear()
        notifyItemRangeRemoved(0, size)
    }
    inner class ListViewHolder(private val binding: ItemManualBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataManualItem){
            binding.no.text=data.no
            binding.t.text=data.T
            binding.at.text=data.dataPengunjung
            binding.ctdma.text=data.ctdma
            binding.ratio.text=data.ratio
            binding.seasonal.text=data.smoothed
            binding.unadjustedForecast.text=data.unadjusted
            binding.adjustedForecast.text=data.adjusted
            binding.error.text=data.error
            binding.mad.text=data.mad
            binding.mape.text=data.mape
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemManualBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: DataManualItem)
    }
}