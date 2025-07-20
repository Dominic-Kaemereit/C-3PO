package dev.dominic.c3po.handler

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class AIHandler {

    private val apiKey: String = System.getenv("TOGETHER_API_KEY")
        ?: throw IllegalStateException("TOGETHER_API_KEY environment variable not set")

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json".toMediaType()

    fun sendContent(content: String): CompletableFuture<String> {
        val future = CompletableFuture<String>()
        val prompt = buildPrompt(content)
        val requestBody = buildRequestBody(prompt)

        val request = Request.Builder()
            .url("https://api.together.xyz/v1/chat/completions")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                future.completeExceptionally(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        val errorBody = it.body?.string()?.takeIf { it.isNotBlank() } ?: "No response body"
                        future.complete("Unexpected response code: ${it.code}. Body: $errorBody")
                    } else {
                        val bodyStr = it.body?.string()
                        future.complete(bodyStr?.let { parseResponse(it) } ?: "Empty response")
                    }
                }
            }
        })

        return future
    }

    private fun buildPrompt(content: String) = """
        Du bist ein Discord Bot und schreibst in einem Channel und beantwortest Fragen von denn Usern.
        Bitte spreche wie C-3PO aus StarWars, da du diese Person darstellt.
        Bitte schreibe nie mehr als 260 Wörter.
        Bitte arbeite mit Zeilenumbrüchen.
        Bitte nutze Markdown, wenn es Sinnvoll ist.
        Bitte antworten Sie in der Sprache, in der der nächste Teil der Geschichte geschrieben ist.
        $content
    """.trimIndent()

    private fun buildRequestBody(prompt: String) = JSONObject(mapOf(
        "model" to "meta-llama/Llama-3.3-70B-Instruct-Turbo-Free",
        "messages" to listOf(mapOf("role" to "user", "content" to prompt))
    )).toString().toRequestBody(mediaType)

    private fun parseResponse(json: String): String {
        val obj = JSONObject(json)
        return obj.getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content")
    }
}