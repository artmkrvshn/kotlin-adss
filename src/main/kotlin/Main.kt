fun main() {
    val matrix = arrayOf(
        doubleArrayOf(6.0, 2.0, -4.0, -2.0),
        doubleArrayOf(4.0, 2.0, 2.0, 2.0),
        doubleArrayOf(6.0, 6.0, 8.0, 4.0)
    )

    val probabilities = doubleArrayOf(0.25, 0.25, 0.25, 0.25)
    val alpha = 0.5

    println("Wald's criterion: ${criteriaWald(matrix)}")
    println("Maximax criterion: ${criteriaMaximax(matrix)}")
    println("Hurwicz's criterion: ${criteriaHurwicz(matrix, alpha)}")
    println("Savage's criterion: ${criteriaSavage(matrix)}")
    println("Bayes' criterion: ${criteriaBayes(matrix, probabilities)}")
    println("Laplace's criterion: ${criteriaLaplace(matrix)}")
}

fun criteriaWald(matrix: Array<DoubleArray>): List<Int> {
    val minInRows = matrix.map { row -> row.minOrNull()!! }
    val maxOfMins = minInRows.maxOrNull()!!
    return minInRows.withIndex().filter { it.value == maxOfMins }.map { it.index + 1 }
}

fun criteriaMaximax(matrix: Array<DoubleArray>): List<Int> {
    val maxInRows = matrix.map { row -> row.maxOrNull()!! }
    val maxOfMaxs = maxInRows.maxOrNull()!!
    return maxInRows.withIndex().filter { it.value == maxOfMaxs }.map { it.index + 1 }
}

fun criteriaHurwicz(matrix: Array<DoubleArray>, alpha: Double): List<Int> {
    val minInRows = matrix.map { row -> row.minOrNull()!! }
    val maxInRows = matrix.map { row -> row.maxOrNull()!! }
    val hurwiczValues = minInRows.zip(maxInRows) { min, max -> alpha * min + (1 - alpha) * max }
    val maxHurwicz = hurwiczValues.maxOrNull()!!
    return hurwiczValues.withIndex().filter { it.value == maxHurwicz }.map { it.index + 1 }
}

fun criteriaSavage(matrix: Array<DoubleArray>): List<Int> {
    val maxInCols = matrix[0].indices.map { col -> matrix.map { row -> row[col] }.maxOrNull()!! }
    val regretMatrix = matrix.map { row -> row.mapIndexed { col, value -> maxInCols[col] - value }.toDoubleArray() }
    val maxInRows = regretMatrix.map { row -> row.maxOrNull()!! }
    val minOfMaxs = maxInRows.minOrNull()!!
    return maxInRows.withIndex().filter { it.value == minOfMaxs }.map { it.index + 1 }
}

fun criteriaBayes(matrix: Array<DoubleArray>, probabilities: DoubleArray): List<Int> {
    val weightedValues = matrix.map { row -> row.zip(probabilities) { value, prob -> value * prob }.sum() }
    val maxWeighted = weightedValues.maxOrNull()!!
    return weightedValues.withIndex().filter { it.value == maxWeighted }.map { it.index + 1 }
}

fun criteriaLaplace(matrix: Array<DoubleArray>): List<Int> {
    val averageValues = matrix.map { row -> row.average() }
    val maxAverage = averageValues.maxOrNull()!!
    return averageValues.withIndex().filter { it.value == maxAverage }.map { it.index + 1 }
}