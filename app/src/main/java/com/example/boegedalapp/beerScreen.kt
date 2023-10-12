

package com.example.boegedalapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavHostController
import com.example.boegedalapp.databinding.ListViewBinding
import com.plcoding.BoegedalApp.BeerList



@Composable
fun BeerScreen(navController: NavHostController) {


    class BeerListView() : AppCompatActivity() {


        private lateinit var binding: ListViewBinding
        private lateinit var listAdapter: listAdapter
        private lateinit var dataList: BeerList

        var dataArrayList = ArrayList<BeerList?>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ListViewBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val nameList = arrayOf("Gylden K", "Flower Power")
            val typeOfBeerList = arrayOf("Gylden", "IPA")
            val alcoholContentList = arrayOf("4.6%", "6.5%")
            val priceList = arrayOf("kr. 30", "kr. 40")

            val imageList = intArrayOf(
                R.drawable.beergyldenk,
                R.drawable.beerflowerpower
            )

            for (i in imageList.indices) {
                dataList = BeerList(
                    nameList[i],
                    typeOfBeerList[i],
                    alcoholContentList[i],
                    imageList[i],
                    priceList[i]
                )
                dataArrayList.add(dataList)
            }
            listAdapter = listAdapter(this@BeerListView, dataArrayList)
            binding.listView.adapter = listAdapter
            binding.listView.isClickable = true

            binding.listView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this@BeerListView, DetailedActivity::class.java)
                    intent.putExtra("nameOfBeer", nameList[position])
                    intent.putExtra("typeOfBeer", typeOfBeerList[position])
                    intent.putExtra("alcoholContent", alcoholContentList[position])
                    intent.putExtra("image", imageList[position])
                    intent.putExtra("price", priceList[position])
                    startActivity(intent)
                }
        }
    }


}