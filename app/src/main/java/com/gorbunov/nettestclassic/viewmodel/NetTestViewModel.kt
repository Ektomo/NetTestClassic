package com.gorbunov.nettestclassic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorbunov.nettestclassic.model.NetTestItem
import com.gorbunov.nettestclassic.retrofit.NetTestApi
import com.gorbunov.nettestclassic.retrofit.NetTestService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Класс вью модели для того, чтобы сохранять данные между пересозданиями и перерисовками экрана,
 *  а также работы с сетью
 */
class NetTestViewModel: ViewModel() {
    private val statePr: MutableLiveData<NetTestViewState> = MutableLiveData(NetTestViewState.Loading)
    val state: LiveData<NetTestViewState> = statePr



    init{
        /**
         * Вызываем загрузку данных при инициализации
         */
        getData()
    }

    /**
     * Функция загрузки данных
     */
    private fun getData(){
        viewModelScope.launch {
            statePr.postValue(NetTestViewState.Loading)
            try {
                /**
                 * Запрос к серверу и парсинг
                 */
                val response = NetTestApi.retrofitService.getData()
                if (response.data.isNotEmpty()) {
                    val list = mutableListOf<NetTestItem>()
                    response.data.forEach { item ->
                        val category = item.direction.title
                        val count = item.groups.sumOf { it.items.size }
                        list.add(NetTestItem(category, count))
                    }
                    statePr.postValue(NetTestViewState.NetTestOk(list))
                } else {
                    throw IllegalStateException("Не удалось загрузить данные")
                }
            }catch (e: Exception){
                statePr.postValue(NetTestViewState.Error(e))
            }
        }
    }


    /**
     * Класс с состояниями экрана
     */
    sealed class NetTestViewState {
        object Loading : NetTestViewState()
        data class Error(val e: Exception) : NetTestViewState()
        data class NetTestOk(val data: List<NetTestItem>) : NetTestViewState()
    }

}