package com.example.madcampcom1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.TabRow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.madcampcom1.ui.theme.MadcampCom1Theme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
//    private lateinit var imageAdapter: ImageAdapter
    private val REQUEST_PERMISSION_CODE = 123



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        recyclerView = findViewById(R.id.recyclerView)
        if (isPermissionGranted()) {
//            setupRecyclerView
            setContent {
//                openGalleryForResult()
            }
        } else {
            requestStoragePermission()
        }
        setContent {
            MadcampCom1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    tabView()
                }
            }
        }
    }


    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_PERMISSION_CODE
        )
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

}

val pages = listOf("연락처", "이미지", "?")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun tabView() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        val pagerState = com.google.accompanist.pager.rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    imageUris = imageUris + it // Append the new Uri to the list
                }
            }
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    content = {
                        Text(
                            text = title,
                            modifier = Modifier.padding(24.dp)
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    selectedContentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { page ->
            if (page == 1) {

//                imagepager(onClick = { Log.d("Filled button", "Filled button clicked.") })
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),

                        ) {
                        itemsIndexed(imageUris) { index, uri ->
                            Image(
                                bitmap = loadBitmapFromUri(uri, context),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = {
                    launcher.launch("image/*")
                }
                )
                {
                    Text("get image")
                }
            } else {
                Text(
                    text = page.toString(), modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
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
@Preview
@Composable
fun previewTabView() {
    tabView()
}