package com.judahben149.noteify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.judahben149.noteify.data.NoteDatabase
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.model.PrivateNote
import com.judahben149.noteify.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PrivateNoteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllPrivateNotes: LiveData<List<PrivateNote>>
    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllPrivateNotes = repository.readAllPrivateNotes
    }

    //methods for private notes
    fun addPrivateNote(note: PrivateNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPrivateNote(note)
        }
    }

    fun removeNoteFromPrivate(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun deletePrivateNote(note: PrivateNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePrivateNote(note)
        }
    }

    fun deleteAllPrivateNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllPrivateNotes()
        }
    }
}