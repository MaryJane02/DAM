package dam.exer_3

fun main() {

    val heights = generateSequence(100.0) { it * 0.6 }
        .drop(1)
        .takeWhile {it >= 1.0}
        .take(15)
        .toList()

    println("Bounce Heights:")
    heights.forEach {
        println("%.2f".format(it))
    }
}