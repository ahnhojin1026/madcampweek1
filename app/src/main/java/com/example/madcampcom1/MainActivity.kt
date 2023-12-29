package com.example.madcampcom1

import android.Manifest
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.madcampcom1.compose.TabView
import com.example.madcampcom1.ui.theme.MadcampCom1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
        setContent {
            MadcampCom1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    TabView()
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        when (it) {
            true -> getContact()
            false -> finish()
        }
    }

    private fun checkPermission() {
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
    }

    companion object {
        private val PROJECTION: Array<out String> = arrayOf(
            Phone.CONTACT_ID,
            Phone.DISPLAY_NAME,
            Phone.NUMBER
        )
    }

    private fun getContact() {
        val cursor = contentResolver.query(
            Phone.CONTENT_URI,
            PROJECTION,
            null,
            null,
            "${Phone.DISPLAY_NAME} ASC"
        )

        while (cursor?.moveToNext() == true) {
            fun getString(columnNameString: String): String {
                val idx = cursor.getColumnIndex(columnNameString)
                return cursor.getString(idx)
            }

            PROJECTION.forEach {
                Log.d("", getString(it))
            }
        }

        cursor?.close()
    }
}