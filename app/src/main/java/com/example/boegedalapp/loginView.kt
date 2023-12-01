package com.example.boegedalapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.boegedalapp.presentation.signup_screen.SignUpScreen
import com.example.boegedalapp.accountNav.Screens
import com.example.boegedalapp.accountNav.NavigationGraph
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.plcoding.BoegedalApp.BeerItem
import androidx.navigation.compose.rememberNavController
import com.example.boegedalapp.data.AuthRepository
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.boegedalapp.presentation.login_screen.SignInScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost


@Composable
fun AccountScreen()
{
    NavigationGraph()
}
