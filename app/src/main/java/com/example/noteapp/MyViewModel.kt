package com.example.noteapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val _selectedDate = MutableLiveData<String>("")
    val selectedDate: LiveData<String>
        get() = _selectedDate

    private val _notesList = MutableLiveData<List<Note>>(emptyList())
    val notesList: LiveData<List<Note>>
        get() = _notesList

    private val noteStorage = NotesStorage(application)

    init {
        loadNotes()
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun loadNotes() {
        _notesList.value = noteStorage.getNotes()
    }

    fun addNote(date: String, content: String) {
        noteStorage.addNote(date, content)
        loadNotes()
    }
}