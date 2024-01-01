package com.example.madcampcom1.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madcampcom1.data.local.entity.Note
import com.example.madcampcom1.data.repository.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository:MemoRepository) : ViewModel(){
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()
    
    init {
        viewModelScope.launch(Dispatchers.IO) { 
            repository.getAllNotes().distinctUntilChanged().collect { listOfNotes ->
                    if (listOfNotes.isNullOrEmpty()) {
                        Log.d("Empty", ":Empty list")
                    } else {
                        _noteList.value = listOfNotes
                    }

                }
        }
    }
    fun addNote(note: Note) = viewModelScope.launch { 
        repository.addNote(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }
    fun removeAllNote(note: Note) = viewModelScope.launch {
        repository.deleteAllNote()
    }
}