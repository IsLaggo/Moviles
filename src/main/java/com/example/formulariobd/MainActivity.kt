package com.example.formulariobd

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var et_nombre: EditText
    private lateinit var et_correo: EditText
    private lateinit var cb_delegado: CheckBox
    private lateinit var dam: RadioButton
    private lateinit var daw: RadioButton
    private lateinit var asir: RadioButton

    private lateinit var bt_insertar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        et_nombre = findViewById(R.id.et_nombre)
        et_correo = findViewById(R.id.et_correo)
        dam = findViewById(R.id.rb_dam)
        daw = findViewById(R.id.rb_daw)
        asir = findViewById(R.id.rb_asir)
        cb_delegado = findViewById(R.id.cb_delegado)
        bt_insertar = findViewById(R.id.button)

        var bd = AlumnoBBDD(applicationContext)

        bt_insertar.setOnClickListener {

            var ciclo = ""
            if (dam.isChecked){
                ciclo ="DAM"
            }
            else if (daw.isChecked){
                ciclo = "DAW"
            }
            else {
                ciclo = "ASIR"
            }
            val alumno = Alumno(0, et_nombre.text.toString(),
                et_correo.text.toString(), ciclo, cb_delegado.isChecked)

            bd.insertarAlumno(alumno)
        }



    }
}