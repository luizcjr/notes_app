package com.luiz.notes.framework.usecase

import com.luiz.core.usecase.*

data class UseCases(
    val addNote: AddNote,
    val getNote: GetNote,
    val getAllNotes: GetAllNotes,
    val removeNote: RemoveNote,
    val getWordCount: GetWordCount
)