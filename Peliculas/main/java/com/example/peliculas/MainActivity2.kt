package com.example.peliculas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    private lateinit var tv_correcto: TextView
    private lateinit var tv_adivinados: TextView
    private lateinit var bt_web: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        tv_correcto = findViewById(R.id.tv_correcto)
        tv_adivinados = findViewById(R.id.tv_aciertos)

        var correcto = intent.getBooleanExtra("ACIERTO", false)
        var adivinadas = intent.getIntExtra("ADIVINADAS", 0)
        if (correcto) {
            tv_correcto.text = getString(R.string.resultado) + getString(R.string.correcto)
        }
        else {
            tv_correcto.text = getString(R.string.resultado) + getString(R.string.incorrecto)
        }
        tv_adivinados.text = getString(R.string.adivinadas) + " " + adivinadas.toString()

        bt_web.setOnClickListener {
            var i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            startActivity(i)
        }



    }
}