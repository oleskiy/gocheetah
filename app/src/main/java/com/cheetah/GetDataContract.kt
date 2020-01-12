package com.cheetah

import android.content.Context


interface GetDataContract {
    interface View {
        fun onGetDataSuccess(message: Int, list: List<Order>)
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun getDataFromURL(context: Context, url: String)
    }

    interface Interactor {
        fun initRetrofitCall(context: Context, url: String)

    }

    interface onGetDataListener {
        fun onSuccess(message: Int, list: List<Order>)
        fun onFailure(message: String)
    }
}
