package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "add_item_test")
data class AddItem(

    @PrimaryKey
    val id: Int? = null,

    val name: String,

    val isChecked: Boolean,

    val listId: Int,
)
