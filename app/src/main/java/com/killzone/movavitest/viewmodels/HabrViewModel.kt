package com.killzone.movavitest.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.killzone.movavitest.model.ConvertedHabrPost
import com.killzone.movavitest.model.ConvertedHabrRssFeed
import com.killzone.movavitest.repositories.MainRepository
import kotlinx.coroutines.launch
import com.killzone.movavitest.repositories.Result

class HabrViewModel @ViewModelInject constructor(
    val repository: MainRepository
) : ViewModel() {


    // List of Habr posts
    private var _habrPostsList = MutableLiveData<ConvertedHabrRssFeed>()
    val habrPostsList: MutableLiveData<ConvertedHabrRssFeed> = _habrPostsList

    // Activates when first loading occurs
    private var _loadingValue = MutableLiveData<Boolean>(false)
    val loadingValue: MutableLiveData<Boolean> = _loadingValue

    // Activates when refresing occurs
    private var _refresingValue = MutableLiveData<Boolean>(false)
    val refreshingValue: MutableLiveData<Boolean> = _refresingValue

    // Error message to display
    private var _errorValue = MutableLiveData<String>()
    val errorValue: MutableLiveData<String> = _errorValue

    // Activates when user clicks on post item in recycler view
    private var _navigateToDetailFragmentEvent = MutableLiveData<Boolean>(false)
    val navigateToDetailFragmentEvent: LiveData<Boolean> = _navigateToDetailFragmentEvent

    // Habr post that will be passed to DetailFragment
    var navigationInfo: ConvertedHabrPost? = null

    init {
        load(true)
    }

    fun load(initialLoad: Boolean = false) {
        viewModelScope.launch {
            setLoadingValue(initialLoad)
            val response = repository.getHabrPosts()

            if (response is Result.Success) {
                _habrPostsList.value = response.data
            } else if (response is Result.Error) {
                _errorValue.value = response.exception.message
            }
            setLoadingValue(initialLoad)
        }
    }



    fun onPostClicked(post: ConvertedHabrPost) {
        navigationInfo = post
        _navigateToDetailFragmentEvent.value = true
    }

    fun resetNavigateEventValue() {
        _navigateToDetailFragmentEvent.value = false
    }

    private fun setLoadingValue(loadValue: Boolean) {
        if (loadValue) {
            loadingValue.value = loadingValue.value == false
        } else {
            refreshingValue.value = refreshingValue.value == false
        }
    }

}