package org.example.dam.exer_2

class Cache<K: Any, V: Any> {
    val map = mutableMapOf<K, V>()

    fun put (key: K, value: V){
        map[key] = value
    }
    fun get(key: K): V?{
        return map[key]
    }

    fun evict(key: K){
        map.remove(key)
    }

    fun size(): Int{
        return map.size
    }

    fun getOrPut(key: K, default: () -> V): V {

        val v = get(key)

        if(v != null){
            return v
        }
        else{
            put(key, default())
        }
        return default()
    }

    fun transform(key: K, action: (V) -> V): Boolean{
        val v = get(key)
        if(v != null){
            val update = action(v)
            put(key, update)
            return true
        }
        return false
    }

    fun snapshot(): Map<K, V>{
        val immutableCopy = map.toMap()
        return immutableCopy
    }

    fun filterValues(predicate: (V) -> Boolean): Map<K, V>{
        val mutableMap = mutableMapOf<K, V>()

        for ((key, value) in map){
            if (predicate(value)){
                mutableMap[key] = value
            }
        }
        return mutableMap.toMap()
    }

}

fun main() {
    val wordCache = Cache<String, Int>()

    wordCache.put("kotlin", 1)
    wordCache.put("scala", 1)
    wordCache.put("haskell", 1)

    println("--- Word frequency cache ---")
    println("Size: ${wordCache.size()}")
    println("Frequency of \"kotlin\": ${wordCache.get("kotlin")}")
    println("getOrPut \"kotlin\": ${wordCache.getOrPut("kotlin") { 0 }}")
    println("getOrPut \"java\": ${wordCache.getOrPut("java") { 0 }}")
    println("Size after getOrPut: ${wordCache.size()}")
    println("Transform \"kotlin\" (+1): ${wordCache.transform("kotlin") { it + 1 }}")
    println("Transform \"cobol\" (+1): ${wordCache.transform("cobol") { it + 1 }}")
    println("Snapshot: ${wordCache.snapshot()}")

    println("Words with frequency > 0: ${wordCache.filterValues { it > 0 }}")

    val idCache = Cache<Int, String>()

    idCache.put(1, "Alice")
    idCache.put(2, "Bob")

    println()
    println("--- Id registry cache ---")
    println("Id 1 -> ${idCache.get(1)}")
    println("Id 2 -> ${idCache.get(2)}")
    idCache.evict(1)
    println("After evict id 1, size: ${idCache.size()}")
    println("Id 1 after evict -> ${idCache.get(1)}")
}