

package com.example.boegedalapp



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.os.Parcelable
import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.plcoding.BoegedalApp.BeerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import androidx.compose.runtime.remember

import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.activity.result.ActivityResultLauncher

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.remember

import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter


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
            modifier = Modifier.fillMaxSize()
                .padding(top = 60.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(beerList) { beer ->
                    BeerListItem(beer, onBeerSelected)
                }
            }

            // Button to navigate to the "Add Beer" screen
            Button(
                onClick = {
                    navController.navigate("addBeer") // Navigate to the "addBeer" destination
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Add Beer")
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
            .padding(8.dp) // Add padding to the Card
            .clip(MaterialTheme.shapes.medium) // Apply a rounded shape to the Card
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
            // Display the beer image
            /*Image(
                painter = painterResource(id = beer.image),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            */
            Spacer(modifier = Modifier.width(16.dp))

            // Display beer details in two lines
            Column(
                modifier = Modifier.weight(1f), // Allow the text to take available space
                verticalArrangement = Arrangement.spacedBy(4.dp) // Add vertical spacing
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
    // Create a composable to display detailed information about the selected beer.
    // You can use the `beer` object to display details about the beer.
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = beer.nameOfBeer, style = TextStyle(fontWeight = FontWeight.Bold))
        /*Image(
            painter = painterResource(id = beer.image),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )*/

        Text(text = "Type: ${beer.typeOfBeer}")
        Text(text = "Alcohol Content: ${beer.alcoholContent}")
        Text(text = "Price: ${beer.price}")
        Text(text = "Description: ${beer.description}")

        Spacer(modifier = Modifier.height(16.dp))

        // Add a "Back" button to navigate back to the list view
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Add a New Beer",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name of Beer") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Type of Beer") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = alcoholContent,
            onValueChange = { alcoholContent = it },
            label = { Text("Alcohol Content") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()        )

        var imageUri by remember { mutableStateOf<Uri?>(null) }

        Image(
            painter = rememberImagePainter(imageUri),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clickable {
                    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                        imageUri = uri
                    }
                    galleryLauncher("image/*")
                }
        )

        Spacer(modifier = Modifier.height(24.dp))



        Button(
            onClick = {
                val newBeer = BeerItem(
                    nameOfBeer = name,
                    typeOfBeer = type,
                    alcoholContent = alcoholContent,
                    price = price,
                    description = description
                )




                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // First, add the beer to Firebase
                        sendFireBaseData(newBeer, viewModel)

                        // Then, fetch the updated data
                        getFirebaseData(viewModel)
                    } catch (e: Exception) {
                        // If an error occurs, display an error message
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















/*
data class BeerList (
    val nameOfBeer: String,
    val typeOfBeer: String,
    val alcoholContent: String,
    val image : Int,
    val price: String,
    val description: String
)



@Composable
fun BeerScreen(navController: NavHostController) {

    BeerRepository()
    val dataArrayList = BeerRepository().getBeers()

    var currentBeerItem by remember { mutableStateOf<BeerList?>(null) }

    if (currentBeerItem != null) {
        //navigate to detail screen
        navController.navigate("detailed")

        }


     else {
        BeerListView(dataList = dataArrayList, currentBeerItem = currentBeerItem) { selectedBeer ->
            currentBeerItem = selectedBeer

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerListView(
    dataList: List<BeerList>,
    currentBeerItem: BeerList?,
    onItemClicked: (BeerList) -> Unit
) {
    Scaffold(
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
            ) {
                items(dataList) { beerItem ->
                    BeerListItem(beerItem) {
                        onItemClicked(beerItem)
                    }
                }
            }
        }, modifier = Modifier.padding(top = 56.dp) // Add this line
    )
}

@Composable
fun BeerListItem(beerItem: BeerList, onItemClick: (BeerList) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(beerItem) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Display the beer image
            Image(
                painter = painterResource(id = beerItem.image),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Display beer details in two lines
            Column(
                modifier = Modifier.weight(1f), // Allow the text to take available space
                verticalArrangement = Arrangement.spacedBy(4.dp) // Add vertical spacing
            ) {
                Text(
                    text = beerItem.nameOfBeer,
                    style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${beerItem.typeOfBeer}, ${beerItem.alcoholContent}"
                )
                Text(
                    text = beerItem.price,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun BeerDetailScreen(beerItem: BeerListNavType, navController: NavHostController) {
    val navController = rememberNavController()
    val beerItem = remember { navController.currentBackStackEntry?.arguments?.getParcelable<BeerListNavType>("beer") }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(text = beerItem.nameOfBeer, style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold))
        Image(
            painter = painterResource(id = beerItem.image),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )


        Text(text = "Type: ${beerItem.typeOfBeer}")
        Text(text = "Alcohol Content: ${beerItem.alcoholContent}")
        Text(text = "Price: ${beerItem.price}")
        Text(text = "Description: ${beerItem.description}")

        Spacer(modifier = Modifier.height(16.dp))

        // Add a "Back" button to navigate back to the list view
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}



class BeerRepository {
    private val dataArrayList = mutableListOf<BeerList>()

    init {
        // Populate the dataArrayList with your beer items here.
        dataArrayList.add(BeerList("Gylden K", "Gylden", "4.6%", R.drawable.beergyldenk, "kr. 30",  "Mørk gylden øl, der dufter af karamel og rørsukker. Gylden Karamel har en aromatisk smag af karamelliseret sukker, cappuccino og chokolade, calvado og malt. Meget let bitterhed. Virkelig en dejlig øl, der fylder munden og efterlader en behagelig varme. "),  )
        dataArrayList.add(BeerList("Flower Power", "IPA", "6.5%", R.drawable.beerflowerpower, "kr. 40", "FlowerPower er skabet til en lille lokal festival som ville have noget øl alle kan drikke. Vi brygger vores NIPA med en rimelig slank krop for en Bøgedal øl og tørhumler med kraftige blomstrede og citrus humler fra USA. Blomsterne aromaen springer ud af glasset" ))
        // Add more beers as needed.
    }

    fun getBeers(): List<BeerList> {
        return dataArrayList
    }
}


/*

    class BeerListView() : AppCompatActivity() {


        private lateinit var binding: ListViewBinding
        private lateinit var listAdapter: listAdapter
        private lateinit var dataList: BeerList

        var dataArrayList = ArrayList<BeerList?>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ListViewBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val nameList = arrayOf("Gylden K", "Flower Power")
            val typeOfBeerList = arrayOf("Gylden", "IPA")
            val alcoholContentList = arrayOf("4.6%", "6.5%")
            val priceList = arrayOf("kr. 30", "kr. 40")

            val imageList = intArrayOf(
                R.drawable.beergyldenk,
                R.drawable.beerflowerpower
            )

            for (i in imageList.indices) {
                dataList = BeerList(
                    nameList[i],
                    typeOfBeerList[i],
                    alcoholContentList[i],
                    imageList[i],
                    priceList[i]
                )
                dataArrayList.add(dataList)
            }
            listAdapter = listAdapter(this@BeerListView, dataArrayList)
            binding.listView.adapter = listAdapter
            binding.listView.isClickable = true

            binding.listView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this@BeerListView, DetailedActivity::class.java)
                    intent.putExtra("nameOfBeer", nameList[position])
                    intent.putExtra("typeOfBeer", typeOfBeerList[position])
                    intent.putExtra("alcoholContent", alcoholContentList[position])
                    intent.putExtra("image", imageList[position])
                    intent.putExtra("price", priceList[position])
                    startActivity(intent)
                }
        }
    }


*/