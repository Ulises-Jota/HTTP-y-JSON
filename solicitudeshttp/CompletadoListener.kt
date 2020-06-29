package com.example.solicitudeshttp

// Esta interface actúa de puente entre las clases DescargaURL y MainActivity.
interface CompletadoListener {
    fun descargaCompleta(resultado: String)
}