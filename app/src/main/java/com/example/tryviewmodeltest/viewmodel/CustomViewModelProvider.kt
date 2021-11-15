package com.example.tryviewmodeltest.viewmodel

import java.lang.ref.WeakReference

class CustomViewModelProvider private constructor() {

	private val viewModelMap = HashMap<Pair<Class<*>, BaseParams>, WeakReference<out BaseViewModel<*>>>()

	fun <T : BaseViewModel<P>, P : BaseParams> getViewModel(viewModelClass: Class<T>, params: P): T {

		val model = viewModelMap[viewModelClass to params]?.get()
		return if (model == null) {
			val constructor = try {
				viewModelClass.getConstructor(params.javaClass)
			} catch (e: Exception) {
				try {
					viewModelClass.getConstructor()
				} catch (e: Exception) {
					throw IllegalArgumentException("Constructor not found for provided parameters")
				}
			}

			val instance = constructor?.newInstance(params) ?: throw IllegalArgumentException()
			viewModelMap[viewModelClass to params] = WeakReference<T>(instance)
			instance
		} else model as? T ?: throw IllegalArgumentException()
	}

	companion object {

		private val providersMap = HashMap<Class<*>, WeakReference<CustomViewModelProvider>>()

		fun of(`object`: Any): CustomViewModelProvider {
			val provider = providersMap[`object`::class.java]?.get()
			return if (provider == null) {
				val customViewModelProvider = CustomViewModelProvider()
				providersMap[`object`::class.java] = WeakReference(customViewModelProvider)
				customViewModelProvider
			} else provider
		}

	}

}