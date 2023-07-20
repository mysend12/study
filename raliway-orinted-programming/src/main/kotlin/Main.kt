import railway.Failure
import railway.Result
import railway.Success
import java.io.File
import java.io.IOException

fun main(args: Array<String>) {
    println("Hello World!")
    println("Program arguments: ${args.joinToString()}")

    val inputFile = "./input.txt"
    val outputFile = "./output.txt"

    fun readFile(filename: String): Result<String, String> {
        return try {
            val fileContents = File(filename).readText()
            Success(fileContents)
        } catch (e: IOException) {
            Failure("${e.message}: 파일을 읽을 수 없습니다.")
        }
    }

    fun processData(data: String): Result<String, String> {
        return if (data.isNotBlank()) {
            val processData = data.plus("\n데이터를 추가합니다.")
            Success(processData)
        } else {
            Failure("내용이 비어있습니다.")
        }
    }

    fun saveToFile(filename: String, data: String): Result<Boolean, String> {
        return try {
            File(filename).writeText(data)
            Success(true)
        } catch (e: IOException) {
            Failure("${e.message}: 파일을 저장하지 못했습니다.")
        }
    }

    fun print(): Result<Number, String> {
        println("성공")
        return Success(1)
    }

    readFile(inputFile)
        .flatMap { data -> processData(data) }
        .flatMap { processData -> saveToFile(outputFile, processData) }
        .flatMap { print() }
        .map { success -> println(success) }
        .onError { error ->
            println("실패")
            println(error)
        }

//    when (result) {
//        is Success -> {
//            println("성공")
//            println(result.value)
//        }
//
//        is Failure -> {
//            println("실패")
//            println(result.error)
//        }
//    }
}