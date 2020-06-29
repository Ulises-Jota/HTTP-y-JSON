package com.example.ejemplojson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var listaPersonas: ArrayList<Persona>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // La variable respuesta simula un string JSON devuelto luego de una solicitud HTTP
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

        // Transformar el string de respuesta en un objeto JSON
        val json = JSONObject(respuesta)

        // Recorre el objeto JSON para obtener los valores tomando en cuenta que es un array
        val personas = json.getJSONArray("personas")

        listaPersonas = ArrayList()

        // Para recorrer el array e ir obteniendo cada valor, se utiliza un for
        for (i in 0..personas.length() - 1){
            val nombre = personas.getJSONObject(i).getString("nombre")
            val pais = personas.getJSONObject(i).getString("pais")
            val estado = personas.getJSONObject(i).getString("estado")
            val experiencia = personas.getJSONObject(i).getInt("experiencia")

            // Se crea un objeto de tipo Persona que tiene los parámetros que se mapean del JSON
            // val persona = Persona(nombre, pais, estado, experiencia)
            // Log.d("PERSONA", persona.nombre)

            // Se agrega a la lista de tipo Persona, los objestos de tipo Persona mapeados del JSON
            listaPersonas?.add(Persona(nombre, pais, estado, experiencia))
        }

        Log.d("onCreate", listaPersonas?.count().toString())
    }
}
