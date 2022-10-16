package com.todokanai.buildnine.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.todokanai.buildnine.room.RoomHelper
import com.todokanai.buildnine.room.RoomTrack
import com.todokanai.buildnine.room.RoomTrackDao

class TrackRepository(application: Application) {

    private val roomHelper = RoomHelper.getInstance(application)!!
    private val roomTrackDao: RoomTrackDao = roomHelper.roomTrackDao()
    private val roomTracks : List<RoomTrack> = roomTrackDao.getAll()

    fun getAll(): List<RoomTrack> {
        return roomTracks
    }

    fun insert(roomTrack:RoomTrack) {
        try {
            val thread = Thread(Runnable {
                roomTrackDao.insert(roomTrack)
            })
            thread.start()
        } catch (e: Exception) {
        }
    }

    fun delete(roomTrack:RoomTrack) {
        try {
            val thread = Thread(Runnable {
                roomTrackDao.delete(roomTrack)
            })
            thread.start()
        } catch (e: Exception) {
        }
    }




}