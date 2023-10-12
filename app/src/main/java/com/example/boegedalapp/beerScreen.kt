

package com.example.boegedalapp

import android.app.Activity
import android.app.ActivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.boegedalapp.BeerList

@Composable
fun BeerScreen(navController: NavHostController){


}



class BeerList: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: listAdapter
    private lateinit var BeerList: BeerList

    var dataArrayList = ArrayList<BeerList?>()

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}



