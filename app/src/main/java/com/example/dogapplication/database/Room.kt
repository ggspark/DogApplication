package com.example.dogapplication.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DogDao {
    @Query("select * from databasedog")
    fun getDogs(): LiveData<List<DatabaseDog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dog: DatabaseDog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dogs: List<DatabaseDog>)
}

@Database(entities = [DatabaseDog::class], version = 1)
abstract class DogsDatabase : RoomDatabase() {
    abstract val dogDao: DogDao
}

private lateinit var INSTANCE: DogsDatabase

fun getDatabase(context: Context): DogsDatabase {
    synchronized(DogsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                DogsDatabase::class.java,
                "videos"
            ).build()
        }
    }
    return INSTANCE
}
