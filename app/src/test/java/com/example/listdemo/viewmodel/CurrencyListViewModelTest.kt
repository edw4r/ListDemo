package com.example.listdemo.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.listdemo.data.CurrencyInfo
import com.example.listdemo.data.CurrencyInfoDao
import com.example.listdemo.data.MockDataHelper
import com.example.listdemo.getOrAwaitValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class CurrencyListViewModelTest {

    // Keep LiveData to operate in Main Thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var currencyListViewModel: CurrencyListViewModel
    @MockK lateinit var currencyInfoDao : CurrencyInfoDao
    @MockK lateinit var mockDataHelper : MockDataHelper


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        currencyListViewModel = CurrencyListViewModel(testDispatcher, currencyInfoDao, mockDataHelper)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun test_mapCurrencyData() {

        // mock
        val mock = ArrayList<CurrencyInfo>()
        mock.add(CurrencyInfo("Test 1 id", "Test 1 name", "Test 1 symbol"))
        mock.add(CurrencyInfo("Test 2 id", "Test 2 name", "Test 2 symbol"))
        mock.add(CurrencyInfo("Test 3 id", "Test 3 name", "Test 3 symbol"))

        // run
        val testResult = currencyListViewModel.mapCurrencyData(mock)

        // verify
        val expectedResult = listOf(
            CurrencyInfoItemViewModel().apply {
                this.id.value = "Test 1 id"
                this.name.value = "Test 1 name"
                this.symbol.value = "Test 1 symbol"
                this.initialChar.value = "T"
            },
            CurrencyInfoItemViewModel().apply {
                this.id.value = "Test 2 id"
                this.name.value = "Test 2 name"
                this.symbol.value = "Test 2 symbol"
                this.initialChar.value = "T"
            },
            CurrencyInfoItemViewModel().apply {
                this.id.value = "Test 3 id"
                this.name.value = "Test 3 name"
                this.symbol.value = "Test 3 symbol"
                this.initialChar.value = "T"
            }
        )

        expectedResult.forEachIndexed { index, currencyInfoItemViewModel ->
            assertTrue(currencyInfoItemViewModel == testResult[index])
        }

    }

    @Test
    fun test_mapCurrencyData_empty_list() {

        // mock
        val mock = null

        // run
        val testResult = currencyListViewModel.mapCurrencyData(mock)

        // verify
        val expectedResult = listOf<CurrencyInfoItemViewModel>()

        assertTrue(expectedResult.isEmpty() == testResult.isEmpty())

    }

    @Test
    fun onLoadButtonClick() = runBlockingTest {

        // mock
        val mock = listOf(
            CurrencyInfo("Test 1 id", "Test 1 name", "Test 1 symbol"),
            CurrencyInfo("Test 2 id", "Test 2 name", "Test 2 symbol"),
            CurrencyInfo("Test 3 id", "Test 3 name", "Test 3 symbol")
        )
        every { mockDataHelper.getCurrencyMockData() } returns mock
        coEvery { currencyInfoDao.getAll() } returns mock

        val expectedResult = listOf(
            CurrencyInfoItemViewModel().apply {
                this.id.value = "Test 1 id"
                this.name.value = "Test 1 name"
                this.symbol.value = "Test 1 symbol"
                this.initialChar.value = "T"
            },
            CurrencyInfoItemViewModel().apply {
                this.id.value = "Test 2 id"
                this.name.value = "Test 2 name"
                this.symbol.value = "Test 2 symbol"
                this.initialChar.value = "T"
            },
            CurrencyInfoItemViewModel().apply {
                this.id.value = "Test 3 id"
                this.name.value = "Test 3 name"
                this.symbol.value = "Test 3 symbol"
                this.initialChar.value = "T"
            }
        )

        // run
        currencyListViewModel.onLoadButtonClick()
        testDispatcher.advanceUntilIdle()

        // Verify
        val testResult = currencyListViewModel.currencyInfoItemViewModels.getOrAwaitValue()
        expectedResult.forEachIndexed { index, currencyInfoItemViewModel ->
            assertTrue(currencyInfoItemViewModel == testResult[index])
        }
    }

    @Test
    fun onSortButtonClick_sorting_flag_is_true()= runBlockingTest {

        // mock
        val mock = listOf(
            CurrencyInfo("C", "C", "C"),
            CurrencyInfo("A", "A", "A"),
            CurrencyInfo("B", "B", "B"),

        )
        val sortedMock = listOf(
            CurrencyInfo("A", "A", "A"),
            CurrencyInfo("B", "B", "B"),
            CurrencyInfo("C", "C", "C")
        )

        every { mockDataHelper.getCurrencyMockData() } returns mock
        coEvery { currencyInfoDao.getAll() } returns mock
        coEvery { currencyInfoDao.getASCSorting() } returns sortedMock

        currencyListViewModel.sortingFlag = true

        val expectedResult = listOf(
            CurrencyInfoItemViewModel().apply {
                this.id.value = "A"
                this.name.value = "A"
                this.symbol.value = "A"
                this.initialChar.value = "A"
            },
            CurrencyInfoItemViewModel().apply {
                this.id.value = "B"
                this.name.value = "B"
                this.symbol.value = "B"
                this.initialChar.value = "B"
            },
            CurrencyInfoItemViewModel().apply {
                this.id.value = "C"
                this.name.value = "C"
                this.symbol.value = "C"
                this.initialChar.value = "C"
            }
        )

        // run
        currencyListViewModel.onSortButtonClick()
        testDispatcher.advanceUntilIdle()

        // verify
        val testResult = currencyListViewModel.currencyInfoItemViewModels.getOrAwaitValue()
        expectedResult.forEachIndexed { index, currencyInfoItemViewModel ->
            assertTrue(currencyInfoItemViewModel == testResult[index])
        }

       coVerify { currencyInfoDao.getASCSorting() }
    }

    @Test
    fun onSortButtonClick_sorting_flag_is_false()= runBlockingTest {

        // mock
        val mock = listOf(
            CurrencyInfo("C", "C", "C"),
            CurrencyInfo("A", "A", "A"),
            CurrencyInfo("B", "B", "B"),

            )
        val sortedMock = listOf(
            CurrencyInfo("C", "C", "C"),
            CurrencyInfo("B", "B", "B"),
            CurrencyInfo("A", "A", "A")
        )

        every { mockDataHelper.getCurrencyMockData() } returns mock
        coEvery { currencyInfoDao.getAll() } returns mock
        coEvery { currencyInfoDao.getDESCSorting() } returns sortedMock

        currencyListViewModel.sortingFlag = false

        val expectedResult = listOf(
            CurrencyInfoItemViewModel().apply {
                this.id.value = "C"
                this.name.value = "C"
                this.symbol.value = "C"
                this.initialChar.value = "C"
            },
            CurrencyInfoItemViewModel().apply {
                this.id.value = "B"
                this.name.value = "B"
                this.symbol.value = "B"
                this.initialChar.value = "B"
            },
            CurrencyInfoItemViewModel().apply {
                this.id.value = "A"
                this.name.value = "A"
                this.symbol.value = "A"
                this.initialChar.value = "A"
            }
        )

        // run
        currencyListViewModel.onSortButtonClick()
        testDispatcher.advanceUntilIdle()

        // verify
        val testResult = currencyListViewModel.currencyInfoItemViewModels.getOrAwaitValue()
        expectedResult.forEachIndexed { index, currencyInfoItemViewModel ->
            assertTrue(currencyInfoItemViewModel == testResult[index])
        }

        coVerify { currencyInfoDao.getDESCSorting() }
    }
}