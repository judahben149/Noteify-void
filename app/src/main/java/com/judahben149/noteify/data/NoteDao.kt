package com.judahben149.noteify.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.model.PrivateNote

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note_table WHERE deleted_status = 0 ORDER BY id ASC")
    fun readAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("UPDATE note_table SET deleted_status = 1")
    suspend fun sendAllNotesToTrash()


    @Query("SELECT * FROM note_table WHERE note_title LIKE :searchQuery OR note_body LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE favorite_status = 1 AND deleted_status = 0")
    fun readAllFavoriteNotes(): LiveData<List<Note>>


    //methods for deleted notes
    @Query("SELECT * FROM note_table WHERE deleted_status = 1")
    fun readAllDeletedNotes(): LiveData<List<Note>>

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("UPDATE note_table SET deleted_status = 0")
    suspend fun restoreNotesFromTrash()

    @Query("DELETE FROM note_table WHERE deleted_status = 1")
    suspend fun deleteAllDeletedNotes()


    //methods for private notes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPrivateNote(note: PrivateNote)

    @Query("SELECT * FROM private_note ORDER BY id ASC")
    fun readAllPrivateNotes(): LiveData<List<PrivateNote>>

    @Delete
    suspend fun deletePrivateNote(note: PrivateNote)

    @Query("DELETE FROM private_note")
    suspend fun deleteAllPrivateNotes()



}