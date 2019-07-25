package com.elad.meetup.viewmodel


import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elad.meetup.repo.CryptoCurrencyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import com.elad.meetup.model.CryptoCurrency
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject


class CryptoCurrencyViewModel @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) :
    ViewModel() {

    var cryptocurrenciesResult: MutableLiveData<List<CryptoCurrency>> = MutableLiveData()
    var cryptocurrenciesError: MutableLiveData<String> = MutableLiveData()
    var cryptocurrenciesLoader: MutableLiveData<Boolean> = MutableLiveData()

    fun cryptocurrenciesResult(): LiveData<List<CryptoCurrency>> {
        return cryptocurrenciesResult
    }

    fun cryptocurrenciesError(): LiveData<String> {
        return cryptocurrenciesError
    }

    fun cryptocurrenciesLoader(): LiveData<Boolean> {
        return cryptocurrenciesLoader
    }


     fun loadCryptocurrencies(limit: Int, offset: Int) {

        GlobalScope.launch {

            try {
                val cryptoCurrencies = cryptoCurrencyRepository.getCryptocurrencies(limit, offset)
                cryptocurrenciesResult.postValue(cryptoCurrencies)
                cryptocurrenciesLoader.postValue(false)
            } catch (e: Exception) {
                cryptocurrenciesLoader.postValue(false)
                e.printStackTrace()
            }
        }

    }

}