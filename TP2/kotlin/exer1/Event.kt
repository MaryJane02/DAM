package org.example.dam.exer_1

import org.example.dam.exer_1.Event.Purchase

sealed class Event {
    class Login(val username: String, val timestamp: Long) : Event() {
        override fun toString(): String {
            return "Login(username=$username, timestamp=$timestamp)"
        }
    }

    class Purchase(val username: String, val amount: Double, val timestamp: Long) : Event() {
        override fun toString(): String {
            return "Purchase(username=$username, amount=$amount, timestamp=$timestamp)"
        }
    }

    class Logout(val username: String, val timestamp: Long) : Event() {
        override fun toString(): String {
            return "Logout(username=$username, timestamp=$timestamp)"
        }
    }
}

fun List<Event>.filterByUser(username: String): List<Event>{
    val outcome = mutableListOf<Event>()

    for (event in this) {
        if (event is Event.Login) {
            if (event.username == username) {
                outcome.add(event)
            }
        }
        else if (event is Event.Logout) {
            if (event.username == username) {
                outcome.add(event)
            }
        }
        else if (event is Event.Purchase) {
            if (event.username == username) {
                outcome.add(event)
            }
        }
    }
    return outcome
}

fun List<Event>.totalSpent(username: String): Double {
    val usernameEvents = this.filterByUser(username)
    val userPurchases = usernameEvents.filterIsInstance<Purchase>()
    val totalAmount = userPurchases.sumOf{it.amount}
    return totalAmount
}

fun processEvents(events: List<Event>, handler: (Event) -> Unit){
    for (event in events) {
        handler(event)
    }
}

fun main() {
    val events = listOf(
        Event.Login("alice", 1_000),
        Event.Purchase("alice", 49.99, 1_100),
        Event.Purchase("bob", 19.99, 1_200),
        Event.Login("bob", 1_050),
        Event.Purchase("alice", 15.0, 1_300),
        Event.Logout("alice", 1_400),
        Event.Logout("bob", 1_500)
    )

    processEvents(events) { event ->
        when (event) {
            is Event.Login ->
                println("[LOGIN] ${event.username} logged in at t = ${event.timestamp}")

            is Event.Purchase ->
                println("[PURCHASE] ${event.username} spent $${event.amount} at t = ${event.timestamp}")

            is Event.Logout ->
                println("[LOGOUT] ${event.username} logged out at t = ${event.timestamp}")
        }
    }

    println()
    println("Total spent by alice: ${events.totalSpent("alice")}")
    println("Total spent by bob: ${events.totalSpent("bob")}")
    println()
    println("Events for alice:")
    events.filterByUser("alice").forEach{println(it)}
}
