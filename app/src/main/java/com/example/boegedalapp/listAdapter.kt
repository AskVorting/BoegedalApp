package com.example.boegedalapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.plcoding.BoegedalApp.BeerList
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView

/*
class listAdapter(context: Context, dataArraylist: ArrayList<BeerList?>) :
    ArrayAdapter<BeerList>(context, R.layout.list_item, dataArraylist!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val listData = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val listImage = view!! .findViewById<ImageView>(R.id.listImage)
        var listName = view.findViewById<TextView>(R.id.listName)
        var listTypeOfBeer = view.findViewById<TextView>(R.id.listTypeOfBeer)


        listImage.setImageResource(listData!!.image)
        listName.text = listData.nameOfBeer
        listTypeOfBeer.text = listData.typeOfBeer

        return view
    }
}

*/