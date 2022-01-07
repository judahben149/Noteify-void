package com.judahben149.noteify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.judahben149.noteify.data.NoteDatabase
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeletedNoteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllDeletedNotes: LiveData<List<Note>>
    private val repository: NoteRepository


    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllDeletedNotes = repository.readAllDeletedNotes
    }

    //methods for deleted notes
    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun deleteAllDeletedNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllDeletedNotes()
        }
    }

//    fun deleteAllNotes() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteAllNotes()
//        }
//    }

    fun restoreNotesFromTrash() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.restoreNotesFromTrash()
        }
    }
}