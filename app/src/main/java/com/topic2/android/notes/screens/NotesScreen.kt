package com.topic2.android.notes.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.topic2.android.notes.domain.model.NoteModel
import com.topic2.android.notes.viewmodel.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.topic2.android.notes.routing.Screen
import kotlinx.coroutines.launch
import ui.components.AppDrawer
import ui.components.Note
import ui.components.TopAppBar


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesScreen(viewModel: MainViewModel) {

    val notes: List<NoteModel> by viewModel
        .notesNotInTrash
        .observeAsState(listOf())

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = {
        TopAppBar(
            title = "Заметки",
            icon = Icons.Filled.List,
            onIconClick = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
    },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onCreateNewNoteClick()},
                contentColor = MaterialTheme.colors.background,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Note Button"
                    )
                }
            )
        },

        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(currentScreen = Screen.Notes, closeDrawerAction = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
            }
            )
        },
        content = {
            if (notes.isNotEmpty()) {
                NotesList(
                    notes = notes,
                    onNoteCheckedChange = { viewModel.onNoteCheckedChange(it) },
                    onNoteClick = { viewModel.onNoteClick(it) }
                )
            }
        }
    )
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