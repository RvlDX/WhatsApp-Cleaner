import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class CleanOptionsActivity : AppCompatActivity() {

    private val STORAGE_PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clean_options)

        // Memeriksa izin penyimpanan
        checkStoragePermissions()

        val checkBoxShared = findViewById<CheckBox>(R.id.checkBoxShared)
        val checkBoxDatabases = findViewById<CheckBox>(R.id.checkBoxDatabases)
        val checkBoxBackups = findViewById<CheckBox>(R.id.checkBoxBackups)
        val checkBoxStatuses = findViewById<CheckBox>(R.id.checkBoxStatuses)

        val btnCleanAll = findViewById<Button>(R.id.btnCleanAll)
        val btnCleanSelected = findViewById<Button>(R.id.btnCleanSelected)

        // Tombol bersihkan semua
        btnCleanAll.setOnClickListener {
            deleteWhatsAppFiles()
            Toast.makeText(this, "Semua file berhasil dibersihkan!", Toast.LENGTH_SHORT).show()
        }

        // Tombol bersihkan sesuai pilihan
        btnCleanSelected.setOnClickListener {
            if (checkBoxShared.isChecked) deleteSpecificFolder(".shared")
            if (checkBoxDatabases.isChecked) deleteSpecificFolder("Databases")
            if (checkBoxBackups.isChecked) deleteSpecificFolder("Backups")
            if (checkBoxStatuses.isChecked) deleteSpecificFolder("Media/.statuses")

            deleteAccountFolders()

            Toast.makeText(this, "File yang dipilih berhasil dibersihkan!", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk membersihkan folder tertentu
    private fun deleteSpecificFolder(folderName: String) {
        val whatsappPath = getExternalFilesDir(null)?.absolutePath + "/Android/media/com.whatsapp/WhatsApp/"
        val folder = File(whatsappPath + folderName)

        if (folder.exists() && folder.isDirectory) {
            val files = folder.listFiles()
            files?.forEach { file ->
                file.delete() // Menghapus file
            }
        }
    }

    // Fungsi untuk menghapus folder dalam folder account
    private fun deleteAccountFolders() {
        val whatsappPath = getExternalFilesDir(null)?.absolutePath + "/Android/media/com.whatsapp/WhatsApp/account/"
        val accountFolder = File(whatsappPath)

        if (accountFolder.exists() && accountFolder.isDirectory) {
            val randomFolders = accountFolder.listFiles { file -> file.isDirectory && file.name.matches(Regex("\\d+")) }
            randomFolders?.forEach { folder ->
                deleteSpecificFolder("${folder.absolutePath}/.shared")
                deleteSpecificFolder("${folder.absolutePath}/Databases")
                deleteSpecificFolder("${folder.absolutePath}/Backups")
                deleteSpecificFolder("${folder.absolutePath}/Media/.statuses")
            }
        }
    }

    // Fungsi untuk membersihkan semua folder
    private fun deleteWhatsAppFiles() {
        val directories = arrayOf(".shared", "Databases", "Backups", "Media/.statuses")
        directories.forEach { dir -> deleteSpecificFolder(dir) }
    }

    // Fungsi untuk memeriksa dan meminta izin penyimpanan
    private fun checkStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {

                // Meminta izin jika belum diberikan
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    // Menangani hasil permintaan izin
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin penyimpanan diberikan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Izin penyimpanan ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
