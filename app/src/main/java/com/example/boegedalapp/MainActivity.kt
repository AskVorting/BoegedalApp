
package com.example.boegedalapp


//import image

//import size
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.ktx.*
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.boegedalapp.ui.theme.M3NavigationDrawerTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.plcoding.BoegedalApp.BeerItem
import com.plcoding.BoegedalApp.NavigationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

//import data








class AppViewModel : ViewModel() {



    private val _beerList = MutableStateFlow<List<BeerItem>>(emptyList())
    val beerList: StateFlow<List<BeerItem>> = _beerList

    // Function to update the beer list in the ViewModel
    fun updateBeerList(newBeerList: List<BeerItem>) {
        _beerList.value = newBeerList
    }
}

fun getFirebaseData(viewModel: AppViewModel) {
    val db = Firebase.firestore // Initialize Firestore

    val collectionRef = db.collection("/Beers") // Reference to your Firestore collection
    val beerList = mutableListOf<BeerItem>()

    collectionRef.get().addOnSuccessListener { documents ->

        val storageRef = Firebase.storage.reference

        for (document in documents) {
            val beer = document.toObject(BeerItem::class.java)

            // Download the image from Firebase Storage
            beerList.add(beer)
            viewModel.updateBeerList(beerList)
        }
    }
}


fun sendFirebaseData(beerItem: BeerItem, imageUri: Uri, viewModel: AppViewModel) {
    val db = Firebase.firestore

    var storageRef = Firebase.storage.reference
    val beerCollection = db.collection("Beers")

    // Create a unique name for the image file in Firebase Storage
    val imageFileName = UUID.randomUUID().toString() + ".jpg"
    val imageRef = storageRef.child("images/$imageFileName")

    val newBeer = hashMapOf(
        "nameOfBeer" to beerItem.nameOfBeer,
        "typeOfBeer" to beerItem.typeOfBeer,
        "alcoholContent" to beerItem.alcoholContent,
        "price" to beerItem.price,
        "description" to beerItem.description,
        "imageURL" to beerItem.imageURL // Store the image file name in Firestore
    )

    // Upload the image to Firebase Storage
    // Upload the image to Firebase Storage
    imageRef.putFile(imageUri)
        .addOnSuccessListener { uploadTask ->
            // Image uploaded successfully
            // Get the download URL of the uploaded image
            uploadTask.storage.downloadUrl
                .addOnSuccessListener { uri ->
                    val imageURL = uri.toString()

                    // Add the new beer data to Firestore with the imageURL
                    val newBeer = hashMapOf(
                        "nameOfBeer" to beerItem.nameOfBeer,
                        "typeOfBeer" to beerItem.typeOfBeer,
                        "alcoholContent" to beerItem.alcoholContent,
                        "price" to beerItem.price,
                        "description" to beerItem.description,
                        "imageURL" to imageURL // Store the image URL in Firestore
                    )

                    beerCollection.add(newBeer)
                        .addOnSuccessListener { documentReference ->
                            // Document added successfully
                            // Retrieve the updated list from Firebase and update the ViewModel

                            beerCollection.get()
                                .addOnSuccessListener { result ->
                                    val updatedBeerList = result.toObjects(BeerItem::class.java)
                                    viewModel.updateBeerList(updatedBeerList)
                                }
                                .addOnFailureListener { e ->
                                    // Handle errors while fetching the updated list
                                }
                        }
                        .addOnFailureListener { e ->
                            // Handle errors
                        }
                }
                .addOnFailureListener { e ->
                    // Handle errors related to getting the download URL
                }
        }
        .addOnFailureListener { e ->
            // Handle errors related to image upload
        }

}







@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    // Create an instance of the ViewModel


    // Create an instance of your ViewModel
    val viewModel: AppViewModel by viewModels()




    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        getFirebaseData(viewModel)

        setContent {
            //Create my Navigation Drawer
            M3NavigationDrawerTheme {
                val navController = rememberNavController()
                val items = listOf(
                    NavigationItem(
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,

                        ),
                    NavigationItem(
                        title = "Beers",
                        selectedIcon = Icons.Filled.Favorite,
                        unselectedIcon = Icons.Outlined.FavoriteBorder,
                    ),
                    NavigationItem(
                        title = "Settings",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                    ),
                    NavigationItem(
                        title = "About",
                        selectedIcon = Icons.Filled.Info,
                        unselectedIcon = Icons.Outlined.Info,
                    ),
                    NavigationItem(
                        title = "Quit",
                        selectedIcon = Icons.Filled.Close,
                        unselectedIcon = Icons.Outlined.Close,
                    )
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //properties of ModalNavigationDrawer
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))
                                items.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        label = {
                                            Text(text = item.title)
                                        },
                                        selected = index == selectedItemIndex,
                                        onClick = {
//                                            navController.navigate(item.route)
                                            selectedItemIndex = index
                                            scope.launch {
                                                drawerState.close()
                                            }
                                            //launch activity
                                            when (index) {
                                                0 -> {
                                                    //mainScreen
                                                    navController.navigate("home")


                                                }

                                                1 -> {
                                                    //Beers
                                                    navController.navigate("beerlistview")


                                                }

                                                2 -> {
                                                    //settings
                                                    navController.navigate("Settings")
                                                }

                                                3 -> {
                                                    //about
                                                    navController.navigate("addBeer")
                                                }

                                                4 -> {
                                                    //quit
                                                }
                                            }

                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        },
                                        badge = {
                                            item.badgeCount?.let {
                                                Text(text = item.badgeCount.toString())
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                            }
                        },
                        drawerState = drawerState
                    )
                    {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.boegedal),
                                                contentDescription = "Logo",
                                                modifier = Modifier
                                                    .width(300.dp)
                                                    .height(75.dp)
                                                    .padding(8.dp)
                                            )

                                        }
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.List,
                                                contentDescription = "Menu",
                                                modifier = Modifier
                                                    .width(50.dp)
                                                    .height(50.dp)
                                                    .padding(8.dp)
                                            )
                                        }
                                    }
                                )
                            },


                            content = { innerPadding -> // Add padding to inner elements
                                NavHost(navController = navController, startDestination = "home" )
                                {
                                    //
                                    composable("home") {
                                        HomeScreen()
                                    }

                                    composable("about") {
                                        aboutScreen()
                                    }

                                    composable("Settings") {
                                        Settings()
                                    }

                                    composable("beerlistview") {
                                        BeerListScreen(
                                            viewModel = viewModel,
                                            onBeerSelected = { selectedBeer ->
                                                val route = "beerDetail/${selectedBeer.nameOfBeer}" // Use the beer's name
                                                navController.navigate(route)
                                            },
                                            navController = navController
                                        )
                                    }
                                    composable(
                                        "beerDetail/{beerName}",
                                        arguments = listOf(navArgument("beerName") { type = NavType.StringType })
                                    ) { backStackEntry ->
                                        val beerName = backStackEntry.arguments?.getString("beerName")
                                        val selectedBeer = viewModel.beerList.value.firstOrNull { it.nameOfBeer == beerName }

                                        if (selectedBeer != null) {
                                            BeerDetailScreen(selectedBeer, navController)
                                        } else {
                                            Text("Beer not found.")
                                        }
                                    }


                                    composable("addBeer") {
                                        // Your AddBeerScreen with the capability to add beer
                                        AddBeerScreen(
                                            navController = navController,
                                            viewModel = viewModel,
                                        )
                                    }

                                    composable("quit") {
                                        finish()
                                    }



                                }
                            }

                        )
                    }
                }
            }
        }
    }
}


