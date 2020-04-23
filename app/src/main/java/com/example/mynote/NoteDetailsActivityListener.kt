package com.example.mynote

import android.content.Context
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class NoteDetailsActivityListener(
    val activity: AppCompatActivity,
    val textInput: EditText
) : MenuItem.OnMenuItemClickListener {
    private var fileName: String = activity.getString(R.string.noteFileName)

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.saveNote -> saveNoteContent()
            R.id.deleteNote -> deleteNoteContent()
        }
        return true
    }

    fun readNoteContent(): String {
        val file = File(activity.filesDir, fileName)
        return if (file.exists()) {
            val readStream = activity.openFileInput(fileName).bufferedReader(Charsets.UTF_8)
            val content = readStream.readText()
            readStream.close()
            return content
        } else ""
    }

    private fun saveNoteContent() {
        activity.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it?.write(textInput.text.toString().toByteArray(Charsets.UTF_8))
        }
    }

    private fun deleteNoteContent() {
        activity.deleteFile(fileName)
        textInput.text.clear()
    }
}
