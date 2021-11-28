package com.yusril.skripsi_app.adapter

import android.R.attr.data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusril.skripsi_app.databinding.ItemDataTouristBinding
import com.yusril.skripsi_app.databinding.ItemMenuForecastingBinding
import com.yusril.skripsi_app.databinding.ItemSettingsBinding
import com.yusril.skripsi_app.entity.MenuForecast
import com.yusril.skripsi_app.response.DataTouristItem


class MenuSettingsListAdapter(private val listData: ArrayList<MenuForecast>): RecyclerView.Adapter<MenuSettingsListAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setListData(){
        listData.clear()
        notifyDataSetChanged()
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: ItemSettingsBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MenuForecast){
            binding.title.text=data.title
            binding.subTitle.text=data.subTitle
            Glide.with(itemView.context)
                .load(data.ic)
                .into(binding.ic)
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        listData.get(position).let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: MenuForecast)
    }
}