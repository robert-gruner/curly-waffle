package com.example.mynote

import android.content.Context
import android.content.DialogInterface
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class NoteDetailsActivityListener(
    val activity: AppCompatActivity,
    val textInput: EditText
) : MenuItem.OnMenuItemClickListener, DialogInterface.OnClickListener {
    private var fileName: String = activity.getString(R.string.noteFileName)

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.saveNote -> {
                saveNoteContent()
                true
            }
            R.id.deleteNote -> {
                showDeletionDialog()
                true
            }
            else -> false
        }
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

    private fun showDeletionDialog() {
        if (textInput.text.isEmpty()) {
            return
        }
        AlertDialog.Builder(activity)
            .setTitle(R.string.deleteHint)
            .setMessage(R.string.confirmDeletion)
            .setPositiveButton(android.R.string.yes, this)
            .setNegativeButton(android.R.string.no, this)
            .show()
    }

    private fun deleteNoteContent() {
        activity.deleteFile(fileName)
        textInput.text.clear()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which) {
            DialogInterface.BUTTON_POSITIVE -> deleteNoteContent()
        }
    }
}
