package com.todoApp.todojetpackcompose.di

import com.todoApp.todojetpackcompose.repository.TodoRepository
import com.todoApp.todojetpackcompose.ui.domain.repository.ITodoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideTodoRepository(
        todoRepository:TodoRepository
    ):ITodoRepository

}