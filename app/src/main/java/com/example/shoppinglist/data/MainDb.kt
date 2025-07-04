package com.example.shoppinglist.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec


@Database(
    entities = [
        ShoppingListItem::class,
        AddItem::class,
        NoteItem::class,
        TestItem::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MainDb: RoomDatabase() {

    abstract val shoppingListDao: ShoppingListDao
    abstract val noteDao: NoteDao
    abstract val addItemDao: AddItemDao

}