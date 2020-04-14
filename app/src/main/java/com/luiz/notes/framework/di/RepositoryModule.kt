package com.luiz.notes.framework.di

import android.app.Application
import com.luiz.core.repository.NoteRepository
import com.luiz.notes.framework.db.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun providesRepository(application: Application) =
        NoteRepository(RoomNoteDataSource(application))
}