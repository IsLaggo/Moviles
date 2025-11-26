package com.example.peliculas

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Collections

class MainActivity : AppCompatActivity() {


    private var contTop = 0
    private var contMedio = 0
    private var contAbajo = 0
    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private lateinit var comprobar: Button
    private lateinit var texto: EditText
    private var nPreguntasAdivinadas = 0
    private lateinit var mpAcierto: MediaPlayer
    private lateinit var mpFallo: MediaPlayer

    private lateinit var preferencias: SharedPreferences

    private val listaArriba = listOf(
        Imagen(R.drawable.lasirenita_top, R.string.sol1),
        Imagen(R.drawable.ellibrodelaselva_top, R.string.sol2),
        Imagen(R.drawable.peterpan_top, R.string.sol3)
    )

    private val listaMedio = listOf(
        Imagen(R.drawable.lasirenita_center, R.string.sol1),
        Imagen(R.drawable.ellibrodelaselva_center, R.string.sol2),
        Imagen(R.drawable.peterpan_center, R.string.sol3)
    )

    private val listaAbajo = listOf(
        Imagen(R.drawable.lasirenita_bottom, R.string.sol1),
        Imagen(R.drawable.ellibrodelaselva_bottom, R.string.sol2),
        Imagen(R.drawable.peterpan_bottom, R.string.sol3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Collections.shuffle(listaArriba)
        Collections.shuffle(listaMedio)
        Collections.shuffle(listaAbajo)

        preferencias = getSharedPreferences("fichero_adivinadas", MODE_PRIVATE)

        mpAcierto = MediaPlayer.create(this, R.raw.correct)
        mpFallo = MediaPlayer.create(this, R.raw.error)

        img1 = findViewById(R.id.img_top)
        img2 = findViewById(R.id.img_center)
        img3 = findViewById(R.id.img_bottom)
        comprobar = findViewById(R.id.btn_comprobar)
        texto = findViewById(R.id.editText)

        img1.setImageResource(listaArriba[0].imag)
        img2.setImageResource(listaMedio[0].imag)
        img3.setImageResource(listaAbajo[0].imag)


        img1.setOnClickListener {
            contTop = (contTop + 1) % listaArriba.size
            img1.setImageResource(listaArriba!![contTop].imag)
        }
        img2.setOnClickListener {
            contMedio = (contMedio + 1) % listaMedio.size
            img2.setImageResource(listaMedio!![contMedio].imag)
        }

        img3.setOnClickListener {
            contAbajo = (contAbajo + 1) % listaAbajo.size
            img3.setImageResource(listaAbajo[contAbajo].imag)
        }

        comprobar.setOnClickListener {
            comprobar()
        }
    }
    fun comprobar() {

        var acierto:Boolean = false
        if (listaArriba[contTop].pelicula  == listaMedio[contMedio].pelicula &&
            listaArriba[contTop].pelicula == listaAbajo[contAbajo].pelicula &&
            getString(listaArriba[contTop].pelicula).equals(texto.getText().toString().trim(),
                ignoreCase = true)) {
                acierto = true
                nPreguntasAdivinadas++;
                mpAcierto.start();
            guardar()
        }
        else {
            mpFallo.start()
        }

        var i: Intent = Intent(applicationContext, MainActivity2::class.java)
        i.putExtra("ACIERTO", acierto)
        //i.putExtra("ADIVINADAS", nPreguntasAdivinadas)
        startActivity(i)

    }

    fun guardar() {
        var editor = preferencias.edit()
        editor.putInt("ADIVINADAS", nPreguntasAdivinadas)
        editor.commit()
    }
}