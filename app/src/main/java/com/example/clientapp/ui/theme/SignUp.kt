package com.example.clientapp.ui.theme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState

import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource // Added this import
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.clientapp.R

class SignUp : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClienTappTheme {
                val isLoading = mainViewModel.isLoading.value
                if (isLoading) {
                    LoadingScreen() // Affiche la barre de chargement
                } else {
                    FirstPage() // Affiche la page principale
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator() // Utilise CircularProgressIndicator pour une barre circulaire
    }
}
@Composable
fun Cit(){
    Box()
    {
        Text(text= "Sign UP",
            fontSize=28.sp,
            fontWeight = FontWeight.Bold,
            modifier=Modifier.fillMaxSize(),
            color= Color.Black,
            textAlign = TextAlign.Center)
    }
}
@Composable
fun FirstPage() {
    val img = painterResource(R.drawable.blue_green_wall_background)
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = img,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment= Alignment.Center
        ) {

            UsernamePasswordForm()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment= Alignment.Center
        ){
            Cit()
        }
    }

}
@Composable
fun UsernamePasswordForm() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment =Alignment.CenterHorizontally

    ) {
        LogoSection()
        Spacer(modifier=Modifier.height(8.dp))

        Text(text = "Username",
            style = MaterialTheme.typography.labelMedium,
            modifier=Modifier.align(Alignment.Start),
            fontWeight = FontWeight.Bold,
            fontSize=24.sp)
        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            placeholder = { Text("Enter your username") }

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Password", style = MaterialTheme.typography.labelMedium,
            modifier=Modifier.align(Alignment.Start),
            fontWeight = FontWeight.Bold,
            fontSize=24.sp)
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            placeholder = { Text("Enter your password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Row to hold the buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    // Afficher un message de "Connexion" dans la Snackbar
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Connexion réussie")
                    }
                    // Add your connection logic here
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Connexion")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    // Clear the username and password fields
                    username = ""
                    password = ""
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Champs effacés")
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Delete")
            }
        }
    }
    // Affichage de la Snackbar
    SnackbarHost(hostState = snackbarHostState)
}
//le logo de profile
@Composable
fun LogoSection(){
    val img = painterResource(R.drawable.logo_section)
    Image(
        painter=img,
        contentDescription="LogoSection",
        modifier=Modifier
            .size(110.dp)
            .clip(CircleShape),
        contentScale= ContentScale.Crop

    )
}
class MainViewModel : ViewModel() {
    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        // Simule un chargement en utilisant un délai de 2 secondes
        viewModelScope.launch {
            delay(2000) //  ajuster le temps ici
            _isLoading.value = false
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClienTappTheme {
        FirstPage()
    }
}

