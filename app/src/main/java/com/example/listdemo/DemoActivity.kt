package com.example.listdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        goToCurrencyListFragment()
    }

    private fun goToCurrencyListFragment(){

        val fragment = CurrencyListFragment.newInstance("", "")
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_layout, fragment, null)
            .addToBackStack(null)
            .commit()

    }
}