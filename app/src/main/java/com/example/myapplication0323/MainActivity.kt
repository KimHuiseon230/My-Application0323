package com.example.myapplication0323

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myapplication0323.databinding.ActivityMain2Binding
import com.example.myapplication0323.databinding.ActivityMainBinding
import com.example.myapplication0323.databinding.RegisterLayout2Binding
import com.example.myapplication0323.databinding.RegisterLayoutBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        binding.btnLogin.setOnClickListener(object:View.OnClickListener {
            override fun onClick(p0: View?) {
                val userBinding: RegisterLayout2Binding
                val dialogBuilder = AlertDialog.Builder(this@MainActivity)
                var userDialog: AlertDialog

                userBinding = RegisterLayout2Binding.inflate(layoutInflater)
                dialogBuilder.setView(userBinding.root)
                userDialog = dialogBuilder.create()
                userDialog .show()

                userBinding.delete.setOnClickListener {
                    Toast.makeText(applicationContext, "취소하셨습니다.", Toast.LENGTH_SHORT).show()
                    userDialog.dismiss()
                }
                userBinding.joinButton.setOnClickListener {
                    binding.tvName.text = userBinding.joinName.text.toString()
                    binding.tvId.text = userBinding.joinId.text.toString()
                    binding.tvGender.text = userBinding.joinGender.text.toString()
                    binding.tvEmail.text = userBinding.joinEmail.text.toString()
                    userDialog.dismiss()
                }
            }
        })
    }
}