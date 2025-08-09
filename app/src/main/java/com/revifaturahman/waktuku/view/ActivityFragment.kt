package com.revifaturahman.waktuku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.revifaturahman.waktuku.R
import com.revifaturahman.waktuku.adapter.ActivityAdapter
import com.revifaturahman.waktuku.data.model.ActivityModel
import com.revifaturahman.waktuku.databinding.FragmentActivityBinding
import com.revifaturahman.waktuku.viewmodel.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityFragment : Fragment() {

    private var _binding: FragmentActivityBinding ?= null
    private val binding get() = _binding!!

    private lateinit var viewModel: ActivityViewModel
    private lateinit var adapter: ActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
        adapter = ActivityAdapter(mutableListOf(), object : ActivityAdapter.OnItemActionListener {
            override fun onMoveUp(position: Int) {
                adapter.moveItem(position, position - 1)
            }

            override fun onMoveDown(position: Int) {
                adapter.moveItem(position, position + 1)
            }

            override fun onDelete(position: Int) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Data")
                    .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                    .setPositiveButton("Hapus") { _, _ ->
                        val activity = adapter.getItem(position)
                        lifecycleScope.launch {
                            viewModel.deleteActivity(activity)
                            adapter.removeItem(position)
                            Toast.makeText(requireContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        })
        binding.rvActivity.adapter = adapter
        binding.rvActivity.layoutManager = LinearLayoutManager(requireContext())

        viewModel.activityList.observe(viewLifecycleOwner){list->
            adapter.updateData(list)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}