package com.topic2.android.notes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.topic2.android.notes.domain.model.NoteModel
import com.topic2.android.notes.viewmodel.MainViewModel
import ui.components.Note
import ui.components.TopAppBar


@Composable
fun NotesScreen(viewModel: MainViewModel){

    val notes: List<NoteModel> by viewModel
        .notesNotInTrash
        .observeAsState(listOf())

    Column {
        TopAppBar(
            title = "Заметки",
            icon = Icons.Filled.List,
            onIconClick = {}
        )
        LazyColumn{
            items(count = notes.size) { noteIndex ->
                val note = notes[noteIndex]
                Note(
                    note = note,
                    onNoteClick = { viewModel.onNoteCheckedChange(it) }
                )
            }
        }
    }

}