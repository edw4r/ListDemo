package com.example.listdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listdemo.R
import com.example.listdemo.databinding.ItemCurrencyInfoDefaultBinding
import com.example.listdemo.viewmodel.CurrencyInfoItemViewModel

class CurrencyInfoListAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<CurrencyInfoItemViewModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val VIEW_TYPE_CURRENCY_INFO_DEFAULT = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CurrencyInfoItemViewModel>() {
            override fun areItemsTheSame(
                oldItem: CurrencyInfoItemViewModel,
                newItem: CurrencyInfoItemViewModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CurrencyInfoItemViewModel,
                newItem: CurrencyInfoItemViewModel
            ): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

//    var viewHolders = ArrayList<RecyclerView.ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CURRENCY_INFO_DEFAULT -> {
                val binding: ItemCurrencyInfoDefaultBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_currency_info_default,
                    parent,
                    false
                )
                CurrencyInfoDefaultItemViewHolder(binding)
            }
            else -> throw IllegalStateException("Item type do not support in Adapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrencyInfoDefaultItemViewHolder -> {
                val itemViewModel = getItem(position)
                if(itemViewModel is CurrencyInfoItemViewModel){
                    holder.binding.viewModel = itemViewModel
                    holder.binding.lifecycleOwner = lifecycleOwner
//                    viewHolders.add(holder)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CurrencyInfoItemViewModel -> VIEW_TYPE_CURRENCY_INFO_DEFAULT
            else -> VIEW_TYPE_CURRENCY_INFO_DEFAULT
        }
    }

    class CurrencyInfoDefaultItemViewHolder(val binding: ItemCurrencyInfoDefaultBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}