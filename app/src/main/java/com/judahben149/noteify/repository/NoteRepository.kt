package com.judahben149.noteify.repository

import androidx.lifecycle.LiveData
import com.judahben149.noteify.data.NoteDao
import com.judahben149.noteify.model.FavoriteNote
import com.judahben149.noteify.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val readAllNotes: LiveData<List<Note>> = noteDao.readAllNotes()
    val readAllFavoriteNotes: LiveData<List<Note>> = noteDao.readAllFavoriteNotes()

    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return noteDao.searchDatabase(searchQuery)
    }

    //methods for favorite notes

//    suspend fun addFavoriteNote(note: Note){
//        noteDao.addFavoriteNote(note)
//    }
//
//    suspend fun updateFavoriteNote(note: FavoriteNote) {
//        noteDao.updateFavoriteNote(note)
//    }
//
//    suspend fun deleteFavoriteNote(note: FavoriteNote) {
//        noteDao.deleteFavoriteNote(note)
//    }

}