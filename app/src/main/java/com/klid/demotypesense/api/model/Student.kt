package com.klid.demotypesense.api.model

import com.squareup.moshi.JsonClass

/**
 * @author Ivan Kaptue
 */
@JsonClass(generateAdapter = false)
data class Student(
    val id: Long?,
    val firstname: String?,
    val lastname: String?,
    val email: String?,
    val school: String?
)
