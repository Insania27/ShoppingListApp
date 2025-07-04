package com.example.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_table")
data class TestItem(

    @PrimaryKey
    val id: Int? = null,

    val title: String,

    val description: String,

    val time: String,

    // Для миграции нужно указать значение
    // по умолчанию в аннотации
    @ColumnInfo(defaultValue = "0")
    val testCounter: Int = 0
)