package com.judahben149.noteify.repository

import androidx.lifecycle.LiveData
import com.judahben149.noteify.data.NoteDao
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.model.PrivateNote

class NoteRepository(private val noteDao: NoteDao) {

    val readAllNotes: LiveData<List<Note>> = noteDao.readAllNotes()
    val readAllFavoriteNotes: LiveData<List<Note>> = noteDao.readAllFavoriteNotes()
    val readAllDeletedNotes: LiveData<List<Note>> = noteDao.readAllDeletedNotes()
    val readAllPrivateNotes: LiveData<List<PrivateNote>> = noteDao.readAllPrivateNotes()


    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun sendAllNotesToTrash() {
        noteDao.sendAllNotesToTrash()
    }


    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return noteDao.searchDatabase(searchQuery)
    }

    //methods for deleted notes
    suspend fun deleteAllDeletedNotes() {
        noteDao.deleteAllDeletedNotes()
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    suspend fun restoreNotesFromTrash() {
        noteDao.restoreNotesFromTrash()
    }


    //methods for private note
    suspend fun addPrivateNote(note: PrivateNote) {
        noteDao.addPrivateNote(note)
    }

    suspend fun deletePrivateNote(note: PrivateNote) {
        noteDao.deletePrivateNote(note)
    }

    suspend fun deleteAllPrivateNotes() {
        noteDao.deleteAllPrivateNotes()
    }
}