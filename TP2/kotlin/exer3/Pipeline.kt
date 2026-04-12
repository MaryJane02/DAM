package org.example.dam.exer_3

import com.sun.org.apache.xerces.internal.impl.xpath.XPath

class Pipeline {

    private val stages = mutableListOf<Pair<String, (List<String>) -> List<String>>>()

    fun addStage(name: String, transform: (List<String>) -> List<String>) {
        stages.add(name to transform)
    }

    fun execute(input: List<String>): List<String> {
        var result = input

        for ((name, transform) in stages) {
            result = transform(result)
        }
        return result
    }

    fun describe() {
        println("Pipeline stages:")
        for ((index, stage) in stages.withIndex()) {
            println("${index+1}: ${stage.first}")
        }
    }
}

fun buildPipeline(lambda: Pipeline.() -> Unit): Pipeline {
    val pipeline = Pipeline()
    pipeline.lambda()
    return pipeline
}

fun main(){

    val logs = listOf (
        " INFO : server started ",
        " ERROR : disk full ",
        " DEBUG : checking config ",
        " ERROR : out of memory ",
        " INFO : request received ",
        " ERROR : connection timeout "
    )

    val pipeline = buildPipeline {
        addStage("Trim"){
            steps -> steps.map {it.trim()}
        }
        addStage("Filter Errors"){
            steps -> steps.filter {it.contains("ERROR")}
        }
        addStage("Uppercase"){
            steps -> steps.map {it.uppercase()}
        }
        addStage("Add index"){
            steps -> steps.mapIndexed {index, step -> "${index+1}.$step"  }
        }
    }

    pipeline.describe()

    val result = pipeline.execute(logs)
    println()
    println("Result:")
    result.forEach { println(it) }
}
