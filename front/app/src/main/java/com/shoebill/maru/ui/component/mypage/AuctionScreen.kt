package com.shoebill.maru.ui.component.mypage

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shoebill.maru.viewmodel.AuctionViewModel


@Composable
fun AuctionScreen(
    auctionViewModel: AuctionViewModel = hiltViewModel()
) {
    val tabIndex = auctionViewModel.tabIndex.observeAsState(0).value
    val tabs = listOf("참여 중인 경매", "참여 가능한 경매")

    TabRow(
        selectedTabIndex = tabIndex,
        backgroundColor = MaterialTheme.colors.background,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = Color(0xFF6039DF),
                height = 2.dp,
                modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex])
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = {
                    Text(
                        text = title,
                        color = if (tabIndex == index) Color(0xFF6039DF) else Color.Black,
                        fontSize = 15.sp
                    )
                },
                selected = tabIndex == index,
                onClick = { auctionViewModel.switchTab(index) },
            )
        }
    }

    when (tabIndex) {
        0 -> MyAuction()
        1 -> OpenAuction()
    }
}

