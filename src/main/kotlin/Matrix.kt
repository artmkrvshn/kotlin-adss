import kotlin.math.min

data class Matrix(val data: Array<DoubleArray>) {

    val rows: Int = data.size
    val columns: Int = data[0].size

    fun inverse(): Matrix {
        if (rows != columns) {
            throw RuntimeException("A matrix should have an equal number of rows and columns")
        }
        val matrix = clone()
        for (i in 0 until rows) {
            matrix.jordanElimination(i, i)
        }
        return matrix
    }

    fun rank(): Int {
        val matrix = clone()
        var rank = 0

        val min = min(rows, columns)

        for (i in 0 until min) {
            val element = matrix.data[i][i]
            if (element != 0.0) {
                matrix.jordanElimination(i, i)
                rank++
            }
        }
        return rank
    }

    fun solve(vectorB: DoubleArray): DoubleArray {
        if (rows != columns || rows != vectorB.size) {
            throw RuntimeException("Matrix dimensions do not match for solving the system")
        }
        val inverseMatrix = inverse()
        val solution = DoubleArray(vectorB.size)
        for (i in solution.indices) {
            for (j in vectorB.indices) {
                solution[i] += inverseMatrix.data[i][j] * vectorB[j]
            }
        }
        return solution
    }

    private fun jordanElimination(row: Int, column: Int) {
        val original = data.map { it.clone() }.toTypedArray()

        val element = data[row][column]
        data[row][column] = 1.0

        for (i in 0 until columns) {
            if (i != column) {
                data[row][i] = -data[row][i]
            }
        }

        for (i in 0 until rows) {
            if (i != row) {
                for (j in 0 until columns) {
                    if (j != column) {
                        data[i][j] = original[i][j] * original[row][column] - original[i][column] * original[row][j]
                    }
                }
            }
        }

        for (i in 0 until rows) {
            for (j in 0 until columns) {
                data[i][j] /= element
            }
        }
    }

    private fun clone(): Matrix {
        return Matrix(Array(data.size) { data[it].clone() })
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (!data.contentDeepEquals(other.data)) return false
        if (rows != other.rows) return false
        if (columns != other.columns) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.contentDeepHashCode()
        result = 31 * result + rows
        result = 31 * result + columns
        return result
    }
}