package com.example.listdemo.activity

import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listdemo.R
import com.example.listdemo.data.CurrencyInfo
import com.example.listdemo.fragment.CurrencyListFragment
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast

import android.R.attr.data
import android.app.PendingIntent.getActivity
import com.example.listdemo.viewmodel.CurrencyInfoItemViewModel


@AndroidEntryPoint
class DemoActivity : AppCompatActivity(), CurrencyListFragment.OnCurrencyItemClickParentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        supportActionBar?.hide()
        goToCurrencyListFragment()
    }

    private fun goToCurrencyListFragment(){

        val demoList = ArrayList<CurrencyInfo>()
        demoList.add(CurrencyInfo("Test 1", "Test 1", "Test 1"))
        demoList.add(CurrencyInfo("Test 2", "Test 2", "Test 2"))
        demoList.add(CurrencyInfo("Test 3", "Test 3", "Test 3"))
        demoList.add(CurrencyInfo("Test 1", "Test 1", "Test 1"))
        demoList.add(CurrencyInfo("Test 2", "Test 2", "Test 2"))
        demoList.add(CurrencyInfo("Test 3", "Test 3", "Test 3"))
        demoList.add(CurrencyInfo("Test 1", "Test 1", "Test 1"))
        demoList.add(CurrencyInfo("Test 2", "Test 2", "Test 2"))
        demoList.add(CurrencyInfo("Test 3", "Test 3", "Test 3"))
        demoList.add(CurrencyInfo("Test 1", "Test 1", "Test 1"))
        demoList.add(CurrencyInfo("Test 2", "Test 2", "Test 2"))
        demoList.add(CurrencyInfo("Test 3", "Test 3", "Test 3"))

        val fragment = CurrencyListFragment.newInstance(demoList, this)
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

    private fun setupClickListener(){

    }

    override fun onCurrencyItemClick(currencyInfoItemViewModel: CurrencyInfoItemViewModel) {
        showToastMessage("In DemoActivity now.\nCurrency ${currencyInfoItemViewModel.name.value} is clicked!")
    }
}