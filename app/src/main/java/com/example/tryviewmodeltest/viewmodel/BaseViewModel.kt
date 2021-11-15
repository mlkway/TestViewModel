package com.example.tryviewmodeltest.viewmodel

abstract class BaseViewModel<out T : BaseParams>(protected val params: T){

}

interface BaseParams