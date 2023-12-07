package com.example.boegedalapp.presentation.login_screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.boegedalapp.R
import com.example.boegedalapp.accountNav.Screens
import com.example.boegedalapp.ui.theme.lightBlue
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signInState.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "Log In",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
        )
        Text(
            text = "Enter your credential's to log in",
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = Color.Gray,
        )
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            /*colors = TextFieldDefaults.textFieldColors(
                containerColor = lightBlue,
                cursorColor = Color.Black,
                disabledLabelColor = lightBlue, unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),*/ shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            /*colors = TextFieldDefaults.textFieldColors(
                containerColor = lightBlue,
                cursorColor = Color.Black,
                disabledLabelColor = lightBlue, unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ), */shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
                Text(text = "Password")
            }
        )

        Button(
            onClick = {
                scope.launch {
                    viewModel.loginUser(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, contentColor = Color.White
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text = "Sign In", color = Color.White, modifier = Modifier.padding(7.dp))
        }
        Text(
            modifier = Modifier
                .padding(15.dp)
                .clickable {
                    navController.navigate(Screens.SignUpScreen.route)
                },
            text = "Don't Have an account? Sign Up",
            fontWeight = FontWeight.Bold, color = Color.Black
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }

        }

            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                        navController.navigate(Screens.LoggedInScreen.route)
                    }
                }
            }

            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

@Composable
fun LogoutButton(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    Button(
        onClick = {
            auth.signOut()
            navController.navigate(Screens.SignInScreen.route)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Log-out")

    }
}

@Composable
fun LoggedInScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You are Logged In",
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "You can add beers in the Beers menu",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        LogoutButton(navController = navController)
    }
}