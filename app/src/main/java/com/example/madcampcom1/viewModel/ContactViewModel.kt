package com.example.madcampcom1.viewModel

import android.content.ContentResolver
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madcampcom1.data.local.entity.ContactEntity
import com.example.madcampcom1.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ContactUIState(
    val list: List<ContactEntity> = ArrayList()
)

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val contentResolver: ContentResolver
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ContactUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.getAll().distinctUntilChanged().collect { contactList ->
                if (contactList.isNullOrEmpty()) {
                    Log.d("", "getContact from contentResolver")

                    val list = mutableListOf<ContactEntity>()
                    val projection: Array<out String> = arrayOf(
                        Phone.CONTACT_ID,
                        Phone.DISPLAY_NAME,
                        Phone.NUMBER
                    )

                    val cursor = contentResolver.query(
                        Phone.CONTENT_URI,
                        projection,
                        null,
                        null,
                        "${Phone.DISPLAY_NAME} ASC"
                    )
                    while (cursor?.moveToNext() == true) {
                        fun getString(columnNameString: String): String {
                            val idx = cursor.getColumnIndex(columnNameString)
                            return cursor.getString(idx)
                        }

                        ContactEntity(
                            contactId = getString(Phone.CONTACT_ID),
                            name = getString(Phone.DISPLAY_NAME),
                            number = getString(Phone.NUMBER)
                        ).let {
                            list.add(it)
                            contactRepository.addContact(it)
                        }

                    }
                    cursor?.close()

                    _uiState.update {
                        it.copy(list = list)
                    }
                } else {
                    Log.d("", "getContact from contactRepository")

                    _uiState.update {
                        it.copy(list = contactList)
                    }
                }
            }
        }
    }

    fun addContact(contactEntity: ContactEntity) =
        viewModelScope.launch { contactRepository.addContact(contactEntity) }

    fun updateContact(contactEntity: ContactEntity) =
        viewModelScope.launch { contactRepository.updateContact(contactEntity) }

    fun removeContact(contactEntity: ContactEntity) =
        viewModelScope.launch { contactRepository.deleteContact(contactEntity) }
}