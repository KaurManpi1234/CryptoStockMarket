package com.example.cryptostockmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.PopupMenu

import androidx.navigation.fragment.findNavController
import com.example.cryptostockmarket.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private val binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding!!.toolbar)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController=navHostFragment!!.findNavController()
        val popupMenu=PopupMenu(this, View(this))
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val smoothBottomBar = binding.bottomBar

        smoothBottomBar.setupWithNavController(popupMenu.menu,navController)




    }

}




