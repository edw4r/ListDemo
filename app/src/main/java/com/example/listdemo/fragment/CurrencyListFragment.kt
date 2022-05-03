package com.example.listdemo.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listdemo.R
import com.example.listdemo.adapter.CurrencyInfoListAdapter
import com.example.listdemo.data.CurrencyInfo
import com.example.listdemo.databinding.FragmentCurrencyListBinding
import com.example.listdemo.viewmodel.CurrencyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.listdemo.activity.DemoActivity
import com.example.listdemo.viewmodel.CurrencyInfoItemViewModel

@AndroidEntryPoint
class CurrencyListFragment : Fragment(), CurrencyInfoListAdapter.OnCurrencyItemClickListener {

    companion object {

        const val KEY_CURRENCY_INFO_LIST = "KEY_CURRENCY_INFO_LIST"

        @JvmStatic
        fun newInstance(
            currencyInfo: ArrayList<CurrencyInfo>,
            currencyItemClickParentListenerOwner: OnCurrencyItemClickParentListener
        ) =
            CurrencyListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(KEY_CURRENCY_INFO_LIST, currencyInfo)
                }
                currencyItemClickParentListener = currencyItemClickParentListenerOwner
            }
    }

    interface OnCurrencyItemClickParentListener {
        fun onCurrencyItemClick(currencyInfoItemViewModel: CurrencyInfoItemViewModel)
    }

    var currencyItemClickParentListener: OnCurrencyItemClickParentListener? = null

    private lateinit var binding: FragmentCurrencyListBinding
    private lateinit var currencyInfoListAdapter: CurrencyInfoListAdapter
    private val currencyListViewModel: CurrencyListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_currency_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = this.currencyListViewModel

        subscribe()
        setupRecyclerview()
        setCurrencyList()

    }

    private fun setCurrencyList() {
        val bundle = this.arguments
        if (bundle != null) {
            val currencyList = bundle.getParcelableArrayList<CurrencyInfo>(KEY_CURRENCY_INFO_LIST)
            currencyListViewModel.setCurrencyList(currencyList)
        }
    }

    private fun subscribe() {
        currencyListViewModel.currencyInfoItemViewModels.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                currencyInfoListAdapter.submitList(it)
            }
        })

    }

    private fun setupRecyclerview() {

        val layoutManager = LinearLayoutManager(context)
        currencyInfoListAdapter = CurrencyInfoListAdapter(this).apply {
            this.setHasStableIds(true)
            this.currencyItemClickListener = this@CurrencyListFragment
        }
        currencyInfoListAdapter.let {
            binding.listOfCurrencyInfo.adapter = currencyInfoListAdapter
        }
        binding.listOfCurrencyInfo.layoutManager = layoutManager
        binding.listOfCurrencyInfo.setHasFixedSize(true)
        binding.listOfCurrencyInfo.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onCurrencyItemClick(currencyInfoItemViewModel: CurrencyInfoItemViewModel) {
        currencyItemClickParentListener?.onCurrencyItemClick(currencyInfoItemViewModel)
    }


}