package com.fax.cursotestingaris.product_list.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promotions")
data class PromotionEntity (
    @PrimaryKey
    val id: String,
    val productIds : String,
    val type: String,
    val percentage : String? = null,
    val buyX : Int? = null,
    val payY : Int? = null,
    val startAtEpoch : Long,
    val endAtEpoch : Long,
)