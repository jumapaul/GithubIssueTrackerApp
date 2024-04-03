package com.example.githubtracker.presentation.home.composables

import UserRepositoryQuery
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
)
@Composable
fun IssuesListingComposable(
    issueName: String,
    createdAt: String,
    labels: List<UserRepositoryQuery.Node1?>,
    onclick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onclick()
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Text(
                text = issueName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = modifier.height(5.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                labels.forEach { label ->
                    val name = label?.name

                    if (label?.color?.isNotEmpty() == true) {
                        val color = label.color
                        val changedColor = Color(android.graphics.Color.parseColor("#$color"))

                        Card(
                            shape = RoundedCornerShape(10.dp),
                            modifier = modifier
                                .wrapContentSize(),
                            colors = CardDefaults.cardColors(
                                containerColor = changedColor
                            )
                        ) {
                            Text(text = name.orEmpty(),
                                modifier = modifier.padding(3.dp)
                                )
                        }
                    }
                }
//                items(labels.size) { label ->
//                    val nameColor = labels.map { it!!.name to it.color }
//
//                    nameColor.forEach { (name, color) ->
//                        names.value = name
//                        colors.value = color
//
//
//                    }
//
//                }
            }
            Text(
                text = "Created At: $createdAt",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
            )
        }
    }
}