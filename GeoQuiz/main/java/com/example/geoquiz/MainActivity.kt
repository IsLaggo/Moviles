package com.example.geoquiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var texto: TextView
    private lateinit var imagen: ImageView
    private lateinit var bt_opcion1: Button
    private lateinit var bt_opcion2: Button
    private lateinit var bt_opcion3: Button
    private lateinit var bt_pista:Button
    private lateinit var lista: List<Pregunta>
    private var indice = 0
    private var puntos=0
    private lateinit var tv_puntos: TextView

    //Ficheros de preferencias
    private lateinit var  preferencias: SharedPreferences

    //Sonidos
    private lateinit var acierto: MediaPlayer
    private lateinit var error: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        texto = findViewById(R.id.texto)
        imagen = findViewById(R.id.imageView)
        bt_opcion1 = findViewById(R.id.bt_opcion1)
        bt_opcion2 = findViewById(R.id.bt_opcion2)
        bt_opcion3 = findViewById(R.id.bt_opcion3)
        bt_pista = findViewById(R.id.bt_pista)
        tv_puntos = findViewById(R.id.tv_puntos)

        preferencias = getSharedPreferences("fichero_puntos", MODE_PRIVATE)

        puntos = preferencias.getInt("PUNTOS", 0)

        tv_puntos.text = puntos.toString() + " puntos"

        acierto = MediaPlayer.create(applicationContext, R.raw.correct)
        error = MediaPlayer.create(applicationContext, R.raw.error)

        val pregunta1: Pregunta = Pregunta(
            R.string.texto_pregunta,
            R.drawable.tamesis, R.string.opcion1, R.string.opcion2,
            R.string.opcion3, 1, R.string.pista1

        )
        val pregunta2: Pregunta = Pregunta(
            R.string.texto_pregunta2,
            R.drawable.madrid, R.string.opcion1_2, R.string.opcion2_2,
            R.string.opcion3_2, 3, R.string.pista2
        )
        val pregunta3: Pregunta = Pregunta(
            R.string.texto_pregunta3,
            R.drawable.kilimanjaro, R.string.opcion1_3, R.string.opcion2_3,
            R.string.opcion3_3, 2, R.string.pista3
        )

        lista = listOf<Pregunta>(pregunta1, pregunta2, pregunta3)

       if (savedInstanceState != null) {
           indice = savedInstanceState.getInt("INDICE")
       }

        actualizarPregunta()

        bt_opcion1.setOnClickListener {
            comprobarRespuesta(1)
        }

        bt_opcion2.setOnClickListener {
            comprobarRespuesta(2)
        }
        bt_opcion3.setOnClickListener {
            comprobarRespuesta(3)

        }
        imagen.setOnClickListener {
            indice = (indice + 1) % lista.size
            actualizarPregunta()

        }

        bt_pista.setOnClickListener {
            val intent = Intent(applicationContext, PistaActivity::class.java)
            intent.putExtra("PISTA", getString(lista.get(indice).pista))
            startActivity(intent)
        }
    }

    fun guardarPuntaciones() {
        val editor = preferencias.edit()
        editor.putInt("PUNTOS", puntos)
        editor.putString("USUARIO", "Ana")
        editor.commit()
    }

    fun actualizarPregunta() {
        texto.setText(lista.get(indice).texto)
        imagen.setImageResource(lista.get(indice).imagen)
        bt_opcion1.setText(lista.get(indice).opcion1)
        bt_opcion2.setText(lista.get(indice).opcion2)
        bt_opcion3.setText(lista.get(indice).opcion3)
        habilitarBotones()
    }

    fun desabilitarBotones() {
        bt_opcion1.setEnabled(false)
        bt_opcion2.setEnabled(false)
        bt_opcion3.setEnabled(false)

    }

    fun habilitarBotones() {
        bt_opcion1.setEnabled(true)
        bt_opcion2.setEnabled(true)
        bt_opcion3.setEnabled(true)

    }
    fun comprobarRespuesta(boton:Int) {
        if (lista.get(indice).respuesta == boton) {
            puntos += 20
            Toast.makeText(applicationContext, R.string.correcto, Toast.LENGTH_SHORT).show()
            acierto.start()
        } else {
            puntos -= 10
            Toast.makeText(applicationContext, R.string.incorrecto, Toast.LENGTH_SHORT)
                .show()
            error.start()
        }
        tv_puntos.text = puntos.toString() + " puntos"

        desabilitarBotones()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(applicationContext, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(applicationContext, "onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        guardarPuntaciones()
        Toast.makeText(applicationContext, "onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext, "onDestroy", Toast.LENGTH_SHORT).show()
    }

    //Esta funcióon se llama antes de destruir la actividad
    override fun onSaveInstanceState(estado_salida: Bundle) {
        super.onSaveInstanceState(estado_salida)
        estado_salida.putInt("INDICE", indice)
    }
}
