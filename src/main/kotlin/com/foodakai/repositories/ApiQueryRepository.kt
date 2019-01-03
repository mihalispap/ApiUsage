package com.foodakai.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.foodakai.data.elasticsearch.Aggregation
import khttp.responses.Response
import org.json.JSONObject
import java.time.LocalDateTime

class ApiQueryRepository {

    var logstashIndex: String = "http://148.251.22.254:9205/logstash-"+ LocalDateTime.now().year+"."+
            (if (LocalDateTime.now().monthValue < 10) "0" +
                    LocalDateTime.now().monthValue else LocalDateTime.now().monthValue)+
    "."+LocalDateTime.now().dayOfMonth+"/_search";

    fun findTopNParams(n: Int): Any{

        //var month: String = if (LocalDateTime.now().monthValue < 10) "0" + LocalDateTime.now().monthValue else LocalDateTime.now().monthValue.toString()
        val response : Response = khttp.post(
                url = logstashIndex,
                json = JSONObject("{\n" +
                        "  \"aggs\": {\n" +
                        "    \"params\": {\n" +
                        "      \"terms\": {\n" +
                        "        \"field\": \"params.keyword\",\n" +
                        "        \"size\": "+n+"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
        )

        val obj : JSONObject = response.jsonObject

        return ((obj.get("aggregations") as JSONObject).get("params") as JSONObject).get("buckets")
    }

}