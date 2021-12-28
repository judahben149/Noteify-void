package com.judahben149.noteify.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.judahben149.noteify.model.DeletedNote
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.model.PrivateNote

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

    @Query("SELECT * FROM note_table WHERE favorite_status = 1")
    fun readAllFavoriteNotes(): LiveData<List<Note>>


    //methods for deleted notes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDeletedNote(note: DeletedNote)

    @Query("SELECT * FROM deleted_note ORDER BY id ASC")
    fun readAllDeletedNotes(): LiveData<List<DeletedNote>>

    @Query("DELETE FROM deleted_note")
    suspend fun deleteAllDeletedNotes()

    //methods for private notes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPrivateNote(note: PrivateNote)

    @Query("SELECT * FROM private_note ORDER BY id ASC")
    fun readAllPrivateNotes(): LiveData<List<PrivateNote>>

    @Query("DELETE FROM private_note")
    suspend fun deleteAllPrivateNotes()

}