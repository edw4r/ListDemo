package com.example.listdemo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listdemo.R
import com.example.listdemo.data.CurrencyInfo
import com.example.listdemo.fragment.CurrencyListFragment
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels

import androidx.lifecycle.lifecycleScope
import com.example.listdemo.data.MockDataHelper
import com.example.listdemo.databinding.ActivityDemoBinding
import com.example.listdemo.viewmodel.CurrencyInfoItemViewModel
import com.example.listdemo.viewmodel.CurrencyListViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DemoActivity : AppCompatActivity(), CurrencyListFragment.OnCurrencyItemClickParentListener {

    private val currencyListViewModel: CurrencyListViewModel by viewModels()
    private lateinit var binding: ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_demo
        )
        binding.lifecycleOwner = this
        binding.viewModel = currencyListViewModel

        supportActionBar?.hide()
        presentListFragment()
    }

    private fun presentListFragment(){
        val fragment = CurrencyListFragment.newInstance(ArrayList(), this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment, null)
            .addToBackStack(null)
            .commit()
    }


    private fun showToastMessage(msg : String){
        Toast.makeText(
           this, msg, Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCurrencyItemClick(currencyInfoItemViewModel: CurrencyInfoItemViewModel) {
        showToastMessage("In DemoActivity now.\nCurrency ${currencyInfoItemViewModel.name.value} is clicked!")
    }

}