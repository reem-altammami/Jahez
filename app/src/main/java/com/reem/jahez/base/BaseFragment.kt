package com.reem.jahez.base

import android.os.Message
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

open class BaseFragment : Fragment() {
    lateinit var viewModel :BaseViewModel

    protected fun setBaseViewModel(baseViewModel: BaseViewModel){
        this.viewModel = baseViewModel

        lifecycleScope.launch {
            viewModel.uiState.collect{
                when{
                    it.isLoading -> showLoading(true)
                    it.message.isNotEmpty() -> showMessage(it.message)
                    else -> {
                        showLoading(false)
                        showMessage("")
                    }

                }
            }
        }
    }

    fun showLoading(show: Boolean){
        val base = requireActivity() as MainActivity
        base.showLoading(show)
    }

    fun showMessage(message: String) {
        val base = requireActivity() as MainActivity
        base.showMessage(message)
    }

}