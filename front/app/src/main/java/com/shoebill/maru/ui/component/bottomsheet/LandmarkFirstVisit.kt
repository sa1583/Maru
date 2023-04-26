package com.shoebill.maru.ui.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoebill.maru.ui.component.LottieDiamond
import com.shoebill.maru.ui.component.common.FabCamera

@OptIn(ExperimentalTextApi::class)
@Composable
@Preview
fun LandmarkFirstVisit() {
    val landmarkName = buildAnnotatedString {
        withStyle(
            SpanStyle(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF6039DF),
                        Color(0xFFA14AB7)
                    )
                ),
                fontWeight = FontWeight.Bold,
            )
        ) {
            append("서울현대박물관") // "Hello"에 색상을 적용합니다.
        }
    }
    val firstLine = buildAnnotatedString {
        withStyle(
            SpanStyle(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF6039DF),
                        Color(0xFFA14AB7)
                    )
                ),
                fontWeight = FontWeight.Bold,
            )
        ) {
            append("서울현대박물관") // "Hello"에 색상을 적용합니다.
        }
        append(" 첫 방문을 축하합니다!")
    }
    val secondLine = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.Bold,
            )
        ) {
            append("위의 보석을 터치") // "Hello"에 색상을 적용합니다.
        }
        append(" 하셔서 포인트를 획득하세요!")
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = landmarkName, fontSize = 40.sp)
        LottieDiamond()
        Column(Modifier.padding(top = 70.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = firstLine, fontSize = 16.sp)
            Text(text = secondLine, fontSize = 16.sp)
        }

        Row(Modifier.weight(1f, false)) {
            FabCamera()
        }
    }
}