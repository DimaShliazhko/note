package com.example.noties.feature

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main() {
    println("Start Flow")


/*    getFirstFlow().collect { flow ->
        println("Collecting $flow")
        delay(1000) // Emulate work
        println("$flow collected")
    }

    getFirstFlow().collectLatest { flow ->
        println("Collecting $flow")
        delay(1000) // Emulate work
        println("$flow collected")
    }

    getFirstFlow()
        .map { item -> item * 2 }
        .collect { flow ->
            println("Collecting $flow")
            delay(1000) // Emulate work
            println("$flow collected")
        }*/

    getSecondFlow().sample(100).collect { flow ->
        println("Collecting $flow")
    }

}

fun getFirstFlow() = flow {
    println("numberFlow has started")
    for (item in 1..5) {
        emit(item)
    }
}

fun getSecondFlow() = flow {
    repeat(10) {
        emit(it)
        delay(50)
    }
}