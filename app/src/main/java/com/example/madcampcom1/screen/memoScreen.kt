package com.example.madcampcom1.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.util.UUID
import androidx.compose.ui.window.Dialog
import com.example.madcampcom1.component.MainTopBar
import com.example.madcampcom1.data.local.entity.Note
import com.example.madcampcom1.ui.theme.Background
import com.example.madcampcom1.viewModel.NoteViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun memoScreen(noteViewModel: NoteViewModel){
    var isExpandCardVisible by remember { mutableStateOf(false) }

    val contextForToast = LocalContext.current.applicationContext
    val notesListState by noteViewModel.noteList.collectAsState(initial = emptyList())

    Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally

    ){
    Scaffold (
        topBar = {
            MainTopBar("My notes", null) {
                IconButton(onClick = {
                    isExpandCardVisible = !isExpandCardVisible
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
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
            AnimatedVisibility(
                visible = isExpandCardVisible,
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
                expandcard(noteViewModel)
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if(!notesListState.isEmpty()){
                    items(notesListState){note->
                        notebox(index = note,noteViewModel)
                    }
                }
            }
        }
    }
    }
}
@Composable
fun emptyote(){
    Surface{ Text(text = "no notes\n press '+' to make new note") }
}
@Composable
fun expandcard(noteViewModel: NoteViewModel){
    Surface {
        Box(modifier = Modifier
            .fillMaxWidth()){
            Column (modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
                ){
                var title: String by remember { mutableStateOf("") }
                NoteInputText(modifier = Modifier.fillMaxWidth(),text = title, label = "Title",maxLine = 1, onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace() || char.isDigit()
                        }) title = it
                })
                var discription by remember { mutableStateOf("") }
                NoteInputText(modifier = Modifier.fillMaxWidth(),text = discription, label = "add new note",maxLine = 10, onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace() || char.isDigit()
                        }) discription = it
                })
                NoteButton(text = "Save", onClick = {
                    val note = Note(title = title,discription = discription)
                    noteViewModel.addNote(note)
                    title = ""
                    discription = ""

                })
            }
        }

    }
}
@Composable
fun NoteButton(modifier: Modifier = Modifier,text: String, onClick: () -> Unit, enabled: Boolean = true){

        Button(onClick = onClick,
            shape = CircleShape,
            enabled = enabled,
            modifier = modifier
        ){
            Text(text = text)
        }

}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteInputText(modifier: Modifier,
                  text: String,
                  label: String,
                  maxLine: Int,
                  onTextChange: (String) -> Unit,
                  onImeAction: () -> Unit = {}) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface {
        TextField(
            value = text,
            onValueChange = {
                            if(text == "My notes"){
                                onTextChange(it)
                            }
                else{
                    onTextChange(it)
                            }
            },
            colors = TextFieldDefaults.textFieldColors(),
            maxLines = maxLine,
            label = { Text(text = label)},
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onImeAction()
                keyboardController?.hide()
            }),
            modifier = modifier

        )
    }

}

@Composable
fun notebox(index:Note,noteViewModel: NoteViewModel){
    val contextForToast = LocalContext.current.applicationContext
    var showDialog by remember { mutableStateOf(false) }
        Box(modifier = Modifier
            .border(
                width = 3.dp,
                color = Color.Black,
                shape = RoundedCornerShape(20.dp)
            )
            .fillMaxWidth(0.9f)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clickable {
                showDialog = true
            }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if(showDialog){
                    Dialog(onDismissRequest = { showDialog = false }) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(Background),
                            shape = RoundedCornerShape(20.dp),
                        )  {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Top
                            ) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .height(24.dp)){
                                    IconButton(onClick = {
                                        showDialog = false
                                    }) {
                                        Icon(Icons.Default.Close, contentDescription = "Close")
                                    }
                                }
                                Text(
                                    text = index.title,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = index.discription,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                                Row(modifier = Modifier
                                    .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                    ){
                                    Button(onClick = {
                                        noteViewModel.deleteNote(index)
                                        showDialog = false
                                    }) {
                                        Text(text = "remove")

                                    }
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = index.title,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    var LocalDate = index.entryDate
                    Text(
                        text = LocalDate,
                        fontSize = 15.sp
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = index.discription,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                }
            }
        }

}
@Composable
fun memoinfo(modifier: Modifier,index:Note){
    Column {
        Text(text = "Title: ${index.title}", fontSize = 20.sp)
        Text(text = "Description: ${index.discription}", fontSize = 16.sp)
        // Any other UI elements you want to display for a single note
    }
}
suspend fun <Note> Flow<List<Note>>.flattenToList() =
    flatMapConcat { it.asFlow() }.toList()