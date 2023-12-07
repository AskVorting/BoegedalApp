package com.example.boegedalapp.accountNav

sealed class Screens(val route: String) {
    object SignInScreen : Screens(route = "SignIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object LoggedInScreen : Screens(route = "LoggedIn_Screen")


}