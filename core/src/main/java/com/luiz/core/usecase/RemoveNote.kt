package com.luiz.core.usecase

import com.luiz.core.data.Note
import com.luiz.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)
}