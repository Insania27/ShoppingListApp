package com.example.shoppinglist.di

import android.app.Application
import androidx.room.Room
import com.example.shoppinglist.data.AddItemRepoImpl
import com.example.shoppinglist.data.AddItemRepository
import com.example.shoppinglist.data.MainDb
import com.example.shoppinglist.data.NoteRepoImpl
import com.example.shoppinglist.data.NoteRepository
import com.example.shoppinglist.data.ShoppingListRepoImpl
import com.example.shoppinglist.data.ShoppingListRepository
import com.example.shoppinglist.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideMainDb(app: Application): MainDb{
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "shop_list_db"
        ).createFromAsset("shop_list_db.db").build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideShopRepo(db: MainDb): ShoppingListRepository{
        return ShoppingListRepoImpl(db.shoppingListDao)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideAddItemRepo(db: MainDb): AddItemRepository {
        return AddItemRepoImpl(db.addItemDao)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideNoteRepo(db: MainDb): NoteRepository {
        return NoteRepoImpl(db.noteDao)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideDataStoreManager(app: Application): DataStoreManager{
        return DataStoreManager(app)
    }

}