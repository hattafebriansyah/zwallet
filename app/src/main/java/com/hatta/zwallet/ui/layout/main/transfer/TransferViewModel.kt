package com.hatta.zwallet.ui.layout.main.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.ContactUser
import com.hatta.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import id.grinaldi.zwallet.model.request.TransferRequest
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

}