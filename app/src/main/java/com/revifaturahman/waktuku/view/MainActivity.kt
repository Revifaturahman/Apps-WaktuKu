package com.revifaturahman.waktuku.view

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.revifaturahman.waktuku.R
import com.revifaturahman.waktuku.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sekarang aman untuk binding dan permission
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pindah ke sini
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
        }

        loadFragment(ActivityFragment())

        binding.navMenu.setOnItemSelectedListener { fragment ->
            when (fragment.itemId) {
                R.id.avtivity -> loadFragment(ActivityFragment())
                R.id.create -> loadFragment(CreateFragment())
            }
            true
        }
    }


    fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}