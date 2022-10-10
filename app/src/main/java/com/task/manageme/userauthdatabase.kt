package com.task.manageme

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [userinfo::class], version = 1)
abstract class userauthdatabase:RoomDatabase(){

    abstract fun getauthuser():authDAO
}