package com.todokanai.buildnine.tool

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.room.Room
import com.todokanai.buildnine.R
import com.todokanai.buildnine.room.RoomHelper
import com.todokanai.buildnine.room.RoomTrack
import com.todokanai.buildnine.service.ForegroundPlayService
import com.todokanai.buildnine.service.ForegroundPlayService.Companion.mCurrent


class TrackTool (context: Context?){

    var helper = Room.databaseBuilder(context!!, RoomHelper::class.java,"room_db")
        .allowMainThreadQueries()
        .build()
    var context: Context? = null
    val myContext = context
    val playlist = helper.roomTrackDao().getAll()        // 전체목록 playList 확인완료

    var mediaPlayer = ForegroundPlayService.mediaPlayer
    val isPlaying : Boolean
        get(){return mediaPlayer.isPlaying}
    val isLooping : Boolean
        get(){return mediaPlayer.isLooping}

    fun scanTrackList(): List<RoomTrack> {
        // 1. 음원 정보 주소
        val listUrl = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI    // URI 값을 주면 나머지 데이터 모아옴
        // 2. 음원 정보 자료형 정의
        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION
        )
        // 3. 컨텐트리졸버의 쿼리에 주소와 컬럼을 입력하면 커서형태로 반환받는다
        val cursor = myContext?.contentResolver?.query(listUrl, proj, null, null, null)
        val trackList = mutableListOf<RoomTrack>()
        while (cursor?.moveToNext() == true) {
            val id = cursor.getString(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val albumId = cursor.getString(3)
            val duration = cursor.getLong(4)

            val track = RoomTrack(id, title, artist, albumId, duration)
            trackList.add(track)
        }
        cursor?.close()
        trackList.sortByDescending { it.title }       // 제목기준으로 정렬
        return trackList    // track 전체 반환
    }



    fun replay() {
        mediaPlayer.isLooping = !mediaPlayer.isLooping  // replay 여부 toggle
        Log.d("tested","isLooping: ${mediaPlayer.isLooping}")
    }       // 반복재생

    fun prev(){
        reset()
        mPrev()
        setTrack()
        start()
    }       // 이전곡
    fun pauseplay(){
        if(isPlaying){
            mediaPlayer.pause()
        }else{
            start()
        }
        Log.d("tester","isPlaying: $isPlaying")
    }       // 일시정지,재생
    fun next(){
        reset()
        mNext()
        setTrack()
        start()
    }       // 다음곡

    fun close() {
    }       // 종료
    fun start() {
        mediaPlayer.start()
        Log.d("tested","isLooping: ${mediaPlayer.isLooping}")
    }              // 재생개시
    fun iconset():Int {
        return when (isPlaying) {
            false -> {
                R.drawable.ic_baseline_play_arrow_24
            } else -> {
                R.drawable.ic_baseline_pause_24
            }
        }
    }  // 재생/일시정지 아이콘
    fun iconset2():Int {
        return when(isLooping){
            false ->{
                R.drawable.ic_baseline_repeat_24
            } else ->{
                R.drawable.ic_baseline_repeat_one_24
            }
        }
    }
    fun reset(){
        mediaPlayer.reset()
    }               // mediaPlayer 비워두기
    fun setTrack(){
        mediaPlayer.reset()
        mediaPlayer.setDataSource(myContext!!,playlist[mCurrent].getTrackUri())
        mediaPlayer.prepare()
    }            // 현재 위치의 곡 담기
    fun mPrev(){
        if(mCurrent == 0){
            mCurrent = playlist.size-1
        } else{
            mCurrent--
        }
    }               // 이전곡 위치로 이동
    fun mNext(){
        if(mCurrent == playlist.size-1){
            mCurrent = 0
        } else {
            mCurrent++
        }
    }               // 다음곡 위치로 이동
}
// val playing ---> mCurrent == null 일때 문제발생 가능성