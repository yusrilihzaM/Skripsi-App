package com.yusril.skripsi_app.adapter

import android.R.attr.data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusril.skripsi_app.databinding.ItemDataTouristBinding
import com.yusril.skripsi_app.response.DataFutureByTypeMethodItem
import com.yusril.skripsi_app.response.DataTouristItem


class DataFutureListAdapter(private val listData: ArrayList<DataFutureByTypeMethodItem>): RecyclerView.Adapter<DataFutureListAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    fun clear() {
        val size: Int = listData.size
        listData.clear()
        notifyItemRangeRemoved(0, size)
    }
    inner class ListViewHolder(private val binding: ItemDataTouristBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataFutureByTypeMethodItem){
            binding.no.text=data.no
            binding.tvbulan.text=data.seasonFuture
            binding.tvtahun.text=data.yearFuture
            binding.jumlah.text=data.adjustedForecast
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemDataTouristBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: DataFutureByTypeMethodItem)
    }
}