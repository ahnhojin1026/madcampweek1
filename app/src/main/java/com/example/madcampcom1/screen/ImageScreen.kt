package com.example.madcampcom1.screen

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.madcampcom1.component.NewImage
import com.example.madcampcom1.data.local.entity.ImageEntity
import com.example.madcampcom1.viewModel.ImageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(imageViewModel: ImageViewModel) {
//    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var isnewimage by remember { mutableStateOf(false) }
    val imageList by imageViewModel.imageList.collectAsState(initial = emptyList())
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
//                imageUris = imageUris + it // Append the new Uri to the list
                val tmpImage = ImageEntity(uri = it.toString(), info = "tmp")
                imageViewModel.addImage(tmpImage)
            }
        }
    val context = LocalContext.current
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
                        Text(text = "My Album", fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
                    },
                    actions = {
                        IconButton(onClick = {
                            /*todo*/
                            isnewimage = true
//                            launcher.launch("image/*")
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }

                    }
                )
                if(isnewimage){
                    Dialog(onDismissRequest = { isnewimage = false }) {

                        NewImage(isnewimage,imageViewModel){
                            isnewimage = false
                        }
                    }
                }
            }
        }


    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column{
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize()

                ) {
                    items(imageList) { image ->
                        var islargeimage by remember { mutableStateOf(false) }
                        Image(
                            bitmap = loadBitmapFromUri(Uri.parse(image.uri), context),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .padding(4.dp)
                                .clickable { islargeimage = !islargeimage }
                        )
                        if (islargeimage) {
                            Dialog(onDismissRequest = { islargeimage = false }) {
                                Card {
                                    Column {
                                        Row(modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween) {
                                            IconButton(onClick = {
                                                islargeimage = false
                                            }) {
                                                Icon(
                                                    Icons.Default.Close,
                                                    contentDescription = "close"
                                                )
                                            }
                                            Text(text = image.info)
                                            IconButton(onClick = {
                                                imageViewModel.deleteImage(image)
                                                islargeimage = false
                                            }) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = "delete"
                                                )
                                            }
                                        }
                                        Image(
                                            bitmap = loadBitmapFromUri(
                                                Uri.parse(image.uri),
                                                context
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxSize()
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
fun getImage(imageViewModel: ImageViewModel){

    /*todo*/
}


fun loadBitmapFromUri(uri: Uri, context: Context): ImageBitmap {
    return try {
        val contentResolver = context.contentResolver
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            // Use the deprecated method for versions prior to Android P
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } else {
            // Use ImageDecoder for Android P (API 28) and later versions
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        ImageBitmap(1, 1)
    }
}