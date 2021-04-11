package com.klid.demotypesense.api

import com.klid.demotypesense.api.model.PageModel
import com.klid.demotypesense.api.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * @author Ivan Kaptue
 */
object StudentRepository {

    private val apiService = ApiFactory.apiService

    suspend fun create(student: Student): Response<Student> =
        withContext(Dispatchers.IO) {
            apiService.create(student)
        }

    suspend fun update(id: Long, student: Student): Response<Student> =
        withContext(Dispatchers.IO) {
            apiService.update(id, student)
        }

    suspend fun delete(id: Long): Response<Void> =
        withContext(Dispatchers.IO) {
            apiService.delete(id)
        }

    suspend fun findById(id: Long): Response<Student> =
        withContext(Dispatchers.IO) {
            apiService.findById(id)
        }

    suspend fun findAll(page: Int, size: Int): Response<PageModel<Student>> =
        withContext(Dispatchers.IO) {
            apiService.findAll(page, size)
        }

    suspend fun search(query: String, page: Int, size: Int): Response<PageModel<Student>> =
        withContext(Dispatchers.IO) {
            apiService.search(query, page, size)
        }

}
