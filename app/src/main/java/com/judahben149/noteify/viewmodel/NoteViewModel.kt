package com.judahben149.noteify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.judahben149.noteify.data.NoteDatabase
import com.judahben149.noteify.model.FavoriteNote
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllNotes: LiveData<List<Note>>
    val readAllFavoriteNote: LiveData<List<Note>>
    private val repository: NoteRepository

//    val isNoteFavorite: Boolean = false

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllNotes = repository.readAllNotes
        readAllFavoriteNote = repository.readAllFavoriteNotes
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

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

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
    }

//    fun countDownDeleteNote() {
//        viewModelScope.launch(Dispatchers.IO) {
//            Thread.sleep(5000)
//        }
//    }

    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return  repository.searchDatabase(searchQuery)
    }

    //methods for favorite notes

//    fun addFavoriteNote(note: FavoriteNote) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addFavoriteNote(note)
//        }
//    }
//
//    fun updateFavoriteNote(note: FavoriteNote) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateFavoriteNote(note)
//        }
//    }
//
//    fun deleteFavoriteNote(note: FavoriteNote) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteFavoriteNote(note)
//        }
//    }
}