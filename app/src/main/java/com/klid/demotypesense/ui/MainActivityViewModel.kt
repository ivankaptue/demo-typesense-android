package com.klid.demotypesense.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.klid.demotypesense.api.model.Student
import com.klid.demotypesense.pagination.StudentDataSource
import com.klid.demotypesense.pagination.StudentDataSourceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

/**
 * @author Ivan Kaptue
 */
class MainActivityViewModel : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    private val _query = MutableLiveData("")
    private val query: LiveData<String>
        get() = _query

    private var studentFactory = StudentDataSourceFactory(coroutineScope, query.value!!)
    private var studentPagedList: LiveData<PagedList<Student>> = MutableLiveData()

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(StudentDataSource.PAGE_SIZE)
            .build()

        studentPagedList = Transformations.switchMap(query) {
            studentFactory = StudentDataSourceFactory(coroutineScope, query.value!!)
            LivePagedListBuilder(studentFactory, config).build()
        }
    }

    fun invalidateDataSource() {
        studentFactory.source.value?.invalidate()
    }

    fun getStudents(): LiveData<PagedList<Student>> {
        return studentPagedList
    }

    fun setQuery(query: String) {
        _query.postValue(query)
    }

    override fun onCleared() {
        coroutineScope.cancel()
    }
}
