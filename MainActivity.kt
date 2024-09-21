import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRequestPermission = findViewById<Button>(R.id.btnRequestPermission)
        btnRequestPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (!Settings.canDrawOverlays(this)) {
                    // Minta izin MANAGE_EXTERNAL_STORAGE
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = Uri.parse("package:" + this.packageName)
                    startActivityForResult(intent, 2296)
                } else {
                    Toast.makeText(this, "Izin sudah diberikan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
