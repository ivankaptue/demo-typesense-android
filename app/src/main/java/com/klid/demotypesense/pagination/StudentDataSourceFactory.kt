package com.klid.demotypesense.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.klid.demotypesense.api.model.Student
import kotlinx.coroutines.CoroutineScope

/**
 * @author Ivan Kaptue
 */
class StudentDataSourceFactory(
    private val scope: CoroutineScope,
    private val query: String
) : DataSource.Factory<Int, Student>() {
    val source = MutableLiveData<StudentDataSource>()

    override fun create(): DataSource<Int, Student> {
        val dataSource = StudentDataSource(scope, query)
        source.postValue(dataSource)
        return dataSource
    }
}
