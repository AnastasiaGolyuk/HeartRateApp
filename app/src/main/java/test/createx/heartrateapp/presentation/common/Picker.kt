package test.createx.heartrateapp.presentation.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Picker(
    values: List<String>,
    startIndex: Int = 0,
    visibleItemsCount: Int = 5,
) {
    val pickerViewModel: PickerViewModel = hiltViewModel()

    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = values.size
    val listStartIndex = 0

    fun getItem(index: Int) = values[index]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val selectedItem = pickerViewModel.selectedItem

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged().collect { item -> pickerViewModel.updateSelectedItem(item) }
    }

    LazyColumn(
        state = listState,
        flingBehavior = flingBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(118.dp)
    ) {
        items(listScrollCount) { index ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = if (getItem(index) == selectedItem.value) Modifier
                    .background(
                        color = RedBg, shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) else Modifier
                    .background(
                        color = White
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = getItem(index),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = getTextStyle(
                        index = index, items = values, selectedItem = selectedItem.value
                    ),
                    color = if (getItem(index) == selectedItem.value) BlackMain else GreySubText
                )
            }
        }
    }
}

@Composable
private fun getTextStyle(index: Int, items: List<String>, selectedItem: String): TextStyle {

    return when {
        items[index] == selectedItem -> {
            MaterialTheme.typography.labelLarge
        }

        (index == items.size - 1 && items[0] == selectedItem) || (index == 0 && items[items.size - 1] == selectedItem) -> {
            MaterialTheme.typography.labelMedium
        }

        (index != items.size - 1 && items[index + 1] == selectedItem) || (index != 0 && items[index - 1] == selectedItem) -> {
            MaterialTheme.typography.labelMedium
        }

        else -> {
            MaterialTheme.typography.bodySmall
        }
    }
}

@Preview
@Composable
fun PickerPreview() {
    HeartRateAppTheme {
        val values = remember { (1..99).map { it.toString() } }
        Picker(
            values = values,
            visibleItemsCount = 5,
        )
    }
}
