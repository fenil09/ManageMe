package com.task.manageme

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface authDAO {
    @Insert
    fun insert(userinfo: userinfo)


}