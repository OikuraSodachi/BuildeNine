package com.todokanai.buildnine.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RoomPlayerDao {

    @Query("select mCurrent[0] from room_player")
    fun mCurrent(): Int

    @Insert(onConflict = REPLACE)
    fun insert(roomPlayer: RoomPlayer)

    @Query("Delete from room_player")
    fun deleteAll()
}