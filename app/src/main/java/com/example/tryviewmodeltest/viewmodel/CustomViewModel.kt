package com.example.tryviewmodeltest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CustomViewModel(params: CustomViewModelParams) : BaseViewModel<CustomViewModelParams>(params) {

    private val liveData = MutableLiveData<State>()

    fun getStateLiveData(): LiveData<State> = liveData

    init {
        liveData.value = CustomViewModel.State(params.number, allItems.filterNot { it == params.number })
    }

    data class State(val number: Int, val otherNumbers: List<Int>)

}

data class CustomViewModelParams(val number: Int) : BaseParams

private val allItems = (0 until 10).toList()