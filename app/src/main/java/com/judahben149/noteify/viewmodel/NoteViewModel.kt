package com.judahben149.noteify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.judahben149.noteify.data.NoteDatabase
import com.judahben149.noteify.model.DeletedNote
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.model.PrivateNote
import com.judahben149.noteify.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllNotes: LiveData<List<Note>>
    val readAllFavoriteNote: LiveData<List<Note>>
    val readAllDeletedNotes: LiveData<List<DeletedNote>>
    val readAllPrivateNotes: LiveData<List<PrivateNote>>
    private val repository: NoteRepository

//    val isNoteFavorite: Boolean = false

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllNotes = repository.readAllNotes
        readAllFavoriteNote = repository.readAllFavoriteNotes
        readAllDeletedNotes = repository.readAllDeletedNotes
        readAllPrivateNotes = repository.readAllPrivateNotes
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


    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return  repository.searchDatabase(searchQuery)
    }

    //methods for deleted notes
    fun addDeletedNote(note: DeletedNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDeletedNote(note)
        }
    }


    //methods for private notes
    fun addPrivateNote(note: PrivateNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPrivateNote(note)
        }
    }

    fun deletePrivateNote(note: PrivateNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePrivateNote(note)
        }
    }

}