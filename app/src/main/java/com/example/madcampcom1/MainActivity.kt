package com.example.madcampcom1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.madcampcom1.ui.theme.MadcampCom1Theme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}

val pages = listOf("연락처", "이미지", "?")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun tabView() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        val pagerState = com.google.accompanist.pager.rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

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
            Text(
                text = page.toString(), modifier = Modifier.wrapContentSize()
            )
        }
    }
}

@Preview
@Composable
fun previewTabView() {
    tabView()
}