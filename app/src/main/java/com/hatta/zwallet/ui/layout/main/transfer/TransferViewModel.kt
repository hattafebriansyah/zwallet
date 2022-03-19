package com.hatta.zwallet.ui.layout.main.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.model.*
import com.hatta.zwallet.model.request.TransferRequest
import com.hatta.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private var  dataSource : ZWalletDataSource): ViewModel(){

    private var selectedContact = MutableLiveData<ContactUser>()
    private var transfer = MutableLiveData<TransferRequest>()

    fun getContactUser(): LiveData<Resource<APIResponse<List<ContactUser>>?>> {
        return dataSource.getContactUser()
    }

    fun setSelectedContact(contact: ContactUser) {
        selectedContact.value = contact
    }

    fun getSelectedContact(): MutableLiveData<ContactUser> {
        return selectedContact
    }

    fun setTransferParameter(data: TransferRequest) {
        transfer.value = data
    }

    fun getTransferParameter(): MutableLiveData<TransferRequest> {
        return transfer
    }

    fun transfer(data: TransferRequest, pin: String): LiveData<Resource<APIResponseTransfer<Transfer>?>> {
        return dataSource.transfer(data, pin)
    }

    fun getBalance() : LiveData<Resource<APIResponse<List<Balance>>?>> {
        return dataSource.getBalance()
    }

}