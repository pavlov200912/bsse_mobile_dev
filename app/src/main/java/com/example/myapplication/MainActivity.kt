package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.userRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        val adapter = UserAdapter()
        recyclerView.adapter = adapter
        adapter.userList = loadUsers()
        adapter.notifyDataSetChanged()
    }

    private fun loadUsers(): List<User> {
        return listOf(
            User("", "Ivan Pavlov", "Russia"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine"),
            User("", "Android Kisling", "Ukraine")
        )
    }


}