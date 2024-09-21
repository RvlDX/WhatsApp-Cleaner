import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class CleanOptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clean_options)

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

    // Fungsi untuk membersihkan semua folder
    private fun deleteWhatsAppFiles() {
        val directories = arrayOf(".shared", "Databases", "Backups", "Media/.statuses")
        directories.forEach { dir -> deleteSpecificFolder(dir) }
    }
}
