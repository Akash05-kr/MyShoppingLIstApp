@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myshoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.contentValuesOf

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    val isEditing: Boolean = false
)


@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf( false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement =  Arrangement.Center) {
        Button(onClick = {showDialog = true},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 48.dp)) {
            Text( "Add Item")

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp))
         {
            items(sItems){
                item ->
                if(item.isEditing){
                    ShoppingItemEditor(item= item, onEditComplete= {
                        editedName, editedQuntity ->
                        sItems = sItems.map{it.copy(isEditing = false)}
                        val editItem = sItems.find { it.id == item.id }
                        editedItem?.let {
                            it.name = editedName
                            it.quantity = editedQuntity
                        }
                    })
                }else{
                    ShoppingListItem(item = item, onEditClick = {
                        sItems = sItems.map { {it.copy(isEditing = it.id =item.id)} }
                    },
                        onDeleteClick = {
                            sItems = sItems-item
                        })


                }

            }

        }

    }
    if (showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = SpaceBetween
                ) {
                    Button(onClick = {
                        if(itemName.isNotBlank()){
                            val newItem = ShoppingItem(
                                id = sItems.size +1,
                                name = itemName,
                                quantity = itemQuantity.toInt()
                            )
                            sItems = sItems + newItem
                            showDialog = false
                            itemName = ""
                            itemQuantity = ""

                        }
                    }) {
                        Text( "Add")
                    }
                    Button(onClick = { itemName = ""
                        itemQuantity = ""
                        showDialog = false}) {
                        Text( "Cancel")
                    }
                }
            },
            title = {Text("Add Shopping Item")},
            text = {
                Column {
                    OutlinedTextField(
                        placeholder = {Text("Item name")},
                        value = itemName,
                        onValueChange = { itemName = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    OutlinedTextField(
                        placeholder = {Text("Item quantity")},
                        value = itemQuantity,
                        onValueChange = { itemQuantity = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    )
                }
            }
            )

    }
    
}


@Composable
fun ShoppingItemEditor(modifier: Modifier = Modifier,
                       item: ShoppingItem,
                       onEditComplete: (String, Int) -> Unit) {
            var editName by remember { mutableStateOf(item.name) }
            var editQuantity by  remember { mutableStateOf(item.quantity.toString()) }
            var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Column {
            BasicTextField(
                value = editName,
                onValueChange = {editName = it},
                singleLine =  true,
                modifier = Modifier.wrapContentSize().padding(8.dp)
            )
            BasicTextField(
                value = editQuantity,
                onValueChange = {editQuantity = it},
                singleLine =  true,
                modifier = Modifier.wrapContentSize().padding(8.dp)
            )
        }
        Button(
            onClick = {
                isEditing = false
                onEditComplete(editName, editQuantity.toIntOrNull() ?: 1)

        }) {
            Text("Save")
        }
    }
}



@Composable
fun ShoppingListItem(
                     item: ShoppingItem,
                     onEditClick: ()-> Unit,
                     onDeleteClick: () -> Unit,
                     ) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(20)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Text(text = "Qty: ${item.quantity}", modifier = Modifier.padding(8.dp))
        Row(modifier = Modifier.padding(8.dp)){
            IconButton(onClick = onEditClick){
                Icon(painter = painterResource(id = R.drawable.ic_edit), contentDescription = "Edit")
            }

            IconButton(onClick = onDeleteClick){
                Icon(painter = painterResource(id = R.drawable.bin), contentDescription = "Delete")
            }

        }

    }
    
}

