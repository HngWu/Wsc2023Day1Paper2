package com.example.wsc2023day1paper2app.api

import com.example.wsc2023day1paper2app.models.ProductItem
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

open class GetProducts {
    open fun getFunction(): MutableList<ProductItem>? {
        val url = URL("http://10.0.2.2:5104/Inflight")

        try {
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            con.setRequestProperty("Content-Type", "application/json; utf-8")
            con.setRequestProperty("Accept", "application/json")
            //con.connectTimeout = 1000

            val status = con.responseCode
            if (status == 200) {
                val reader = BufferedReader(InputStreamReader(con.inputStream))
                val jsonData = reader.use { it.readText() }
                reader.close()

                val objectList = Json.decodeFromString<List<ProductItem>>(jsonData) as MutableList<ProductItem>?


                return objectList


            }
            con.disconnect()
        } catch (e: Exception) {
            return null
        }
        return null
    }

}