package com.todokanai.buildnine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.todokanai.buildnine.repository.TrackRepository
import com.todokanai.buildnine.room.RoomTrack

class PlayingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TrackRepository(application)
    private val trackList = repository.getAll()

    fun getAll(): List<RoomTrack> {
        return this.trackList
    }

    fun insert(roomTrack: RoomTrack) {
        repository.insert(roomTrack)
    }

    fun delete(roomTrack: RoomTrack) {
        repository.delete(roomTrack)
    }
}