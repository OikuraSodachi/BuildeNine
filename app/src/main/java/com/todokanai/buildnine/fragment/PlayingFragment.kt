package com.todokanai.buildnine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.todokanai.buildnine.R
import com.todokanai.buildnine.databinding.FragmentPlayingBinding
import com.todokanai.buildnine.viewmodel.PlayingViewModel

class PlayingFragment : Fragment() {

    companion object {
        fun newInstance() = PlayingFragment()
    }
    lateinit var binding: FragmentPlayingBinding

    private lateinit var viewModel: PlayingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_playing, container, false)
        return binding.root
    }
    /*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayingViewModel::class.java)
        viewModel.getAll().observe(viewLifecycleOwner, { trackList ->
            // Update UI
        })

        binding.setVariable(0,viewModel)
    }
     */         //기존 코드 구간
}