package com.task.manageme

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class userinfo(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var email:String,
    var username:String,
    var password:String

)
