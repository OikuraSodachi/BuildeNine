package com.todokanai.buildnine.adapter

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.todokanai.buildnine.R
import com.todokanai.buildnine.room.RoomTrack
import com.todokanai.buildnine.service.ForegroundPlayService
import java.text.SimpleDateFormat

class TrackRecyclerAdapter : RecyclerView.Adapter<TrackRecyclerAdapter.ViewHolder>() {
    var trackList = mutableListOf<RoomTrack>()
    var onItemClick: ((RoomTrack) -> Unit)? = null
    val mediaPlayer : MediaPlayer?
    get(){return ForegroundPlayService.mediaPlayer}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val roomTrack = trackList[position]
        viewHolder.setTrack(roomTrack)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {//원래는 홀더 밖에 있어야할 클래스 구겨넣음
        var trackUri: Uri? = null
        private val imageAlbum = itemView.findViewById<ImageView>(R.id.imageAlbum)
        private val textArtist = itemView.findViewById<TextView>(R.id.textArtist)
        private val textTitle = itemView.findViewById<TextView>(R.id.textTitle)
        private val textDuration = itemView.findViewById<TextView>(R.id.textDuration)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(trackList[bindingAdapterPosition])
                mediaPlayer?.reset()    // mediaPlayer 초기화
                mediaPlayer?.setDataSource(itemView.context,trackUri!!)   //mediaPlayer에 음원id(trackUri) 넣기
                mediaPlayer?.prepare()
                mediaPlayer?.start()                // 재생 개시
            }     //---------아이템뷰가 클릭되면 음원 재생
        }


        fun setTrack(roomTrack: RoomTrack) {

                imageAlbum.setImageURI(roomTrack.getAlbumUri())     //앨범이미지 투영
                textArtist.text = roomTrack.artist
                textTitle.text = "$absoluteAdapterPosition  ${roomTrack.title}"
                val duration = SimpleDateFormat("mm:ss").format(roomTrack.duration)
                textDuration.text = duration
                                            // 홀더에 내용 추가
            this.trackUri = roomTrack.getTrackUri()
        }
    }
}