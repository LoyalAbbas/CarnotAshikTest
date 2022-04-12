package com.app.ashikcarnottest.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.ashikcarnottest.R
import com.app.ashikcarnottest.databinding.MarketItemViewBinding
import java.util.*

class MarketItemListAdapter (private val context: Context,
    private var marketItemList: ArrayList<MarketItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewBinding: MarketItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.market_item_view, parent, false)
        return ItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as ItemViewHolder
        holder.viewBinding.txtState.text = marketItemList.get(position).state
        holder.viewBinding.txtDistrict.text = marketItemList.get(position).district
        holder.viewBinding.txtMarket.text = marketItemList.get(position).market
        holder.viewBinding.txtCommodity.text = marketItemList.get(position).commodity
        holder.viewBinding.txtVariety.text = marketItemList.get(position).variety
        holder.viewBinding.txtArrivalDate.text = marketItemList.get(position).arrival_date
        holder.viewBinding.txtMinPrice.text = marketItemList.get(position).min_price
        holder.viewBinding.txtMaxPrice.text = marketItemList.get(position).max_price
        holder.viewBinding.txtModalPrice.text = marketItemList.get(position).modal_price

    }

    override fun getItemCount(): Int {
        return marketItemList.size
    }

    inner class ItemViewHolder(var viewBinding: MarketItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root)
}