package com.judahben149.noteify.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,
    @ColumnInfo(name = "note_title")
    var noteTitle: String,
    @ColumnInfo(name = "note_body")
    var noteBody: String,
    @ColumnInfo(name = "favorite_status")
    var favoriteStatus: Boolean = false,
    @ColumnInfo(name = "time_created")
    var timeCreated: Long,
    @ColumnInfo(name = "time_updated")
    var timeUpdated: Long
): Parcelable
