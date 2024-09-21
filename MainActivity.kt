package com.example.filecleaner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private var folderUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnChooseFolder = findViewById<Button>(R.id.btnChooseFolder)
        val btnCleanNow = findViewById<Button>(R.id.btnCleanNow)

        // Pilih Folder
        btnChooseFolder.setOnClickListener {
            chooseFolder()
        }

        // Bersihkan file di folder yang dipilih
        btnCleanNow.setOnClickListener {
            if (folderUri != null) {
                cleanFolder(folderUri!!)
            } else {
                Toast.makeText(this, "Pilih folder terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Storage Access Framework untuk memilih folder
    private fun chooseFolder() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            folderUri = result.data?.data
            contentResolver.takePersistableUriPermission(
                folderUri!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            Toast.makeText(this, "Folder dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk membersihkan folder
    private fun cleanFolder(folderUri: Uri) {
        try {
            val docUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                folderUri,
                DocumentsContract.getTreeDocumentId(folderUri)
            )
            val cursor = contentResolver.query(docUri, arrayOf(DocumentsContract.Document.COLUMN_DOCUMENT_ID), null, null, null)

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val documentId = cursor.getString(0)
                    val fileUri = DocumentsContract.buildDocumentUriUsingTree(folderUri, documentId)

                    // Hapus file
                    contentResolver.delete(fileUri, null, null)
                }
                cursor.close()
                Toast.makeText(this, "Semua file dihapus", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal membersihkan folder", Toast.LENGTH_SHORT).show()
        }
    }
}
