package com.example.geoquiz

import androidx.annotation.StringRes

data class Pregunta(@StringRes val texto:Int,
                    val imagen:Int,
                    @StringRes val opcion1:Int,
                    @StringRes val opcion2:Int,
                    @StringRes val opcion3:Int,
                    val respuesta: Int,
                    val pista:Int)
