package test.createx.heartrateapp.presentation.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PickerViewModel @Inject constructor(
) : ViewModel() {

    private val _items =  mutableStateOf(listOf<String>())
    val items: State<List<String>> = _items

    private val _selectedItem = mutableStateOf("")
    val selectedItem: State<String> = _selectedItem

    fun updateItems(newItems: List<String>) {
        _items.value = newItems
    }

    fun updateSelectedItem(newItem: String) {
        _selectedItem.value = newItem
    }
}