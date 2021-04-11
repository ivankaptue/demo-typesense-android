package com.klid.demotypesense.pagination

import androidx.paging.PageKeyedDataSource
import com.klid.demotypesense.api.StudentRepository
import com.klid.demotypesense.api.model.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author Ivan Kaptue
 */
class StudentDataSource(
    private val scope: CoroutineScope,
    private val query: String
) : PageKeyedDataSource<Int, Student>() {

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 20
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Student>
    ) {
        scope.launch {
            try {
                val response = StudentRepository.search(query, FIRST_PAGE, PAGE_SIZE)
                val nextPageKey = if (response.body()!!.last) null else FIRST_PAGE + 1
                callback.onResult(response.body()!!.content, null, nextPageKey)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Student>
    ) {
        scope.launch {
            try {
                val response = StudentRepository.search(query, params.key, PAGE_SIZE)
                val adjacentKey = if (params.key > FIRST_PAGE) params.key - 1 else null
                callback.onResult(response.body()!!.content, adjacentKey)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Student>
    ) {
        scope.launch {
            try {
                val response = StudentRepository.search(query, params.key, PAGE_SIZE)
                val adjacentKey = if (response.body()!!.last) null else params.key + 1
                callback.onResult(response.body()!!.content, adjacentKey)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

    }
}
