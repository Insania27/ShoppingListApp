package com.example.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "shop_list_name")
data class ShoppingListItem(

    @PrimaryKey
    val id: Int? = null,

    val name: String,

    @ColumnInfo("my_time")
    val time: String,

    val allItemsCount: Int,

    val allSelectedItemsCount: Int,
)
