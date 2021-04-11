package com.klid.demotypesense.api.model

import com.squareup.moshi.JsonClass

/**
 * @author Ivan Kaptue
 */
@JsonClass(generateAdapter = false)
data class PageModel<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)
