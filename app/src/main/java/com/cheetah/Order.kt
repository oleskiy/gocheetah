package com.cheetah

open class Order{
        internal var quantity: Float? = null
        internal var sub_total: Int? = null
        internal var substitutable: Boolean? = null
        internal var packaging_type: String? = null
        internal var product: Product? = null


    inner class Product{
        internal var name: String? = null
        internal var unit_price: Int? = null
        internal var unit_photo_filename: String? = null
    }
}