package com.task.manageme

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface authDAO {
    @Insert
    fun insert(userinfo: userinfo)

    @Query("SELECT email FROM USERINFO")
    fun getuseremail():String

    @Query("SELECT password FROM USERINFO")
    fun getuserpassword():String

}