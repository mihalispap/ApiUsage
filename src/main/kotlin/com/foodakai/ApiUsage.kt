package com.foodakai

import com.foodakai.repositories.ApiQueryRepository
import org.json.JSONObject
import spark.kotlin.*

fun main(args: Array<String>) {

    val apiQueryRepository = ApiQueryRepository()

    val http: Http = ignite().port(4567)

    http.get("/hello") { "Welcome to website abc." }

    http.get("/find/top-params/:n") {
        apiQueryRepository.findTopNParams(params("n").toInt())
    }


}

