package com.example.shoppinglist.data


import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllItems(): Flow<List<NoteItem>>

    suspend fun insertItem(item: NoteItem)

    suspend fun deleteItem(item: NoteItem)

    suspend fun getNoteItemById(id: Int): NoteItem

}