package com.example.madcampcom1.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.madcampcom1.viewModel.ContactViewModel
import kotlinx.coroutines.launch

private val pages = listOf("연락처", "이미지", "?")

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(contactViewModel: ContactViewModel) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState()
        TabRow(pagerState)

        HorizontalPager(
            pageCount = pages.size,
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> ContactScreen(contactViewModel)
                1 -> ImageScreen()
                else -> Text(
                    text = page.toString(), modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabRow(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
            )
        },
    ) {
        pages.forEachIndexed { index, title ->
            Tab(
                content = {
                    Text(
                        text = title, modifier = Modifier.padding(24.dp)
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        }
    }
}