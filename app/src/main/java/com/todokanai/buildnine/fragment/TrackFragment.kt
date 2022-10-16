package com.todokanai.buildnine.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.todokanai.buildnine.R
import com.todokanai.buildnine.adapter.TrackRecyclerAdapter
import com.todokanai.buildnine.databinding.FragmentTrackBinding
import com.todokanai.buildnine.room.RoomHelper
import com.todokanai.buildnine.service.ForegroundPlayService.Companion.mCurrent
import com.todokanai.buildnine.viewmodel.TrackViewModel

class TrackFragment : Fragment() {

    companion object {
        fun newInstance() = TrackFragment()
    }
    lateinit var helper : RoomHelper
    lateinit var binding: FragmentTrackBinding

    private lateinit var viewModel: TrackViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = TrackRecyclerAdapter()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_track, container, false)
        binding.trackRecyclerView.adapter = adapter
        binding.trackRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.swipe.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            binding.swipe.isRefreshing = false          //------swipe 해서 목록 새로고침
        }

        val ct : Context = requireContext()
        helper = Room.databaseBuilder(ct, RoomHelper::class.java,"room_db")
            .allowMainThreadQueries()
            .build()

        adapter.trackList.addAll(helper.roomTrackDao().getAll())
        Log.d("tested","loaded")

        adapter.onItemClick = {
            mCurrent = it.no?.toInt()!!-1
            Log.d("mtested","InForeground: $mCurrent")
        }
        return binding.root
    }

    /*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrackViewModel::class.java)
        viewModel.getAll().observe(viewLifecycleOwner, { trackList ->
            // Update UI
        })
        binding.setVariable(0,viewModel)
    }
    */
}

//   variableId????