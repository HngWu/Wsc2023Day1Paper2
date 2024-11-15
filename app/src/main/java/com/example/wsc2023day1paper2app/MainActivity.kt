package com.example.wsc2023day1paper2app

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wsc2023day1paper2app.ui.theme.Wsc2023Day1Paper2AppTheme
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import java.util.Timer
import java.util.TimerTask
import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.TextField
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.wsc2023day1paper2app.Product
import com.example.wsc2023day1paper2app.api.GetProducts
import com.example.wsc2023day1paper2app.models.ProductItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val colorScheme = lightColorScheme(
            primary = Color(0xFFEF3840),
            onPrimary = Color.White,
            // Add other color customizations if needed
        )



        setContent {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = androidx.compose.material3.Typography(),
                content = {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "home") {
                        composable("home") { HomeScreen(navController, this@MainActivity, colorScheme) }
                        composable("reading_light") { ReadingLightScreen(this@MainActivity) }
                        composable("call_assistance") { CallAssistanceScreen(this@MainActivity,colorScheme) }
                        composable("shopping") { ShoppingScreen(this@MainActivity) }
                    }


                }
            )
        }
    }
}


@Composable
fun HomeScreen(navController: NavController, context: Context, materialColorTheme : ColorScheme) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Home", "Reading Light", "Call Assistance", "Shopping")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .height(90.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row (
                verticalAlignment = Alignment.CenterVertically

            ){
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(100.dp)
                )

                Text(
                    text = "World Skills Airlines",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        when (selectedTabIndex) {
            0 -> {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 50.dp, 16.dp, 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { selectedTabIndex = 0 },
                        modifier = Modifier.width(200.dp).height(60.dp).padding(0.dp, 0.dp, 0.dp, 10.dp)
                    ) {
                        Text("Home")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { selectedTabIndex = 1 },
                        modifier = Modifier.width(200.dp).height(60.dp).padding(0.dp, 0.dp, 0.dp, 10.dp)
                    ) {
                        Text("Reading Light")
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { selectedTabIndex = 2 },
                        modifier = Modifier.width(200.dp).height(60.dp).padding(0.dp, 0.dp, 0.dp, 10.dp)
                    ) {
                        Text("Call Assistance")
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { selectedTabIndex = 3 },
                        modifier = Modifier.width(200.dp).height(60.dp).padding(0.dp, 0.dp, 0.dp, 10.dp)
                    ) {
                        Text("Shopping")
                    }
                }

            }
            1 -> ReadingLightScreen( context = LocalContext.current)
            2 -> CallAssistanceScreen(context = LocalContext.current, materialColorTheme = materialColorTheme)
            3 -> ShoppingScreen(context = LocalContext.current)
        }

        Spacer(modifier = Modifier.weight(1f))

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
    }
}

fun saveLightState(context: Context, isLightOn: Boolean) {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putBoolean("isLightOn", isLightOn)
        apply()
    }
}

fun getLightState(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isLightOn", false)
}

@Composable
fun ReadingLightScreen(context: Context) {
    var isLightOn by remember { mutableStateOf(getLightState(context)) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Button(onClick = {
            isLightOn = !isLightOn
            saveLightState(context, isLightOn)
        },
                modifier = Modifier.width(150.dp).height(70.dp).testTag("readingLightOnOffButton")
            // Adjust the size as needed
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = if (isLightOn) R.drawable.ic_action_on else R.drawable.ic_action_off),
                    contentDescription = "Reading Light"
                )
                Text(if (isLightOn) "On" else "Off")
            }

        }
    }
}

@Composable
fun CallAssistanceScreen(context: Context, materialColorTheme: ColorScheme) {
    var isBlinking by remember { mutableStateOf(false) }
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.beep) }
    val handler = Handler(Looper.getMainLooper())
    var buttonColor by remember { mutableStateOf(materialColorTheme.primary) }
    var originalColor =  materialColorTheme.primary



    LaunchedEffect(isBlinking) {
        if (isBlinking) {
            val timer = Timer()
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    handler.post {
                        mediaPlayer.start()
                        buttonColor = if (buttonColor == materialColorTheme.primary) Color.Blue else materialColorTheme.primary
                    }
                }
            }, 0, 500) // Blink every 500ms

            handler.postDelayed({
                buttonColor = materialColorTheme.primary
                timer.cancel()
                mediaPlayer.stop()
                mediaPlayer.prepare()
                isBlinking = false
            }, 5000) // Stop after 5 seconds
        }

        if (!isBlinking){
            buttonColor = originalColor
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { isBlinking = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                disabledContainerColor = buttonColor
            ),
            enabled = !isBlinking,
            modifier = Modifier
                .padding(16.dp)
                .height(60.dp)
                .width(200.dp)
                .testTag("callButton")
                .semantics { contentDescription = if (buttonColor != originalColor) "Blue button" else "Red button" }
        ) {
            Text(
                text = "Call Assistance",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ShoppingScreen(context: Context) {
    var selectedCategory by remember { mutableStateOf("mains") }
    var products by remember { mutableStateOf(listOf<ProductItem>()) }
    var cart by remember { mutableStateOf<List<ProductItem>>(emptyList()) }
    var selectedProduct by remember { mutableStateOf<ProductItem?>(null) }
    var showProductDetails by remember { mutableStateOf(false) }
    var showCheckout by remember { mutableStateOf(false) }
    var paymentStatus by remember { mutableStateOf<String?>(null) }

    var load by remember { mutableStateOf(true) }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var enableNavBar by remember { mutableStateOf(false) }


    if(load)
    {
        LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val productList = GetProducts().getFunction()
                if (productList != null) {
                    products = productList as MutableList<ProductItem>
                    enableNavBar = true
                    load = false
                }
            }
        }
    }
    // Load products from JSON

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Category Tabs
        TabRow(
            selectedTabIndex = listOf("mains", "snacks", "drinks", "sides",).indexOf(selectedCategory),
            modifier = Modifier.testTag("categoryTabs"),
            ) {
            listOf("mains", "snacks", "drinks", "sides").forEach { category ->
                Tab(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category
                                load = true
                              },
                    text = { Text(category.capitalize()) },
                    modifier = Modifier.testTag(category),
                    enabled = !load
                )
            }
        }

        // Product List
        LazyColumn(
            modifier = Modifier.fillMaxWidth().testTag("productList"),
            //verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products.filter { it.category == selectedCategory }) { product ->
                ProductItem(product, onClick = {
                    selectedProduct = product
                    showProductDetails = true
                }, modifier = Modifier.testTag(product.name).semantics { contentDescription = product.name })
            }
            item{
                Button(
                onClick = { showCheckout = true },
                enabled = cart.isNotEmpty(),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Checkout")
            }
            }
        }
        Toast.LENGTH_SHORT


        // Product Details Dialog
        if (showProductDetails && selectedProduct != null) {
            AlertDialog(
                onDismissRequest = { showProductDetails = false },
                title = { Text(selectedProduct!!.name) },
                text = {
                    Column {
                        Image(
                            bitmap = decodeBase64Image(selectedProduct!!.image),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                        )
                        Text("Price: \$${selectedProduct!!.price}", modifier = Modifier.testTag(selectedProduct!!.price.toString()))
                        Text(selectedProduct!!.description, modifier = Modifier.testTag(selectedProduct!!.description))
                        Text("Availability: ${selectedProduct!!.availability}", modifier = Modifier.testTag(selectedProduct!!.availability))
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (selectedProduct!!.availability == "in_stock")
                        {
                            cart = cart + selectedProduct!!
                            showProductDetails = false
                        }
                        else
                        {
                            Toast.makeText(context, "Product is out of stock", Toast.LENGTH_SHORT).show()
                        }



                    }) {
                        Text("Add to Cart")
                    }
                },
                dismissButton = {
                    Button(onClick = { showProductDetails = false }) {
                        Text("Close")
                    }
                }
            )
        }

        // Checkout Dialog
        if (showCheckout) {
            AlertDialog(
                onDismissRequest = { showCheckout = false },
                title = { Text("Checkout") },
                text = {
                    Column {
                        cart.forEach { product ->
                            Text("${product.name} - \$${product.price}")
                        }
                        Text("Total: \$${cart.sumOf { it.price }}")



                        TextField(
                            value = cardNumber,
                            onValueChange = { cardNumber = it },
                            label = { Text("Card Number(16-digit)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = expiryDate,
                            onValueChange = { expiryDate = it },
                            label = { Text("Expiry Date (MM/YY)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = cvv,
                            onValueChange = { cvv = it },
                            label = { Text("CVV(XXX)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = cardHolderName,
                            onValueChange = { cardHolderName = it },
                            label = { Text("Card Holder Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        // Validate input fields
                        var success = true
                        if (cardNumber.isBlank() || cardNumber.length != 16 || expiryDate.isBlank() || !expiryDate.matches(Regex("\\d{2}/\\d{2}")) || cvv.isBlank() || cvv.length != 3 || cardHolderName.isBlank()) {
                            paymentStatus = "Please fill in all fields correctly"
                            success = false
                        } else {
                            // Process payment
                            paymentStatus = if (success) "Payment Successful, Order received" else "Payment Failed"

                            if (success) {
                                // Clear cart
                                cart = emptyList()
                            }

                            showCheckout = false
                        }
                    }) {
                        Text("Pay")
                    }
                },
                dismissButton = {
                    Button(onClick = { showCheckout = false }) {
                        Text("Close")
                    }
                }
            )
        }

        // Payment Status Dialog
        paymentStatus?.let {
            AlertDialog(
                onDismissRequest = { paymentStatus = null },
                title = { Text(it) },
                confirmButton = {
                    Button(onClick = { paymentStatus = null }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun ProductItem(product: ProductItem, onClick: () -> Unit, modifier: Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
            .then(modifier)
    ) {
        Image(
            bitmap = decodeBase64Image(product.image),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(product.name, style = MaterialTheme.typography.headlineMedium)
            Text("Price: \$${product.price}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun decodeBase64Image(base64Str: String): ImageBitmap {
    val imageBytes = Base64.decode(base64Str, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size).asImageBitmap()
}

fun processPayment(): Boolean {
    // Simulate payment processing
    return true
}

fun loadProducts(): List<Product> {
    // Load products from JSON
    return listOf()
}

data class Product(val name: String, val price: Double, val category: String)

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Wsc2023Day1Paper2AppTheme {
        Greeting("Android")
    }
}