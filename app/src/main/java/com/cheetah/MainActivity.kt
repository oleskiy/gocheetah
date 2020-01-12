package com.cheetah

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity(),GetDataContract.View {
       private var mPresenter: Presenter? = null

       lateinit var recyclerView: RecyclerView
       lateinit var total:TextView
       lateinit var tvToolbarTitle:TextView
       lateinit var searchText:TextView
       lateinit var searchButton:ImageView
       lateinit var recyclerViewAdapter:ListAdapter
       lateinit var listOrder: List<Order>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton = findViewById(com.cheetah.R.id.searchButton)
        searchText = findViewById(com.cheetah.R.id.searchField)
        tvToolbarTitle = findViewById(com.cheetah.R.id.tvToolbarTitle)

        mPresenter = Presenter(this)

        mPresenter!!.getDataFromURL(applicationContext, "http://www.mocky.io/v2/")
        total = findViewById(R.id.total)
         recyclerView = findViewById(R.id.products)

        var linearLayoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.setLayoutManager(linearLayoutManager)

        searchButton.setOnClickListener {
                v->search()
        }

        searchText.setOnEditorActionListener { v, actionId, event ->
              var s =   listOrder.filter { it.product!!.name!!.contains(searchText.text.toString()) }
            recyclerViewAdapter.setAll(s)
                false
        }
    }


    override fun onGetDataSuccess(message: Int, list: List<Order>) {

        Log.d("success",""+message )
        listOrder=list
        total.text = "Total: "+NumberFormat.getCurrencyInstance(Locale.US).format(message)
        recyclerViewAdapter = ListAdapter(applicationContext, list)
        recyclerView.setAdapter(recyclerViewAdapter)
    }

    override fun onGetDataFailure(message: String) {
        Log.d("error", message)
    }


    fun search(){
        if(searchText.isVisible){
            searchText.visibility= View.GONE
            tvToolbarTitle.visibility= View.VISIBLE
            recyclerViewAdapter.setAll(listOrder)
        }else{
            searchText.visibility= View.VISIBLE
            tvToolbarTitle.visibility= View.GONE
        }
    }

}
