

package com.example.boegedalapp



import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext.Auth
import com.plcoding.BoegedalApp.BeerItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.boegedalapp.accountNav.NavigationGraph
import com.example.boegedalapp.accountNav.Screens


@Composable
fun BeerListScreen(
    viewModel: AppViewModel,
    onBeerSelected: (BeerItem) -> Unit,
    navController: NavHostController
) {
    val beerList by viewModel.beerList.collectAsState()

    if (beerList.isEmpty()) {
        Text(text = "No beers available.")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(beerList) { beer ->
                    BeerListItem(beer, onBeerSelected)
                }
            }

            //knap som skulle være med pga opgaven krav.....
            /*
            Button(onClick = { navController.navigateUp()}) {
                Text(text = "Back")

            }
            */
            val user = Firebase.auth.currentUser
                if(user != null) {
                    Button(
                        onClick = {
                            navController.navigate("addBeer")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Add Beer")
                    }
                } else {
                    Text(
                        modifier = Modifier
                            .padding(15.dp),
                        text = "You need to log in to add beers!",
                        fontWeight = FontWeight.Bold, color = Color.Black
                    )
                    Button(
                        onClick = {
                            navController.navigate("account")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Log In")
                    }
                }

        }
    }
}



@Composable
fun BeerListItem(
    beer: BeerItem,
    onBeerSelected: (BeerItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onBeerSelected(beer) }
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .then(Modifier.background(Color.White, MaterialTheme.shapes.medium))
            .then(Modifier.shadow(4.dp))
    ) {
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Display the beer image using Coil to load the image from firebase storage
                Image(
                    painter = rememberImagePainter(data = beer.imageURL),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp)) ,
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Display beer details in two lines
                Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = beer.nameOfBeer,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${beer.typeOfBeer}, ${beer.alcoholContent}"
                )
                Text(
                    text = beer.price,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}






@Composable
fun BeerDetailScreen(beer: BeerItem, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = beer.nameOfBeer,
            style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 40.sp),

        )
        // Load and display the beer's image using the imageURL from firebase storage
        Image(
            painter = rememberImagePainter(beer.imageURL),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(text = "Type: ${beer.typeOfBeer}")
        Text(text = "Alcohol Content: ${beer.alcoholContent}")
        Text(text = "Price: ${beer.price}")
        Text(text = "Description: ${beer.description}")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBeerScreen(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var alcoholContent by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Add a New Beer",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.padding(16.dp)
            )
        }

        item {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name of Beer") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Type of Beer") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = alcoholContent,
                onValueChange = { alcoholContent = it },
                label = { Text("Alcohol Content") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
        }


        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }
            ) {
                if (imageUri != null) {
                    // Display the selected image
                    Image(
                        painter = rememberImagePainter(data = imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Display a box outline with the text "Select Image"
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                    ) {
                        Text(
                            text = "Select Image",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Button(
                onClick = {
                    val newBeer = BeerItem(
                        nameOfBeer = name,
                        typeOfBeer = type,
                        alcoholContent = alcoholContent,
                        price = price,
                        description = description,
                        imageURL = imageUri.toString()
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            sendFirebaseData(newBeer, imageUri!!, viewModel)
                            getFirebaseData(viewModel)
                        } catch (e: Exception) {
                            println("Error: ${e.message}")
                        }
                    }
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Beer")
            }


    }
}
}
