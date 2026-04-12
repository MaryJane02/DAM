fun main(){

    //a - IntArray
    val array1 = IntArray(50) { i -> ((i+1) * (i+1))}
    println(array1.joinToString())

    //b - range + map()
    val array2 = (1..50).map { it * it }.toIntArray()
    println(array2.joinToString())

    //c - Array
    val array3 = Array(50) { i -> (i + 1) * (i + 1) }
    println(array3.joinToString())
}