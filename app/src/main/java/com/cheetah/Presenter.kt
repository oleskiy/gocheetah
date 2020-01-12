package com.cheetah

import android.content.Context


class Presenter(private val mGetDataView: GetDataContract.View) : GetDataContract.Presenter,
    GetDataContract.onGetDataListener {
    private val mIntractor: Intractor

    init {
        mIntractor = Intractor(this)
    }

    override fun getDataFromURL(context: Context, url: String) {
        mIntractor.initRetrofitCall(context, url)
    }

    override fun onSuccess(message: Int, allCountriesData: List<Order>) {
        mGetDataView.onGetDataSuccess(message, allCountriesData)
    }

    override fun onFailure(message: String) {
        mGetDataView.onGetDataFailure(message)
    }
}