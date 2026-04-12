package org.example.dam.exer_2

fun addition(a: Double, b: Double): Double {
    return a + b
}

fun subtraction(a: Double, b: Double): Double{
    return a - b
}

fun multiplication(a: Double, b: Double): Double{
    return a * b
}

fun division(a: Double, b: Double): Double{
    return a / b
}

fun and(a: Boolean, b: Boolean): Boolean{
    return a && b
}

fun or(a: Boolean, b: Boolean): Boolean{
    return a || b
}

fun not(a: Boolean): Boolean{
    return !a
}

fun leftShift(a: Int, b: Int): Int {
    return a shl b
}

fun rightShift(a: Int, b: Int): Int {
    return a shr b
}

fun getValidDouble(prompt: String): Double {
    while (true) {
        println(prompt)
        val input = readlnOrNull()?.toDoubleOrNull()
        if (input != null) return input
        println("Invalid input. Please enter a valid number.")
    }
}

fun getValidBoolean(prompt: String): Boolean {
    while (true) {
        println(prompt)
        when (val input = readlnOrNull()?.lowercase()) {
            "true" -> return true
            "false" -> return false
            else -> println("Invalid input. Please enter true or false.")
        }
    }
}

fun getValidYesNo(prompt: String): Boolean {
    while (true) {
        println(prompt)
        when (readlnOrNull()?.lowercase()) {
            "yes" -> return true
            "no" -> return false
            else -> println("Invalid input. Please enter yes or no.")
        }
    }
}

fun getValidNonZeroDouble(prompt: String): Double {
    while (true) {
        val input = getValidDouble(prompt)
        if (input != 0.0) return input
        println("Cannot divide by zero. Please enter a non-zero number.")
    }
}

fun toHexString(value: Int): String {
    val hex = if (value < 0) {
        "0x" + (value.toUInt() and 0xFFFFFFFFu).toString(16).uppercase()
    } else {
        "0x" + value.toString(16).uppercase()
    }
    return hex
}


fun toHexString(value: Double): String {
    return toHexString(value.toInt())
}

fun main() {

    var continueCalc = true

    while(continueCalc){

        println("Choose operation: (+, -, *, /, &&, ||, !, shl, shr)")
        val operator = readlnOrNull()

        if(operator == "+" || operator == "-" || operator == "*" || operator == "/") {

            val num1 = getValidDouble("Enter first number: ")
            val num2 = if (operator == "/") getValidNonZeroDouble("Enter second number: ") else getValidDouble("Enter second number: ")

            when (operator) {
                "+" -> {
                    val result = addition(num1, num2)
                    println("Decimal: $result")
                    println("Hexadecimal: ${toHexString(result)}")
                }
                "-" -> {
                    val result = subtraction(num1, num2)
                    println("Decimal: $result")
                    println("Hexadecimal: ${toHexString(result)}")

                }

                "*" -> {
                    val result = multiplication(num1, num2)
                    println("Decimal: $result")
                    println("Hexadecimal: ${toHexString(result)}")
                }
                "/" -> {
                    val result = division(num1, num2)
                    println("Decimal: $result")
                    println("Hexadecimal: ${toHexString(result)}")
                }
            }

        } else if(operator == "&&" || operator == "||") {

            val value1 = getValidBoolean("Enter first value (true/false): ")
            val value2 = getValidBoolean("Enter second value (true/false): ")

            when (operator) {
                "&&" -> println("Result: ${and(value1, value2)}")
                "||" -> println("Result: ${or(value1, value2)}")
            }

        } else if(operator == "!") {

            val value = getValidBoolean("Enter value (true/false): ")
            println("Result: ${not(value)}")

        } else if(operator == "shl" || operator == "shr") {

            val num1 = getValidDouble("Enter first number (integer for shift operation): ").toInt()
            val num2 = getValidDouble("Enter number of positions to shift (integer): ").toInt()

            when (operator) {
                "shl" -> {
                    val result = leftShift(num1, num2)
                    println("Decimal: $result")
                    println("Hexadecimal: ${toHexString(result)}")
                }
                "shr" -> {
                    val result = rightShift(num1, num2)
                    println("Decimal: $result")
                    println("Hexadecimal: ${toHexString(result)}")
                }
            }
        } else {
            println("Invalid operation")
        }

        continueCalc = getValidYesNo("Do you want to continue? (yes/no): ")
    }

    println("Thank you for using the calculator!")
}
