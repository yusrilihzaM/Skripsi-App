package com.yusril.skripsi_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusril.skripsi_app.adapter.HomeListAdapter.*
import com.yusril.skripsi_app.databinding.ItemMenuBinding
import com.yusril.skripsi_app.entity.Menu

class HomeListAdapter(private val List: ArrayList<Menu>): RecyclerView.Adapter<ListViewHolder>() {
    fun clear() {
        List.clear()
        notifyDataSetChanged()
    }
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    inner class ListViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu){
            with(binding){
                icMenu?.let {
                    Glide.with(itemView.context)
                        .load(menu.ic)
                        .into(it)
                }
                titleMenu.text=menu.title
                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(menu)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(List[position])
    }

    override fun getItemCount(): Int {
        return 1
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Menu)
    }
}