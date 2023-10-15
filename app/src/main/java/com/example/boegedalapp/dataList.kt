package com.plcoding.BoegedalApp

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

data class BeerItem(
    var nameOfBeer: String = "",
    var typeOfBeer: String = "",
    var alcoholContent: String = "",
    var price: String = "",
    var description: String = "",
    var imageURL: String = ""

)