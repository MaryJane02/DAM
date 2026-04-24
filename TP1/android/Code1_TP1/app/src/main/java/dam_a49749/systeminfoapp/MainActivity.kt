package dam_a49749.systeminfoapp

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText: EditText = findViewById(R.id.editTextTextMultiLine)

        val systemInfo = """
            Device Information:
            Manufacturer: ${Build.MANUFACTURER}
            Model: ${Build.MODEL}
            Brand: ${Build.BRAND}
            Type: ${Build.TYPE}
            User: ${Build.USER}
            Base: 1
            Incremental: ${Build.VERSION.INCREMENTAL}
            SDK: ${Build.VERSION.SDK_INT}
            Version Code: ${Build.VERSION.RELEASE}
            Display: ${Build.DISPLAY}
        """.trimIndent()

        editText.setText(systemInfo)
    }
}