package com.luiz.notes.framework.db

import androidx.room.*

@Dao
interface NoteDao {
    /* O onConflict faz que simplesmente, quando o objeto existir, que substitua (atualiza) o mesmo */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteEntity(noteEntity: NoteEntity)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteEntity(id: Long): NoteEntity

    @Query("SELECT * FROM note")
    suspend fun getAllNoteEntities(): List<NoteEntity>

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)
}