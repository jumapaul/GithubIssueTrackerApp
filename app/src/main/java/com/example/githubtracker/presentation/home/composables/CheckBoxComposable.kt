package com.example.githubtracker.presentation.home.composables

import androidx.compose.foundation.background
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckBoxComposable(
//    isChecked: Boolean,
//    checkedText: MutableState<String>,
) {

//    var expand = remember {
//        mutableStateOf(false)
//    }
//    Checkbox(checked = expand.value, onCheckedChange = {
//        expand.value = it
//    }, colors = CheckboxDefaults.colors(Color.White),
//        modifier = Modifier.background(color = Color.Blue)
//        )
//    Row(
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Box(
//            modifier = Modifier
//                .clip(CircleShape)
//                .size(25.dp)
//                .padding(3.dp)
//                .clip(CircleShape)
//                .background(color = Color.White),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(imageVector = Icons.Default.Check, contentDescription = null)
//        }
//
//        Checkbox(checked =, onCheckedChange =)
//    }

}

@Preview
@Composable
fun CheckBoxComposablePreview() {
    CheckBoxComposable()
}