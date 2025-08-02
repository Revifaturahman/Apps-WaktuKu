package com.example.waktuku.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.waktuku.R
import com.example.waktuku.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(ActivityFragment())

        binding.navMenu.setOnItemSelectedListener {fragment ->
            when(fragment.itemId){
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