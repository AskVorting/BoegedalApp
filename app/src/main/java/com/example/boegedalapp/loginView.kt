package com.example.boegedalapp

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import javax.inject.Inject

class EmailPasswordActivity : Activity() {
    private lateinit var auth: FirebaseAuth
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
            }
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun reload() {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}
//Everything above might be redundant later


class MakeItSoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LogIn() }
    }
}
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(){
    data class LoginUiState(
        val email: String = "",
        val password: String = ""
    )
    var uiState = mutableStateOf(LoginUiState())
        private set
}
/* //Unsure about this
@Composable
fun LoginScreen(popUpScreen: () -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
    val uiState by LoginViewModel.uiState

    BasicToolbar(AppText.login_details)

    Column([...]) {
        EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())

        [...]
    }
}*/

// Login Outline
@Composable
fun LogIn()
{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This is the Log In page",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )

    }
}