package ru.myuniquenickname.currencyconversion.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myuniquenickname.currencyconversion.domain.Currency
import ru.myuniquenickname.currencyconversion.domain.GetCurrencyInteractor

class CurrencyViewModel(private val getCurrencyList: GetCurrencyInteractor) : ViewModel() {

    private val _mutableLoadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState> get() = _mutableLoadingState

    private val _mutableActiveCurrency = MutableLiveData<Currency>()
    val activeCurrency: LiveData<Currency> get() = _mutableActiveCurrency

    private val _mutableCurrencyList = MutableLiveData<List<Currency>>()
    val currencyList: LiveData<List<Currency>> get() = _mutableCurrencyList

    fun loadCurrencyList() {

        viewModelScope.launch {
            try {
                _mutableLoadingState.value = LoadingState.LOADING
                val list: List<Currency> = getCurrencyList.getCurrencyList()

                if (list.isNotEmpty()) {
                    _mutableCurrencyList.value = list
                } else {
                    _mutableCurrencyList.value = getCurrencyList.loadCurrencyList()
                }
                if (_mutableActiveCurrency.value == null) {
                    _mutableActiveCurrency.value = _mutableCurrencyList.value?.get(10)
                }
                _mutableLoadingState.value = LoadingState.LOADED
            } catch (exception: Exception) {
                _mutableLoadingState.value = LoadingState.error(exception.message)
            }
        }
    }

    fun refreshCurrencyList() {
        try {
            viewModelScope.launch {
                _mutableLoadingState.value = LoadingState.LOADING
                _mutableCurrencyList.value = getCurrencyList.loadCurrencyList()
                _mutableLoadingState.value = LoadingState.LOADED
                Log.d("aaa", "sdasdasd")
            }
        } catch (exception: Exception) {
            _mutableLoadingState.value = LoadingState.error(exception.message)
        }
    }

    fun setActiveCurrency(currency: Currency) {
        _mutableActiveCurrency.value = currency
    }
}
