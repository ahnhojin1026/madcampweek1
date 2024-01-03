package com.example.madcampcom1.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.madcampcom1.data.local.entity.ImageEntity
import com.example.madcampcom1.screen.loadBitmapFromUri
import com.example.madcampcom1.ui.theme.Background
import com.example.madcampcom1.viewModel.ImageViewModel


@Composable
fun NewImage(isnewimage:Boolean, imageViewModel: ImageViewModel, onClose:() -> Unit){
    var tmpuri by remember { mutableStateOf<Uri?>(null) }
    var tmpinfo by remember { mutableStateOf<String>("") }

    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                tmpuri= it

//                val tmpImage = ImageEntity(uri = it.toString(), info = "tmp")
//                imageViewModel.addImage(tmpImage)
            }
        }

    if(isnewimage) {
        Surface{
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(Background),
                shape = RoundedCornerShape(20.dp),
            ) {
                Column (modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Row (modifier = Modifier.fillMaxWidth()){
                        IconButton(onClick = onClose) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize(0.7f)
                    ){
                        tmpuri?.let { uri ->
                            Image(
                                bitmap = loadBitmapFromUri(uri, context),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    }
                    var textValue by remember { mutableStateOf("") } // State to hold the TextField value

                    TextField(
                        value = textValue,
                        onValueChange = { textValue = it },
                        label = { Text("Enter image info") }, // Label for the TextField
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,

                    ){
                        Button(onClick = {
                            launcher.launch("image/*")
                        }
                        )
                        {
                            Text("load image")
                        }
                        Button(onClick = {
                            val tmpImage = ImageEntity(uri = tmpuri.toString(), info = textValue)
                            imageViewModel.addImage(tmpImage)
                            onClose()
                        }
                        )
                        {
                            Text("save image")
                        }
                    }
                }
            }
        }
    }
}
//@Preview
//@Composable
//fun NewImagepre(){
//    val isNewImage = remember { mutableStateOf(true) }
//    val imageViewModel: ImageViewModel by viewModels()
//    // Create a composable that uses the NewImage composable
//    NewImage(isnewimage = isNewImage.value,imageViewModel) {
//        isNewImage.value = false
//    }
//}