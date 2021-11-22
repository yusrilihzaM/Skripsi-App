package com.yusril.skripsi_app.adapter

import android.R.attr.data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusril.skripsi_app.databinding.ItemDataTouristBinding
import com.yusril.skripsi_app.databinding.ItemMenuForecastingBinding
import com.yusril.skripsi_app.entity.MenuForecast
import com.yusril.skripsi_app.response.DataTouristItem


class MenuForecastListAdapter(private val listData: ArrayList<MenuForecast>): RecyclerView.Adapter<MenuForecastListAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: ItemMenuForecastingBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MenuForecast){
            binding.tvTitle.text=data.title
            binding.tvDesc.text=data.subTitle
            Glide.with(itemView.context)
                .load(data.ic)
                .into(binding.ic)
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemMenuForecastingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: MenuForecast)
    }
}