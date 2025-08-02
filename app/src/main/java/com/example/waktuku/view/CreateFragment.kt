package com.example.waktuku.view

import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.waktuku.data.model.ActivityModel
import com.example.waktuku.databinding.FragmentCreateBinding
import com.example.waktuku.utils.AlarmHelper
import com.example.waktuku.viewmodel.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

@AndroidEntryPoint
class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ActivityViewModel by viewModels()

    private var selectedActivity: ActivityModel ?= null
    private var selectedHour = -1
    private var selectedMinute = -1
    private var audioPath: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false
    private val RECORD_AUDIO_REQUEST_CODE = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TimePicker
        binding.btnPickTime.setOnClickListener {
            val now = Calendar.getInstance()
            val dialog = TimePickerDialog(
                requireContext(),
                { _: TimePicker, hour: Int, minute: Int ->
                    selectedHour = hour
                    selectedMinute = minute
                    binding.etTime.setText(String.format("%02d:%02d", hour, minute))
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
            )
            dialog.show()
        }

        // Rekam suara
        binding.btnRecordVoice.setOnClickListener {
            if (!checkAudioPermission()) return@setOnClickListener
            if (!isRecording) startRecording() else stopRecording()
        }

        // Simpan data
        binding.btnSaveActivity.setOnClickListener {
            val activityName = binding.etActivityName.text.toString()

            if (activityName.isBlank() || selectedHour == -1 || audioPath == null) {
                Toast.makeText(requireContext(), "Isi semua data dengan lengkap", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val time = String.format("%02d:%02d", selectedHour, selectedMinute)

            lifecycleScope.launch {
                if (selectedActivity == null) {
                    // INSERT
                    val activityNew = ActivityModel(
                        activity = activityName,
                        time = time,
                        audio = audioPath!!
                    )
                    val newId = viewModel.insertActivityAndReturnId(activityNew)
                    AlarmHelper.setAlarm(requireContext(), newId.toInt(), activityName, selectedHour, selectedMinute, audioPath)
                    Toast.makeText(requireContext(), "Aktivitas ditambahkan", Toast.LENGTH_SHORT).show()
                } else {
                    // UPDATE
                    val updatedActivity = selectedActivity!!.copy(
                        activity = activityName,
                        time = time,
                        audio = audioPath!!
                    )
                    viewModel.updateActivity(updatedActivity)
                    AlarmHelper.setAlarm(requireContext(), updatedActivity.id, activityName, selectedHour, selectedMinute, audioPath)
                    Toast.makeText(requireContext(), "Aktivitas diperbarui", Toast.LENGTH_SHORT).show()
                }

                // Reset form setelah simpan
                binding.etActivityName.text?.clear()
                binding.etTime.setText("")
                binding.tvRecordingStatus.text = "Belum ada rekaman"
                audioPath = null
                selectedHour = -1
                selectedMinute = -1
                selectedActivity = null
            }


        }

    }

    private fun checkAudioPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_REQUEST_CODE
            )
            return false
        }
        return true
    }

    private fun startRecording() {
        val outputDir = requireContext().filesDir
        val file = File(outputDir, "voice_${System.currentTimeMillis()}.3gp")
        audioPath = file.absolutePath

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(audioPath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            prepare()
            start()
        }

        isRecording = true
        binding.tvRecordingStatus.text = "Merekam suara..."
        binding.btnRecordVoice.text = "Berhenti Rekam"
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        isRecording = false
        binding.tvRecordingStatus.text = "Rekaman disimpan"
        binding.btnRecordVoice.text = "Rekam Suara"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
