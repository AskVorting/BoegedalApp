package com.example.boegedalapp.accountNav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.boegedalapp.presentation.login_screen.LoggedInScreen
import com.example.boegedalapp.presentation.login_screen.SignInScreen
import com.example.boegedalapp.presentation.signup_screen.SignUpScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    val _startDestination = if (Firebase.auth.currentUser != null) {
        Screens.LoggedInScreen.route
    } else {
        Screens.SignInScreen.route
    }

    NavHost(
        navController = navController,

        startDestination = _startDestination

    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController)
        }

        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController)
        }

        composable(route = Screens.LoggedInScreen.route) {
            LoggedInScreen(navController)
        }
    }

}