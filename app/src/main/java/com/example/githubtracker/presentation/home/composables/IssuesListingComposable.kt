package com.example.githubtracker.presentation.home.composables

import UserRepositoryQuery
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
)
@Composable
fun IssuesListingComposable(
    issueName: UserRepositoryQuery.Node?,
    modifier: Modifier = Modifier
) {

    val time = issueName?.createdAt.toString()
    val localTime = ZonedDateTime.parse(time)
    val formatter =
        DateTimeFormatter.ofPattern("EE dd/MM/yyyy hh:mm:ss a")
    val convertedTime = localTime.format(formatter)
    val label = issueName?.labels?.nodes.orEmpty()
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
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Text(
                text = issueName?.title.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = modifier.height(5.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                label.forEach { label ->
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
                            Text(
                                text = name.orEmpty(),
                                modifier = modifier.padding(3.dp)
                            )
                        }
                    }
                }
            }
            Text(
                text = "Created At: $convertedTime",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
            )
        }
    }
}