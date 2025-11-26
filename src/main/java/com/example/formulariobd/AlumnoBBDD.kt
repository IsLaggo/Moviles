package com.example.formulariobd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AlumnoBBDD (context: Context): SQLiteOpenHelper(context, "alumnos", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreate = "CREATE TABLE Alumno(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nombre VARCHAR(50), " +
                        "correo VARCHAR(100), " +
                        "delegado NUMERIC, " +
                        "ciclo VARCHAR(50) " +
                        ")"
        db?.execSQL(sqlCreate)
    }


    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TODO("Not yet implemented")
    }

    fun insertarAlumno(alumno:Alumno){
        val db = writableDatabase

        var delegado = 0
        if (alumno.delegado) {
            delegado = 1;
        }

        val SQLInsertar = "INSERT INTO Alumno " +
                "(nombre, correo, delegado, ciclo) " +
                "VALUES (" +
                "'" + alumno.nombre + "', " +
                "'" + alumno.correo + "', " +
                ""  + delegado + ", " +
                "'" + alumno.ciclo + "'" +
                ")"

        db?.execSQL(SQLInsertar)
    }
}