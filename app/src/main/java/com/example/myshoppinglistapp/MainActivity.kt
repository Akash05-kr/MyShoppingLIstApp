package com.example.myshoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.activity.enableEdgeToEdge
import com.example.myshoppinglistapp.ui.theme.MyShoppingLIstAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyShoppingLIstAppTheme {
                ShoppingListApp()
                }
            }
        }
    }



