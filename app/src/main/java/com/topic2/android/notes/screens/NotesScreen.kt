package com.topic2.android.notes.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.topic2.android.notes.domain.model.NoteModel
import com.topic2.android.notes.*
import com.topic2.android.notes.viewmodel.MainViewModel
import androidx.compose.runtime.getValue
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
        NotesList(
            notes = notes,
            onNoteCheckedChange = {viewModel.onNoteCheckedChange(it)},
            onNoteClick = {viewModel.onNoteClick(it)})
    }

}

@Composable
private fun NotesList(
    notes: List<NoteModel>,
    onNoteCheckedChange: (NoteModel) -> Unit,
    onNoteClick:(NoteModel) -> Unit
){
    LazyColumn{
        items(count = notes.size){noteIndex ->
            val note = notes[noteIndex]
            Note(
                note = note,
                onNoteClick = onNoteClick,
                onNoteCheckedChange = onNoteCheckedChange
            )
        }
    }
}

@Preview
@Composable
private fun NotesListPreview(){
    NotesList(
        notes = listOf(
            NoteModel(1, "Note1", "Content1", null),
                    NoteModel(2, "Note2", "Content2", false),
                NoteModel(3, "Note3", "Content3", true)
        ),
        onNoteCheckedChange = {},
        onNoteClick = {})
}