package railway

sealed class Result<out T, out E> {
    fun <R> map(transform: (T) -> R): Result<R, E> = when (this) {
        is Success -> Success(transform(this.value))
        is Failure -> this
    }

    fun <R> flatMap(transform: (T) -> Result<R, @UnsafeVariance E>): Result<R, E> = when (this) {
        is Success -> transform(this.value)
        is Failure -> this
    }

    fun getOrElse(defaultValue: @UnsafeVariance T): T = when (this) {
        is Success -> this.value
        is Failure -> defaultValue
    }

    fun <R> onError(transform: (E) -> R): Result<T, R> = when (this) {
        is Success -> this
        is Failure -> Failure(transform(this.error))
    }
}

data class Success<out T>(val value: T) : Result<T, Nothing>()
data class Failure<out E>(val error: E) : Result<Nothing, E>()
