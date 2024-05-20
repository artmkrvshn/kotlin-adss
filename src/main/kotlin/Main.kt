import kotlin.system.exitProcess

fun main() {
    val matrixDataVar4 = arrayOf(
        doubleArrayOf(3.0, 2.0, 2.0),
        doubleArrayOf(1.0, -2.0, 5.0),
        doubleArrayOf(-2.0, -3.0, 4.0)
    )

//    val matrixData = arrayOf(
//        doubleArrayOf(5.0, -3.0, 7.0),
//        doubleArrayOf(-1.0, 4.0, 3.0),
//        doubleArrayOf(6.0, -2.0, 5.0)
//    )


    val matrixData = arrayOf(
        doubleArrayOf(6.0, 2.0, 5.0),
        doubleArrayOf(-3.0, 4.0, -1.0),
        doubleArrayOf(1.0, 4.0, 3.0)
    )


    var matrix = Matrix(matrixData)

    while (true) {
        println("1 - Enter matrix A; 2 - Inverse; 3 - Rank; 4 - Solve; 5 - Print matrix; 0 - Exit")
        val input = readln()
        when (input) {
            "0" -> exitProcess(0)
            "1" -> {
                print("Enter variant 4? +/-: ")
                val answer = readln().trim()
                if (answer == "+") {
                    matrix = Matrix(matrixDataVar4)
                } else {
                    print("Enter matrix's rows: ")
                    val rows = readln().trim().toInt()

                    print("Enter matrix's columns: ")
                    val columns = readln().trim().toInt()

                    val newMatrixData = Array(rows) { DoubleArray(columns) }

                    println("Enter the elements of the matrix A:")
                    for (i in 0 until rows) {
                        val numbers = readln().split(" ").toList().map { it.toDouble() }.toDoubleArray()
                        if (numbers.size != columns) {
                            throw RuntimeException("Wrong number of columns!")
                        }
                        newMatrixData[i] = numbers
                    }
                    matrix = Matrix(newMatrixData)
                }
            }

            "2" -> {
                println("Matrix A:")
                println(matrixToString(matrix))
                val inverseMatrix = matrix.inverse()
                println("\nInverse Matrix A^-1:")
                println(matrixToString(inverseMatrix))
            }

            "3" -> {
                val rank = matrix.rank()
                println("\nRank of Matrix A: $rank")
            }

            "4" -> {
                println("Enter the elements of the vector B:")
                val vectorB = readln().split(" ").toList().map { it.toDouble() }.toDoubleArray()

                val solution = matrix.solve(vectorB)
                println("\nSolution of the system AX = B:")
                println(arrayToString(solution))
            }

            "5" -> {
                println(matrixToString(matrix))
            }
        }

    }
}

private fun matrixToString(matrixGPT: Matrix): String {
    return matrixGPT.data.joinToString("\n") { it.joinToString("\t") { value -> "%.3f".format(value) } }
}

private fun arrayToString(array: DoubleArray): String {
    return array.joinToString("\t") { value -> "%.3f".format(value) }
}