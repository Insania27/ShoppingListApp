package com.example.shoppinglist.note_list_screen

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist.add_item_screen.AddItemEvent
import com.example.shoppinglist.data.NoteItem
import com.example.shoppinglist.data.NoteRepository
import com.example.shoppinglist.datastore.DataStoreManager
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository,
    dataStoreManager: DataStoreManager
): ViewModel(), DialogController {

    val noteListFlow = repository.getAllItems()
    private var noteItem: NoteItem? = null

    var noteList by mutableStateOf(listOf<NoteItem>())
    var originNoteList = listOf<NoteItem>()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var titleColor = mutableStateOf("#003FFF")

    var searchText by mutableStateOf("")
        private set

    override var dialogTitle = mutableStateOf("Delete this note?")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            dataStoreManager.getStringPreference(
                DataStoreManager.TITLE_COLOR,
                "#003FFF"
            ).collect{ color ->
                titleColor.value = color
            }
        }

        viewModelScope.launch {
            noteListFlow.collect{ list ->
                noteList = list
                originNoteList = list
            }
        }
    }

    fun onEvent(event: NoteListEvent){
        when(event){
            is NoteListEvent.OnShowDeleteDialog -> {
                openDialog.value = true
                noteItem = event.item
            }
            is NoteListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }
            is NoteListEvent.UndoneDeleteItem -> {
                viewModelScope.launch {
                    repository.insertItem(noteItem!!)
                }
            }
            is NoteListEvent.OnTextSearchChange -> {
                searchText = event.text
                noteList = originNoteList.filter { note ->
                    note.title.lowercase().startsWith(searchText.lowercase())
                }
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }
            is DialogEvent.OnConfirm -> {
                viewModelScope.launch {
                    repository.deleteItem(noteItem!!)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        "Undone delete item?"
                    ))
                }
                openDialog.value = false
            }
            else -> {  }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}