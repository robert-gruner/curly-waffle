package com.example.mynote

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import java.io.File

class NoteDetailsActivityListener(
    val activity: AppCompatActivity,
    val textInput: EditText
) : MenuItem.OnMenuItemClickListener,
    DialogInterface.OnClickListener {
    private var fileName: String = activity.getString(R.string.noteFileName)

    companion object {
        val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.saveNote -> saveNoteContent()
            R.id.deleteNote -> showDeletionDialog()
            R.id.shareNote -> startShareNote()
            R.id.attachPhoto -> takePicture()
            R.id.attachedNotePhoto -> removePhoto()
            else -> {
                NavUtils.navigateUpFromSameTask(activity)
            }
        }
        return true
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which) {
            DialogInterface.BUTTON_POSITIVE -> deleteNoteContent()
        }
    }

    fun setImage(image: Bitmap) {
        activity.findViewById<ImageView>(R.id.attachedNotePhoto).setImageBitmap(image)
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

    private fun removePhoto() {
        TODO("Not yet implemented")
    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun startShareNote() {
        if (textInput.text.isEmpty()) {
            return
        }
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, textInput.text)
        }
        activity.startActivity(
            Intent.createChooser(intent, null) // OS zeigt dann den Auswahldialog an
        )
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
}
