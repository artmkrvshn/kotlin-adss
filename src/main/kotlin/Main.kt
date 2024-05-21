import kotlin.system.exitProcess

fun main() {
    var matrix = arrayOf(
        doubleArrayOf(6.0, 2.0, -4.0, -2.0),
        doubleArrayOf(4.0, 2.0, 2.0, 2.0),
        doubleArrayOf(6.0, 6.0, 8.0, 4.0)
    )

    while (true) {
        print("1 - enter matrix; wald/maximax/hurwicz/savage/bayes/laplace; 0 - exit")
        val command = readln()
        when (command) {
            "0" -> exitProcess(0)
            "1" -> {
                print("Enter rows: ")
                val rows = readln().toInt()
                print("Enter columns: ")
                val columns = readln().toInt()

                val newMatrix = Array(rows) { DoubleArray(columns) }

                for (i in 0 until rows) {
                    val row = readln()
                    val numbers = row.split(" ").map { it.trim().toDouble() }.toDoubleArray()
                    newMatrix[i] = numbers
                }
                matrix = newMatrix
            }

            "wald" -> println("Wald's criterion: ${criteriaWald(matrix)}\n")
            "maximax" -> println("Maximax criterion: ${criteriaMaximax(matrix)}\n")
            "hurwicz" -> {
                print("Enter alpha (0..1): ")
                val alpha = readln().toDouble()
                if (alpha > 1 || alpha < 0)
                    println("Hurwicz's criterion: ${criteriaHurwicz(matrix, alpha)}\n")
            }

            "savage" -> println("Savage's criterion: ${criteriaSavage(matrix)}\n")
            "bayes" -> {
                print("Enter probabilities (${matrix[0].size}, double): ")
                val probabilities = readln().split(" ").map { it.trim().toDouble() }.toDoubleArray()
                println("Bayes' criterion: ${criteriaBayes(matrix, probabilities)}\n")
            }

            "laplace" -> println("Laplace's criterion: ${criteriaLaplace(matrix)}\n")
        }
    }
}

fun criteriaWald(matrix: Array<DoubleArray>): List<Int> {
    println("Критерій Вальда:")
    val minInRows = matrix.map { row -> row.minOrNull()!! }.onEachIndexed { i, it ->
        println("Мінімальне значення в рядку ${i + 1}: $it")
    }
    val maxOfMins = minInRows.maxOrNull()!!
    println("Максимальне з мінімальних значень: $maxOfMins")
    return minInRows.withIndex().filter { it.value == maxOfMins }.map { it.index + 1 }
}

fun criteriaMaximax(matrix: Array<DoubleArray>): List<Int> {
    println("Критерій Максимакса:")
    val maxInRows = matrix.map { row -> row.maxOrNull()!! }.onEachIndexed { i, it ->
        println("Мінімальне значення в рядку ${i + 1}: $it")
    }
    val maxOfMaxs = maxInRows.maxOrNull()!!
    println("Максимальне з максимальних значень: $maxOfMaxs")
    return maxInRows.withIndex().filter { it.value == maxOfMaxs }.map { it.index + 1 }
}

fun criteriaHurwicz(matrix: Array<DoubleArray>, alpha: Double): List<Int> {
    println("Критерій Гурвіца (alpha = $alpha):")
    val minInRows = matrix.map { row -> row.minOrNull()!! }
    val maxInRows = matrix.map { row -> row.maxOrNull()!! }
    val hurwiczValues =
        minInRows.zip(maxInRows) { min, max -> alpha * min + (1 - alpha) * max }.onEachIndexed { i, it ->
            println("Значення Гурвіца для рядка ${i + 1}: $it (min: ${minInRows[i]}, max: ${maxInRows[i]})")
        }
    val maxHurwicz = hurwiczValues.maxOrNull()!!
    println("Максимальне значення Гурвіца: $maxHurwicz")
    return hurwiczValues.withIndex().filter { it.value == maxHurwicz }.map { it.index + 1 }
}

fun criteriaSavage(matrix: Array<DoubleArray>): List<Int> {
    println("Критерій Севіджа:")
    val maxInCols = matrix[0].indices.map { col -> matrix.map { row -> row[col] }.maxOrNull()!! }
    println("Максимальні значення в стовпцях: $maxInCols")
    val regretMatrix = matrix.map { row -> row.mapIndexed { col, value -> maxInCols[col] - value }.toDoubleArray() }
    regretMatrix.forEachIndexed { index, row ->
        println("Рядок матриці жалю ${index + 1}: ${row.joinToString(", ")}")
    }
    val maxInRows = regretMatrix.map { row -> row.maxOrNull()!! }
    val minOfMaxs = maxInRows.minOrNull()!!
    println("Мінімальне з максимальних значень: $minOfMaxs")
    return maxInRows.withIndex().filter { it.value == minOfMaxs }.map { it.index + 1 }
}

fun criteriaBayes(matrix: Array<DoubleArray>, probabilities: DoubleArray): List<Int> {
    println("Критерій Байєса:")
    val weightedValues =
        matrix.map { row -> row.zip(probabilities) { value, prob -> value * prob }.sum() }.onEachIndexed { i, it ->
            println("Зважене значення для рядка ${i + 1}: $it")
        }
    val maxWeighted = weightedValues.maxOrNull()!!
    println("Максимальне зважене значення: $maxWeighted")
    return weightedValues.withIndex().filter { it.value == maxWeighted }.map { it.index + 1 }
}

fun criteriaLaplace(matrix: Array<DoubleArray>): List<Int> {
    println("Критерій Лапласа:")
    val averageValues = matrix.map { row -> row.average() }.onEachIndexed { i, it ->
        println("Середнє значення для рядка ${i + 1}: $it")
    }
    val maxAverage = averageValues.maxOrNull()!!
    println("Максимальне середнє значення: $maxAverage")
    return averageValues.withIndex().filter { it.value == maxAverage }.map { it.index + 1 }
}