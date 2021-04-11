package com.klid.demotypesense.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.klid.demotypesense.R
import com.klid.demotypesense.adapter.StudentAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)
            .get(MainActivityViewModel::class.java)
    }

    private lateinit var studentAdapter: StudentAdapter
    private val searchHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        initViews()
        initViewModel()
    }

    private fun initViews() {
        val studentRecyclerView: RecyclerView = findViewById(R.id.student_recycler_view)
        studentAdapter = StudentAdapter()
        studentRecyclerView.adapter = studentAdapter
        studentRecyclerView.setHasFixedSize(true)

        val queryEditText: EditText = findViewById(R.id.query_edit_text)
        queryEditText.doAfterTextChanged {
            searchHandler.removeCallbacksAndMessages(null)
            searchHandler.postDelayed({
                handleSearch(it.toString())
            }, 200)
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun initViewModel() {
        viewModel.getStudents().observe(this) {
            studentAdapter.submitList(it)
        }
    }

    private fun handleSearch(query: String) {
        viewModel.setQuery(query)
        viewModel.invalidateDataSource()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
