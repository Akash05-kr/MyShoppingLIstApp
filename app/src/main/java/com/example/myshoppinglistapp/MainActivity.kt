package com.example.myshoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.myshoppinglistapp.ui.theme.MyShoppingLIstAppTheme
import kotlinx.coroutines.flow.callbackFlow
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyShoppingLIstAppTheme {
                //ShoppingListApp()
                Navigation()
                }
            }
        }
    }

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

   NavHost(navController, startDestination= "shoppinglistscreen"){
    composable("shoppinglistscreen"){
        ShoppingListApp(
            locationUtils = locationUtils,
            viewModel = viewModel,
            navController = navController,
            context = context,
            address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
        )
    }

       dialog("locationscreen"){
           backstack ->
           viewModel.location.value?.let { it1 ->
               LocationSelectionScreen(location = it1, onLocationSelected ={
                   viewModel.fetchAddress("${it.latitude},${it.longitude}")
                   navController.popBackStack()
               } )
           }
       }
   }

}

