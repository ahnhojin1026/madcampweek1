package com.example.madcampcom1.viewModel

import android.content.ContentResolver
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.telephony.PhoneNumberUtils
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
import java.util.Locale
import javax.inject.Inject

data class ContactUIState(
    val contactMap: Map<Char, List<ContactEntity>> = emptyMap(),
    val expandedId: String? = null,
    val isMenuExpanded: Boolean = false
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
                _uiState.update {
                    it.copy(contactMap = contactListToMap(contactList))
                }
            }
        }
    }

    private fun contactListToMap(list: List<ContactEntity>): Map<Char, List<ContactEntity>> {
        val map = mutableMapOf<Char, List<ContactEntity>>()

        val koreanConsonant = arrayOf(
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
            'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
        )

        fun getConsonant(c: Char): Char = koreanConsonant[(c.code - 44032) / 588]
        fun isKorean(c: Char): Boolean = c in 'ㄱ'..'ㅣ' || c in '가'..'힣'
        fun isNumber(c: Char): Boolean = c in '0'..'9'
        fun isSpecial(c: Char): Boolean =
            c in '!'..'/' || c in ':'..'@' || c in '['..'`' || c in '{'..'~'

        fun charToKey(c: Char): Char {
            if (isKorean(c)) return getConsonant(c)
            if (isNumber(c)) return '#'
            if (isSpecial(c)) return '&'
            return c
        }

        for (contactEntity in list) {
            val key = charToKey(contactEntity.name[0].uppercaseChar())
            map[key] = map.getOrPut(key) { emptyList() }.plus(contactEntity)
        }
        return map
    }

    fun getContactFromContentResolver() {
        Log.d("ContactViewModel", "getContact")

        val list = mutableListOf<ContactEntity>()
        val projection: Array<out String> = arrayOf(
            Phone._ID,
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
                id = getString(Phone._ID),
                name = getString(Phone.DISPLAY_NAME),
                number = PhoneNumberUtils.formatNumber(
                    getString(Phone.NUMBER),
                    Locale.getDefault().country
                )
            ).let {
                list.add(it)
            }

        }
        cursor?.close()

        addContacts(list)
    }

    fun addContact(contactEntity: ContactEntity) =
        viewModelScope.launch { contactRepository.addContact(contactEntity) }

    private fun addContacts(contacts: List<ContactEntity>) =
        viewModelScope.launch { contactRepository.addContacts(contacts) }

    fun updateContact(contactEntity: ContactEntity) =
        viewModelScope.launch { contactRepository.updateContact(contactEntity) }

    fun removeContact(contactEntity: ContactEntity) =
        viewModelScope.launch { contactRepository.deleteContact(contactEntity) }

    fun removeAll() = viewModelScope.launch { contactRepository.deleteAll() }

    fun onItemClicked(id: String) {
        _uiState.update {
            it.copy(
                expandedId = if (isExpanded(id)) null else id
            )
        }
    }

    fun isExpanded(id: String): Boolean {
        return _uiState.value.expandedId == id
    }

    fun onMenu(isMenuExpanded: Boolean) =
        _uiState.update { it.copy(isMenuExpanded = isMenuExpanded) }
}