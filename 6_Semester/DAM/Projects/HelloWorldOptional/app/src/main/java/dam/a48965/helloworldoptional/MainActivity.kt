package dam.a48965.helloworldoptional

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dam.a48965.helloworldoptional.R.layout.activity_main

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the EditText widget
        val editText = findViewById<EditText>(R.id.editTextTextMultiLine)

        // Extract build information using android.os.Build
        val buildInfo = StringBuilder()
        buildInfo.append("Manufacturer: ${Build.MANUFACTURER}\n")
        buildInfo.append("Model: ${Build.MODEL}\n")
        buildInfo.append("Brand: ${Build.BRAND}\n")
        buildInfo.append("Type: ${Build.TYPE}\n")
        buildInfo.append("User: ${Build.USER}\n")
        buildInfo.append("Base OS: ${Build.VERSION.BASE_OS}\n")
        buildInfo.append("Incremental: ${Build.VERSION.INCREMENTAL}\n")
        buildInfo.append("SDK Version: ${Build.VERSION.SDK_INT}\n")
        buildInfo.append("Version Code: ${Build.VERSION.RELEASE}\n")
        buildInfo.append("Display: ${Build.DISPLAY}\n")

        // Set the build information to the EditText widget
        editText.setText(buildInfo.toString())
    }
}
