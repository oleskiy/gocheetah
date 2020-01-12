package com.cheetah

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.text.NumberFormat
import java.util.*


class ListAdapter(private val context: Context, list: List<Order>) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
       return list.size
    }

    public fun setAll(listAll:List<Order>){
        list = listAll as ArrayList<Order>
        notifyDataSetChanged()
    }

    private var list = ArrayList<Order>()

    init {
        this.list = list as ArrayList<Order>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {

        holder.tvName.setText(list[position].product!!.name)
        holder.tvTotalPrice.setText(NumberFormat.getCurrencyInstance(Locale.US).format(list[position].sub_total!!/100) )
        holder.tvPrice.setText(NumberFormat.getCurrencyInstance(Locale.US).format(list[position].product!!.unit_price!!/100)+"/"+list[position].packaging_type)
        holder.tvCount.setText(list[position].quantity.toString())
        Glide.with(holder.ivIcon.context)
            .load(list[position].product!!.unit_photo_filename)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    Log.d("onLoadFailed",e.toString())
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    Log.d("onResourceReady","true")
                    return false
                }

            })
            .placeholder(R.drawable.icon)
            .into(holder.ivIcon)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvName: TextView
        internal var tvPrice: TextView
        internal var tvTotalPrice: TextView
        internal var tvCount: TextView
        internal var ivIcon: ImageView

        init {
            tvName = itemView.findViewById(R.id.productName)
            tvTotalPrice = itemView.findViewById(R.id.price)
            tvPrice = itemView.findViewById(R.id.productPrice)
            tvCount = itemView.findViewById(R.id.count)
            ivIcon = itemView.findViewById(R.id.productIcon)

        }
    }
}