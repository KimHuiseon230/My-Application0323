package com.example.myapplication0323

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication0323.databinding.ActivityMain2Binding
import com.example.myapplication0323.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}