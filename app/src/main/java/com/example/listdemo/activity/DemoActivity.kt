package com.example.listdemo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listdemo.R
import com.example.listdemo.data.CurrencyInfo
import com.example.listdemo.fragment.CurrencyListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        goToCurrencyListFragment()
    }

    private fun goToCurrencyListFragment(){

        val demoList = ArrayList<CurrencyInfo>()
        demoList.add(CurrencyInfo("Test 1", "Test 1", "Test 1"))
        demoList.add(CurrencyInfo("Test 2", "Test 2", "Test 2"))
        demoList.add(CurrencyInfo("Test 3", "Test 3", "Test 3"))

        val fragment = CurrencyListFragment.newInstance(demoList)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_layout, fragment, null)
            .addToBackStack(null)
            .commit()

    }
}