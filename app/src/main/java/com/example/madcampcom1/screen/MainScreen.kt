package com.example.madcampcom1.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.madcampcom1.R
import com.example.madcampcom1.viewModel.ContactViewModel
import com.example.madcampcom1.viewModel.ImageViewModel
import com.example.madcampcom1.viewModel.NoteViewModel
import kotlinx.coroutines.launch

private val pages = listOf("연락처", "이미지", "메모장")

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(noteViewModel: NoteViewModel, imageViewModel: ImageViewModel) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState()

        HorizontalPager(
            pageCount = pages.size,
            state = pagerState,
            modifier = Modifier.weight(1F)
        ) { page ->
            when (page) {
                0 -> ContactNavHost()
                1 -> ImageScreen(imageViewModel)
                2 -> memoScreen(noteViewModel)
                else -> Text(
                    text = page.toString(), modifier = Modifier.wrapContentSize()
                )
            }
        }

        Divider()

        TabRow(pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabRow(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { },
        divider = { }
    ) {
        pages.forEachIndexed { index, title ->
            val selected = pagerState.currentPage == index
            val icons = mapOf(
                true to listOf(
                    R.drawable.ic_person_filled,
                    R.drawable.ic_image_filled,
                    R.drawable.ic_sticky_note_filled
                ),
                false to listOf(
                    R.drawable.ic_person_outlined,
                    R.drawable.ic_image_outlined,
                    R.drawable.ic_sticky_note_outlined
                )
            )

            Tab(
                content = {
                    if (selected)
                        Column(
                            modifier = Modifier.padding(
                                top = 8.dp,
                                bottom = 12.dp
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(painterResource(icons[true]!![index]), "")
                            Text(text = title)
                        }
                    else
                        Icon(painterResource(icons[false]!![index]), "")
                },
                selected = selected,
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewTabRow() {
    TabRow(rememberPagerState())
}