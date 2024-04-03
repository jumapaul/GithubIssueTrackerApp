package com.example.githubtracker.presentation.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GetLabelListing(
    title: String,
    labels: List<String>,
    modifier: Modifier = Modifier
) {

    val selectedString = remember {
        mutableStateOf(title)
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.wrapContentSize()
    ) {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = modifier.height(25.dp),
            border = BorderStroke(
                1.dp,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(2.dp)
            ) {
                Text(text = selectedString.value)

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
        }


        if (expanded) {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = 10.dp
            ) {
                LazyColumn(
                    modifier = modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(labels) {
                        Text(text = it, modifier.clickable {
                            selectedString.value = it
                        })
                    }
                }
            }

        }

    }

}