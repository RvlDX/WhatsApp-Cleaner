import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnWhatsApp = findViewById<Button>(R.id.btnWhatsApp)
        btnWhatsApp.setOnClickListener {
            // Buka halaman pilihan pembersihan setelah memilih WhatsApp
            val intent = Intent(this, CleanOptionsActivity::class.java)
            startActivity(intent)
        }
    }
}
