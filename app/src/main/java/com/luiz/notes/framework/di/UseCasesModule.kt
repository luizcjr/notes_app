package com.luiz.notes.framework.di

import com.luiz.core.repository.NoteRepository
import com.luiz.core.usecase.*
import com.luiz.notes.framework.usecase.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun providesUseCase(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetNote(repository),
        GetAllNotes(repository),
        RemoveNote(repository),
        GetWordCount()
    )
}