package dam

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import java.util.Properties

class TempTest {

    @Test
    fun testLowTemperature() = runBlocking {

        val props = Properties().apply {
            setProperty("GEMINI_API_KEY", "AIzaSyDI72eLbJzk8bLdrRLqNOFxxXKwAaDZQOk")
            setProperty("TEMPERATURE", "0.1")
        }

        val assistant = AIAssistantGeminiClasses(props)

        val response = assistant.processInput(
            "Tell me how you would survive a zombie apocalypse if you were a human."
        )

        println("\nLOW TEMPERATURE:\n$response")
    }

    @Test
    fun testHighTemperature() = runBlocking {

        val props = Properties().apply {
            setProperty("GEMINI_API_KEY", "AIzaSyDI72eLbJzk8bLdrRLqNOFxxXKwAaDZQOk")
            setProperty("TEMPERATURE", "0.9")
        }

        val assistant = AIAssistantGeminiClasses(props)

        val response = assistant.processInput(
            "Tell me how you would survive a zombie apocalypse if you were a human."
        )

        println("\nHIGH TEMPERATURE:\n$response")
    }
}