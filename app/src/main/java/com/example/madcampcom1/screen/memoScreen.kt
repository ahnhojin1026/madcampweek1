package com.example.madcampcom1.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.util.UUID

data class Note(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val discription: String,
    val entryDate: LocalDate = LocalDate.now()
)
class NotesDataSource{
    fun loadNotes():List<Note>{
        return listOf(Note(title="aa", discription = "aa"),Note(title="aa", discription = "aa"),Note(title="aa", discription = "aa"))
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = "id:pixel_7", showSystemUi = true)
@Composable
fun memoScreen(){
    var isExpanded by remember { mutableStateOf(false) }
    val contextForToast = LocalContext.current.applicationContext
    Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally

    ){
    Scaffold (
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TopAppBar(
                    title = {
                        Text(text = "My notes", fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .height(25.dp)
                        .width(70.dp)
                ) {
                    Text(
                        text = "add+",
                        modifier = Modifier.fillMaxSize(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }


    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

//            expandcard()
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(30) { index ->
                    notebox(index)
                }
            }
        }
    }
    }
}

//@Composable
//fun expandcard(){
//    val expandTransition = remember {
//        expandVertically(
//            expandFrom = Alignment.Top, animationSpec = tween(300)
//        ) + fadeIn(
//            animationSpec = tween(300)
//        )
//    }
//
//    val collapseTransition = remember {
//        shrinkVertically(
//            shrinkTowards = Alignment.Top, animationSpec = tween(300)
//        ) + fadeOut(
//            animationSpec = tween(300)
//        )
//    }
//
//    AnimatedVisibility(
//        visible = isExpanded, enter = expandTransition, exit = collapseTransition
//    ) {
//        Box(
//            modifier = Modifier.padding(8.dp)
//        ) {
//            Text(
//                text = "hihi", fontSize = 16.sp, modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}

@Composable
fun notebox(index:Int){
    val contextForToast = LocalContext.current.applicationContext
    Surface() {
        Box(modifier = Modifier
            .border(
                width = 3.dp,
                color = Color.Black,
                shape = RoundedCornerShape(20.dp)
            )
            .fillMaxWidth(0.9f)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clickable {
                Toast
                    .makeText(contextForToast, "memo $index", Toast.LENGTH_SHORT)
                    .show()
            }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "your memo $index",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    var LocalDate = LocalDate.now()
                    Text(
                        text = LocalDate.toString(),
                        fontSize = 15.sp
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "this is a example text\nthis is a example text\nthis is a example text\n")
                }
            }
        }
    }
}