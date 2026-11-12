package com.example.noteapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String>
        get() = _selectedDate
    init {
        _selectedDate.value = ""
    }
    fun setSelectedDate(date: String){
        _selectedDate.value = date
    }
}