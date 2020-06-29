package com.example.solicitudeshttp

import android.os.AsyncTask
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class DescargaURL(var completadoListener: CompletadoListener): AsyncTask<String, Void, String>() {

    // Esta función se ejecuta en un thread diferente al principal
    override fun doInBackground(vararg params: String): String? {
        try {
            return descargarDatos(params[0])
        } catch (e: IOException){
            return null
        }
    }

    // Esta función trabaja con el resultado de doInBackground, al que manda a la clase Main a través de la interface
    override fun onPostExecute(result: String) {
        try {
            completadoListener.descargaCompleta(result)
        }catch (e: Exception){

        }
    }

    @Throws(IOException::class)
    private fun descargarDatos(url: String): String {
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
    }
}