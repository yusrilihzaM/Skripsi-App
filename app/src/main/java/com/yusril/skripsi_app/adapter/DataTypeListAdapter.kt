package com.yusril.skripsi_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusril.skripsi_app.databinding.ItemDatatypeBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem

class DataTypeListAdapter(private val listData: ArrayList<DataTouristTypeItem>): RecyclerView.Adapter<DataTypeListAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: ItemDatatypeBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataType: DataTouristTypeItem){
            binding.no.text=dataType.no
            binding.title.text=dataType.touristDataType
            itemView.setOnClickListener{
                onItemClickCallback?.onItemClicked(dataType)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemDatatypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
    interface OnItemClickCallback {
        fun onItemClicked(dataType: DataTouristTypeItem)
    }
}