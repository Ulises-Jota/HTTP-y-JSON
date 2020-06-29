package com.example.solicitudeshttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), CompletadoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bValidarRed = findViewById<Button>(R.id.bValidarRed)
        val bSolicitudHTTP = findViewById<Button>(R.id.bSolicitudHTTP)
        val bVolley = findViewById<Button>(R.id.bVolley)
        val bOk = findViewById<Button>(R.id.bOk)

        bValidarRed.setOnClickListener{
            // Validar red
            if (Network.hayRed(this)){
                Toast.makeText(this, "Sí hay red", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show()
            }
        }

        bSolicitudHTTP.setOnClickListener{
            // Valida red y luego llama a la función que corresponda según el ejemplo
            if (Network.hayRed(this)){
                // La línea siguiente se utiliza para ejemplificar una conexión en el mismo thread
                // Log.d("bSolicitudOnClick", descargarDatos("http://www.google.com"))

                // La línea siguiente se utiliza para ejemplificar una conexión en un thread diferente (asincrónica)
                DescargaURL(this).execute("http://www.google.com")

            } else {
                Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show()
            }
        }

        bVolley.setOnClickListener {
            // Validar red
            if (Network.hayRed(this)){
                solicitudHTTPVolley("http://www.google.com")
            } else {
                Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show()
            }
        }

        bOk.setOnClickListener {
            // Validar red
            if (Network.hayRed(this)){
                solicitudHTTPOkHTTP("http://www.google.com")
            } else {
                Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Método para Volley (no es necesario implementar clases e interfaces extras),
    // permite administrar múltiples solicitudes HTTP.
    private fun solicitudHTTPVolley(url: String){
        // Crea una queue donde se van a ir agregando las solicitudes
        val queue = Volley.newRequestQueue(this)

        // Crea la solicitud. Requiere el método, la url y la acción sobre la respuesta.
        val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String>{
            response ->
            try {
                Log.d("solicitudHTTPVolley", response)
            } catch (e: Exception){

            }
        }, Response.ErrorListener {  })

        // Agrega la solicitud a la queue
        queue.add(solicitud)
    }

    // Método para OkHTTP. También maneja varias solicitudes en una queue, pero hay explicitar que el resultado
    // se debe ejecutar en el thread principal, para tener más control en la respuesta.
    private fun solicitudHTTPOkHTTP(url: String){
        val cliente = OkHttpClient()
        val solicitud = okhttp3.Request.Builder().url(url).build()

        cliente.newCall(solicitud).enqueue(object: okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                // Implementar error en caso de que falle la solicitud
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val resultado = response.body()?.string()

                // Ejecuta el código en el thread principal
                this@MainActivity.runOnUiThread {
                    try {
                        Log.d("solicitudHTTPOkHTTP", resultado)
                    } catch (e: Exception){

                    }
                }
            }

        })
    }

    // La función sobreescrita de la interface permite mapear el resultado del thread que se ejectuta en DescargaURL
    override fun descargaCompleta(resultado: String) {
        Log.d("DescargaCompleta", resultado)
    }

    // La función descargarDatos() se usa para retornar un string a partir de un objeto InputStream.
    // El bloque se comenta ya que se reutiliza en la clase DescargaURL para ejemplificar
    // una conexión asíncrona.
    /*@Throws(IOException::class)
    private fun descargarDatos(url: String): String {

        // Setear ThreadPolicy para que permita las conexiones en el thread principal
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        var inputStream: InputStream? = null
        try {
            val url = URL(url)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connect()

            inputStream = conn.inputStream
            return inputStream.bufferedReader().use {
                it.readText()
            }
        } finally {
            if (inputStream != null){
                inputStream.close()
            }
        }
    }*/
}
