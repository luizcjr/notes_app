package com.luiz.core.usecase

import com.luiz.core.repository.NoteRepository

class GetAllNotes(private val noteRepository: NoteRepository) {
    suspend operator fun invoke() = noteRepository.getNotes()
}