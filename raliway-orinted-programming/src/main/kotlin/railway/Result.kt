package railway

sealed class Result<out T, out E>(
    open val rollbackFunctions: MutableList<() -> Any> = mutableListOf()
) {

    fun addRollbackFunction(
        rollbackFunction: () -> Unit
    ): Result<T, E> {
        if (this is Success) rollbackFunctions.add(rollbackFunction)
        return this
    }

    private fun copyRollbackFunctions(list: List<() -> Any>) {
        list.forEach { function -> this.rollbackFunctions.add(function) }
    }

    fun <R> map(transform: (T) -> R): Result<R, E> = when (this) {
        is Success -> Success(
            transform(this.value),
            rollbackFunctions = this.rollbackFunctions
        )

        is Failure -> this
    }

    fun <R> flatMap(transform: (T) -> Result<R, @UnsafeVariance E>): Result<R, E> = when (this) {
        is Success -> {
            val result = transform(this.value)
            result.copyRollbackFunctions(this.rollbackFunctions)
            result
        }
        is Failure -> this
    }

    fun <R> onError(transform: (E) -> R): Result<T, R> = when (this) {
        is Success -> this
        is Failure -> Failure(
            transform(this.error),
            rollbackFunctions = this.rollbackFunctions
        )
    }

    fun onRollback(): Result<T, E> = when (this) {
        is Success -> this
        is Failure -> {
            rollbackFunctions.reversed().forEach { func -> func() }
            this
        }
    }

}

data class Success<out T>(val value: T, override val rollbackFunctions: MutableList<() -> Any> = mutableListOf()) :
    Result<T, Nothing>() {

}

data class Failure<out E>(val error: E, override val rollbackFunctions: MutableList<() -> Any> = mutableListOf()) :
    Result<Nothing, E>()
