package com.example.mynote

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailsActivityListener(
    val activity: AppCompatActivity,
    val textInput: EditText
) : MenuItem.OnMenuItemClickListener,
    DialogInterface.OnClickListener {
    private var fileName: String = activity.getString(R.string.noteFileName)
    private lateinit var currentPhotoPath: String

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

    fun setImage() {
        val imageView = activity.findViewById<ImageView>(R.id.attachedNotePhoto)
        // Get the dimensions of the View
        val targetW: Int = imageView.width
        val targetH: Int = imageView.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            imageView.setImageBitmap(bitmap)
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

    private fun removePhoto() {
        TODO("Not yet implemented")
    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        activity,
                        "com.example.mynote",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    activity. startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
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
            putExtra(Intent.EXTRA_TEXT, textInput.text.toString())
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

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
}
