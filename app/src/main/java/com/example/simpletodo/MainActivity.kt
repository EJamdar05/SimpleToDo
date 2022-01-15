package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

/*
    User Interactions will be handled here/logic handling
 */

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>();
    lateinit var adapter : TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //set up layout file (xml link)

        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //remove item from list
                listOfTasks.removeAt(position)
                //notify adapter of change
                adapter.notifyDataSetChanged()

                //save the changes
                saveItems()

            }

        }
        //populate list of tasks
        loadItems()

        //look up recycle view in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        //attach adapter to recycle view
        recyclerView.adapter = adapter
        //layout manager to set itself out
        recyclerView.layoutManager = LinearLayoutManager(this)

        val textField = findViewById<EditText>(R.id.addTaskField)
        //button setup and input field for items to be added
        //add reference to button and then getting onClick listener
        findViewById<Button>(R.id.button).setOnClickListener{ //telling kotlin to find a specific view by id
            //code for button click event
            //Log.i("Caren","Button clicked by user") //tag, message

            //get user input
            val userInput = textField.text.toString() //converting obj to string input
            //add string to array of tasks (strings)
            listOfTasks.add(userInput)

            //notify adapter that data is updated
            adapter.notifyItemInserted(listOfTasks.size-1) //append to end of list

            //save to file
            saveItems()

            //clear the input field
            textField.setText("")

        }

        //deleting items via long click


    }

    //save data user has inputted via basic io

    //method to open file
    fun getDataFile(): File {
        return File(filesDir, "data.txt")
    }

    //load items line by line
    fun loadItems(){
        try {
            listOfTasks =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
    //save items to file
    fun saveItems(){
        try{
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }



}