package com.luiz.notes.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luiz.core.data.Note

@Entity(tableName = "note")
class NoteEntity(
    var title: String,
    var content: String,
    @ColumnInfo(name = "creation_date")
    var creationTime: Long,
    @ColumnInfo(name = "update_time")
    var updatedTime: Long,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) {
    companion object {
        fun fromNote(note: Note) =
            NoteEntity(note.title, note.content, note.creationTime, note.updatedTime, note.id)
    }

    fun toNote() = Note(title, content, creationTime, updatedTime, id)
}