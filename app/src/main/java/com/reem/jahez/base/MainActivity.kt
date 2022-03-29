package com.reem.jahez.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.reem.jahez.R
import com.reem.jahez.databinding.ActivityMainBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewModel :BaseViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun showLoading(show: Boolean){
        if (show){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }

    fun showMessage(message: String){
      if(message.isNotEmpty())  {
            binding.loginMessage.visibility = View.VISIBLE
          binding.loading.visibility = View.GONE
            binding.loginMessage.text = message
        } else {
            binding.loginMessage.visibility = View.GONE
        }
    }
}