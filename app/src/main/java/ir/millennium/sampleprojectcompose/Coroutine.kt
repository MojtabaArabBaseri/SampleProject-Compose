package ir.millennium.sampleprojectcompose

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//fun main() = runBlocking {
//    val scope = CoroutineScope(Dispatchers.Default)
////    scope.launch { launchFeature() }
//    launchFeature()
//    delay(3000)
//    scope.cancel()
//}
//
//suspend fun launchFeature() = coroutineScope {
//    val job = launch {
//        delay(2000)
//        println("Koskhol nagaeidom")
//        delay(5000)
//    }
//    job.join()
//    println("finish mother fucker")
//}


// Sequentially executes doWorld followed by "Done"
//fun main() = runBlocking() {
//    CoroutineScope(Dispatchers.Default).launch { doWorld() }
//    delay(1500)
//    println("Done")
//}
//
//// Concurrently executes both sections
//suspend fun doWorld() = coroutineScope { // this: CoroutineScope
//    launch {
//        delay(2000L)
//        println("World 2")
//    }
//    launch {
//        delay(1000L)
//        println("World 1")
//    }
//    println("Hello")
//}

//fun main() = runBlocking {
//    repeat(1_000_000) {
//        launch {
//            delay(2_000)
//            print("*")
//        }
//    }
//}


@OptIn(DelicateCoroutinesApi::class)
fun main(): Unit = runBlocking {
    GlobalScope.launch {
        launch {
            delay(2_000)
            print("*")
        }
    }
}