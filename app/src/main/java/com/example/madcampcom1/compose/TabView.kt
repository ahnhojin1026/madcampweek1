package com.example.madcampcom1.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

private val pages = listOf("연락처", "이미지", "?")

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun TabView() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        val pagerState = rememberPagerState()
        TabRow(pagerState)

        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> ContactView()
                else -> Text(
                    text = page.toString(), modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabRow(pagerState: PagerState) {
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
            Tab(content = {
                Text(
                    text = title, modifier = Modifier.padding(24.dp)
                )
            }, selected = pagerState.currentPage == index, onClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(index)
                }
            }, selectedContentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}