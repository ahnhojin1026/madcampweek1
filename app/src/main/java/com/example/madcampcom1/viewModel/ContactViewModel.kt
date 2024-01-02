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
    val expandedId: Int? = null,
    val isMenuExpanded: Boolean = false,
    val dialogValue: ContactEntity? = null
)

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository, private val contentResolver: ContentResolver
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContactUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.getAll().distinctUntilChanged().collect { contactList ->
                _uiState.update {
                    it.copy(contactMap = contactListToMap(contactList), expandedId = null)
                }
            }
        }
    }

    private fun nameToKey(name: String): Char {
        val koreanConsonant = arrayOf(
            'ㄱ',
            'ㄲ',
            'ㄴ',
            'ㄷ',
            'ㄸ',
            'ㄹ',
            'ㅁ',
            'ㅂ',
            'ㅃ',
            'ㅅ',
            'ㅆ',
            'ㅇ',
            'ㅈ',
            'ㅉ',
            'ㅊ',
            'ㅋ',
            'ㅌ',
            'ㅍ',
            'ㅎ'
        )

        fun getConsonant(c: Char): Char =
            if (c in koreanConsonant) c else koreanConsonant[(c.code - 44032) / 588]

        fun isKorean(c: Char): Boolean = c in 'ㄱ'..'ㅣ' || c in '가'..'힣'
        fun isNumber(c: Char): Boolean = c in '0'..'9'
        fun isSpecial(c: Char): Boolean =
            c in '!'..'/' || c in ':'..'@' || c in '['..'`' || c in '{'..'~'

        val c = name[0].uppercaseChar()

        if (isKorean(c)) return getConsonant(c)
        if (isNumber(c)) return '#'
        if (isSpecial(c)) return '&'
        return c
    }

    private fun contactListToMap(list: List<ContactEntity>): Map<Char, List<ContactEntity>> {
        val sortedList = list.sortedWith(compareBy({nameToKey(it.name)}, { it.name }))
        val map = mutableMapOf<Char, List<ContactEntity>>()

        for (contactEntity in sortedList) {
            val key = nameToKey(contactEntity.name)
            map[key] = map.getOrPut(key) { emptyList() }.plus(contactEntity)
        }
        return map
    }

    fun getContactFromContentResolver() {
        Log.d("ContactViewModel", "getContact")

        val list = mutableListOf<ContactEntity>()
        val projection: Array<out String> = arrayOf(
            Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER
        )

        val cursor = contentResolver.query(
            Phone.CONTENT_URI, projection, null, null, null
        )
        while (cursor?.moveToNext() == true) {
            fun getString(columnNameString: String): String {
                val idx = cursor.getColumnIndex(columnNameString)
                return cursor.getString(idx)
            }

            ContactEntity(
                id = getString(Phone.CONTACT_ID).toInt(),
                name = getString(Phone.DISPLAY_NAME),
                numbers = listOf(
                    PhoneNumberUtils.formatNumber(
                        getString(Phone.NUMBER), Locale.getDefault().country
                    )
                )
            ).let {
                list.find { some -> some.id == it.id }.let { some ->
                    if (some == null) list.add(it)
                    else {
                        list.remove(some)
                        list.add(it.copy(numbers = it.numbers + some.numbers))
                    }
                }
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

    fun onItemClicked(id: Int) {
        _uiState.update {
            it.copy(
                expandedId = if (isExpanded(id)) null else id
            )
        }
    }

    fun isExpanded(id: Int): Boolean {
        return _uiState.value.expandedId == id
    }

    fun onMenu(isMenuExpanded: Boolean) =
        _uiState.update { it.copy(isMenuExpanded = isMenuExpanded) }

    fun setDialogValue(dialogValue: ContactEntity?) =
        _uiState.update { it.copy(dialogValue = dialogValue) }
}