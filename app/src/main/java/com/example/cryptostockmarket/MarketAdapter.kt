package com.example.cryptostockmarket

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptostockmarket.databinding.CurrencyItemLayoutBinding


class MarketAdapter(var context: Context, var list: List<CryptoCurrency>, var type: String) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketAdapter.MarketViewHolder {
        val binding = CurrencyItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MarketViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun upDateData(dataItem:List<CryptoCurrency>){
        list=dataItem
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MarketAdapter.MarketViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MarketViewHolder(private val binding: CurrencyItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CryptoCurrency) {
            binding.currencyNameTextView.text = item.name
            binding.currencySymbolTextView.text = item.symbol

            Glide.with(context)
                .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png")
                .thumbnail(
                    Glide.with(context).load(
                        R.drawable.spinner
                    )
                ).into(binding.currencyImageView)

            Glide.with(context)
                .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png")
                .thumbnail(
                    Glide.with(context).load(
                        R.drawable.spinner
                    )
                ).into(binding.currencyChartImageView)

            binding.currencyPriceTextView.text =
                "${String.format("$%.02f", item.quotes[0].price)}"

            if (item.quotes!![0].percentChange24h > 0) {
                binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
                binding.currencyChangeTextView.text =
                    "+ ${String.format("%.02f", item.quotes[0].percentChange24h)} %"
            } else {
                binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
                binding.currencyChangeTextView.text =
                    "${String.format("%.02f", item.quotes[0].percentChange24h)} %"
            }
          itemView.setOnClickListener {
                if (type == "home") {
                    findNavController(it).navigate(
                        HomeFragmentDirections.actionHomeFragment2ToDetailsFragment(item)
                    )
                } else if (type == "market") {
                    findNavController(it).navigate(
                        MarketFragmentDirections.actionMarketFragment2ToDetailsFragment(item)
                    )
                }else{
                    findNavController(it).navigate(
                     WatchlistFragmentDirections.actionWatchlistFragment2ToDetailsFragment(item)
                    )
                }
            }
        }

    }}