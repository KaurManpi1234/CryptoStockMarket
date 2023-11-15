package com.example.cryptostockmarket

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.cryptostockmarket.databinding.FragmentWatchlistBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


   class WatchlistFragment : Fragment() {
    private lateinit var binding: FragmentWatchlistBinding
   private lateinit var watchlist:ArrayList<String>

 private lateinit var watchListItem:ArrayList<CryptoCurrency>

     @SuppressLint("SuspiciousIndentation")
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
     ): View? {
        binding = FragmentWatchlistBinding.inflate(layoutInflater, container, false)
       readData()
        lifecycleScope.launch(Dispatchers.IO){
            val res=ApiUtilities.getInstance().create(ApiInterface::class.java)
                .getMarketData()
            if (res.body()!=null){
                withContext(Dispatchers.Main){
         watchListItem=ArrayList()
                    watchListItem.clear()
                    for (watchData in watchlist){
                        for (item in res.body()!!.data.cryptoCurrencyList) {
                            if (watchData==item.symbol){
                                watchListItem.add(item)

                            }
                        }
                    }
                    binding.spinKitView.visibility = GONE
                    binding.watchlistRecyclerView.adapter = MarketAdapter(requireContext(),watchListItem,"watchfragment")
                }
            }
        }
        return binding.root
    }
    private fun readData() {
        val sharedPreferences=requireContext().getSharedPreferences("watchList", Context.MODE_PRIVATE)
        val gson= Gson()
        val json=sharedPreferences.getString("watchList",ArrayList<String>().toString())
        val type=object : TypeToken<ArrayList<String>>(){}.type
        watchlist=gson.fromJson(json,type)
    }

}