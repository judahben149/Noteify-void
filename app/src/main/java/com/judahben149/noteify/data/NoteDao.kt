package com.judahben149.noteify.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.judahben149.noteify.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun readAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table WHERE note_title LIKE :searchQuery OR note_body LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Note>>
}