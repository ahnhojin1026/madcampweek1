package com.example.madcampcom1

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.madcampcom1.screen.MainScreen
import com.example.madcampcom1.ui.theme.MadcampCom1Theme
import com.example.madcampcom1.viewModel.ContactViewModel
import com.example.madcampcom1.viewModel.ImageViewModel
import com.example.madcampcom1.viewModel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val contactViewModel: ContactViewModel by viewModels()
    private val memoViewModel: NoteViewModel by viewModels()
    private val imageViewModel: ImageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                !memoViewModel.isReady.value

            }
        }
        checkPermission()

        setContent {
            MadcampCom1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val memoViewModel: NoteViewModel by viewModels()
                    val imageViewModel: ImageViewModel by viewModels()
                    MainScreen(memoViewModel, imageViewModel)
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        for (entry in it) {
            when(entry.value) {
                true -> {}
                false -> finish()
            }
        }
    }

    private fun checkPermission() {
        requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE))
    }

}