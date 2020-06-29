package com.example.ejemplogson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val respuesta = "{ \"personas\" : [ " +
                "{" +
                " \"nombre\" : \"Javier\" ," +
                " \"pais\" : \"Argentina\" ," +
                " \"estado\" : \"soltero\" ," +
                " \"experiencia\" : 5}," +

                "{" +
                " \"nombre\" : \"Marcelo\" ," +
                " \"pais\" : \"Uruguay\" ," +
                " \"estado\" : \"casado\" ," +
                " \"experiencia\" : 16}" +
                " ]" +
                " }"

        // Con la librería GSON, es mucho más simple mapear un JSON que de forma nativa.
        val gson = Gson()
        val res = gson.fromJson(respuesta, Personas::class.java)
    }
}
