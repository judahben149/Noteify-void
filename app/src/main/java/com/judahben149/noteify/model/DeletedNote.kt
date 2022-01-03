package com.judahben149.noteify.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "deleted_note")

data class DeletedNote(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,
    @ColumnInfo(name = "deleted_note_title")
    var noteTitle: String,
    @ColumnInfo(name = "deleted_note_body")
    var noteBody: String,
    @ColumnInfo(name = "time_created")
    var timeCreated: Long = 0,
    @ColumnInfo(name = "time_updated")
    var timeUpdated: Long = 0,
    @ColumnInfo(name = "time_deleted")
    var dateDeleted: Long,
) : Parcelable

