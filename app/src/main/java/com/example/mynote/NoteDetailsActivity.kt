package com.example.mynote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

class NoteDetailsActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var noteDetailsActivityListener: NoteDetailsActivityListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)

        editText = findViewById(R.id.noteInput)
        noteDetailsActivityListener = NoteDetailsActivityListener(this, editText)
        editText.setText(noteDetailsActivityListener.readNoteContent())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return noteDetailsActivityListener.onMenuItemClick(item)
    }
}
