package com.example.waktuku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.waktuku.R
import com.example.waktuku.databinding.FragmentActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityFragment : Fragment() {

    private var _binding: FragmentActivityBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}