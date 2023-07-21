import railway.Failure
import railway.Result
import railway.Success
import java.io.File
import java.io.IOException

fun main(args: Array<String>) {
    val inputFile = "./input.txt"
    val outputFile = "./output.txt"

    fun readFile(filename: String): Result<String, String> {
        return try {
            println("${filename}을 읽습니다.")
            val fileContents = File(filename).readText()
            Success(fileContents)
        } catch (e: IOException) {
            Failure("${e.message}: 파일을 읽을 수 없습니다.")
        }
    }

    fun processData(data: String): Result<String, String> {
        return if (data.isNotBlank()) {
            println("내용을 추가합니다.")
            val processData = data.plus("\n데이터를 추가합니다.")
            Success(processData)
        } else {
            Failure("내용이 비어있습니다.")
        }
    }

    fun saveToFile(filename: String, data: String): Result<Boolean, String> {
        return try {
//            throw IOException("IOException")
            println("${filename}파일을 생성합니다.")
            File(filename).writeText(data)
            Success(true)
        } catch (e: IOException) {
            Failure("${e.message}: 파일을 저장하지 못했습니다.")
        }
    }

    fun print(): Result<Number, String> {
        return Success(1)
    }

    fun readFileRollback(filename: String) {
        println("${filename}을 읽는 작업이 롤백되었습니다.")
    }

    fun processDataRollback() {
        println("데이터를 입력이 롤백되었습니다..")
    }

    fun saveToFileRollback(filename: String) {
        println("$filename 파일을 삭제합니다.")
        File(filename).delete()
    }

    readFile(inputFile)
        .addRollbackFunction { readFileRollback(inputFile) }
        .flatMap { data -> processData(data) }
        .addRollbackFunction { processDataRollback() }
        .flatMap { processData -> saveToFile(outputFile, processData) }
        .addRollbackFunction { saveToFileRollback(outputFile) }
        .flatMap { print() }
        .flatMap { Failure("실패") }
        .onError { error ->
            println(error)
        }.onRollback()

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