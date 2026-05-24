package dam

import kotlinx.coroutines.runBlocking
import java.util.Properties
import kotlin.test.Test

class SentimentTest {

    @Test
    fun testSentimentAnalysis() = runBlocking {
        val properties = Properties().apply {
            setProperty("GEMINI_API_KEY", "AIzaSyDI72eLbJzk8bLdrRLqNOFxxXKwAaDZQOk")
            setProperty("TEMPERATURE", "0.2")
        }

        val assistant = AIAssistantGeminiClasses(properties)

        val input =
            "analyze sentiment: \"I was expecting something better. The product works, but it's slower and more limited than I hoped.\""

        val response = assistant.processInput(input)

        println(response)
    }
}