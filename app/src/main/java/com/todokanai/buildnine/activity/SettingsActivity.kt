package com.todokanai.buildnine.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import com.todokanai.buildnine.R
import com.todokanai.buildnine.room.RoomHelper
import com.todokanai.buildnine.tool.TrackTool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {



    lateinit var helper: RoomHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val scanBtn = findViewById<Button>(R.id.Scanbtn)
        val backBtn = findViewById<ImageButton>(R.id.Backbtn)

        scanBtn.setOnClickListener{scan()}          // scan버튼 난타하면 리스트 사이즈 증가 issue. 작업 완료시까지 버튼 disable로 해결?

        val intentmain = Intent(this,MainActivity::class.java)
        backBtn.setOnClickListener {startActivity(intentmain);Log.d("tested111","back")} //Backbtn에 대한 동작


    }

    fun scan() {
        TrackTool(this).reset()      // 혹시 몰라서 정지명령 내려둠
        val scannedList = TrackTool(applicationContext).scanTrackList()
        lifecycleScope.launch(Dispatchers.IO) {
            helper = RoomHelper.getInstance(applicationContext)!!

            val size = scannedList.size
            helper.roomTrackDao().deleteAll()                       // 목록비우기
            for (a in 1..size) {
                helper.roomTrackDao().insert(scannedList[a - 1])
            }                               // 스캔된 목록
            Log.d("tested111", "Scan")
        }
    }           // 음원파일 스캔 함수
}