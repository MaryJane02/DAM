package dam

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*

/**
 * GeminiAIAssistant class provides an interface to communicate with Google's Gemini AI models.
 * This class handles API authentication, request formatting, response parsing, and error handling.
 * It implements retry logic for rate-limited requests and validates JSON responses.
 *
 * @param properties Properties containing the API key for authentication with Gemini services
 */
class AIAssistantGeminiClasses(override val properties: Properties) : AIAssistant {

    override fun getSystem() = "GEMINI"
    override val apiKeyName = "GEMINI_API_KEY"

    // Model selection - different models have different capabilities and costs
    //  override var model = "gemini-1.0-pro" // NOK - Primary model for most tasks
    // override var model = "gemini-1.0-ultra" // NOK - Most capable model (if available)
    // override var model = "gemini-1.5-flash" // OK - Faster, less expensive
    // override var model = "gemini-1.5-pro" // OK - Primary model for most tasks
    // override var model = "gemini-2.0-flash" // OK - Most capable model (if available)
    // override var model = "gemini-2.0-pro" // NOK - Most capable model (if available)
    // override var model = "gemini-2.5-flash" // NOK - Most capable model (if available)
    // override var model = "gemini-2.5-flash-preview" // NOK - Most capable model (if available) //override var model = "gemini-2.5-flash-preview-04-17" // NOK - Most capable model (if available)
    override var model = "gemini-3.1-flash-lite"

    // Data classes for Gemini API request structure

    data class Part(
        val text: String
    )

    data class Content(
        val role: String,
        val parts: List<Part>
    )

    data class GeminiRequest(
        val contents: List<Content>,
        val generationConfig: GenerationConfig? = null
    )

    data class GenerationConfig(
        val temperature: Double? = 0.4,      // Default reasonable balance
        val topK: Int? = 40,                 // Limits selection to top K most likely tokens
        val topP: Double? = 0.95,            // Nucleus sampling - covers 95% of probability mass
        val maxOutputTokens: Int? = 800,     // Controls response length
        val candidateCount: Int? = 1         // Number of alternative responses to generate
    )


    // Gson instance for JSON serialization
    private val gson = Gson()

    private fun isSentimentRequest(prompt: String): Boolean {
        return prompt.trim().lowercase().startsWith("analyze sentiment:")
    }

    private fun buildSentimentPrompt(userInput: String): String {
        val contentToAnalyze = userInput.removePrefix("analyze sentiment:").trim()

        return """
        Analyze the sentiment of the following text and rate it on a 1–7 scale:

        1. Very Negative
        2. Negative
        3. Slightly Negative
        4. Neutral
        5. Slightly Positive
        6. Positive
        7. Very Positive

        Return only raw JSON in this exact format:
        {
          "rating": [number],
          "justification": "[reason]"
        }

        Do not use markdown.
        Do not wrap the answer in ```json.
        Do not include any text before or after the JSON.

        Text: "$contentToAnalyze"
    """.trimIndent()
    }

    /**
     * Constructs and formats a structured request from the given input prompt.
     * This method is intended to prepare the necessary request structure for
     * sending to an AI-powered model or API.
     *
     * @param prompt The user's input query or prompt that needs to be formatted into a request
     */
    override fun buildRequest(prompt: String): Request {
        val finalPrompt = if (isSentimentRequest(prompt)) {
            buildSentimentPrompt(prompt)
        } else {
            prompt
        }
        // Create request structure using data classes
        val part = Part(text = finalPrompt)
        val content = Content(
            role = "user",
            parts = listOf(part)
        )
        val geminiRequest = GeminiRequest(
            contents = listOf(content)
            , generationConfig = GenerationConfig(
                temperature = properties.getProperty("TEMPERATURE")?.toDouble(),
                maxOutputTokens = properties.getProperty("MAX_TOKENS")?.toInt()
        ))

        // Convert to JSON string using Gson
        val requestBody = gson.toJson(geminiRequest)

        // Configure the HTTP request with proper headers and authentication
        val request = Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1/models/$model:generateContent?key=$apiKey")  // Gemini API endpoint
            .addHeader("Content-Type", "application/json")  // Specify content type
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))  // Set the request body
            .build()
        return request
    }
}