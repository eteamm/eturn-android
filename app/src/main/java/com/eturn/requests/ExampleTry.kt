package com.eturn.requests

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ExampleTry {
    val client = OkHttpClient()

    fun run(){

        val request = Request.Builder()
            .url("http://publicobject.com/helloworld.txt")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("Запрос к серверу не был успешен:" +
                                " ${response.code} ${response.message}")
                    }
                    // пример получения всех заголовков ответа
                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }
                    // вывод тела ответа
                    println(response.body!!.string())
                }
            }
        })
    }
}