import kotlin.test.Test
import kotlin.test.assertEquals

class Test {

    @Test
    fun inverseTest1() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(5.0, -3.0, 7.0),
                doubleArrayOf(-1.0, 4.0, 3.0),
                doubleArrayOf(6.0, -2.0, 5.0)
            )
        )
        val expected = Matrix(
            arrayOf(
                doubleArrayOf(-0.280, -0.011, 0.398),
                doubleArrayOf(-0.247, 0.183, 0.237),
                doubleArrayOf(0.237, 0.086, -0.183)
            )
        )

        val result = matrix.inverse()

        equals(result, expected, 0.001)
    }

    @Test
    fun inverseTest2() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(6.0, 2.0, 5.0),
                doubleArrayOf(-3.0, 4.0, -1.0),
                doubleArrayOf(1.0, 4.0, 3.0)
            )
        )
        val expected = Matrix(
            arrayOf(
                doubleArrayOf(0.5, 0.437, -0.687),
                doubleArrayOf(0.25, 0.406, -0.281),
                doubleArrayOf(-0.5, -0.687, 0.937)
            )
        )

        val result = matrix.inverse()

        equals(result, expected, 0.001)
    }

    @Test
    fun inverseTest3() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(2.0, -1.0, 3.0),
                doubleArrayOf(-1.0, 2.0, 2.0),
                doubleArrayOf(1.0, 1.0, 1.0)
            )
        )
        val expected = Matrix(
            arrayOf(
                doubleArrayOf(0.0, -0.333, 0.667),
                doubleArrayOf(-0.25, 0.083, 0.583),
                doubleArrayOf(0.25, 0.25, -0.25)
            )
        )

        val result = matrix.inverse()

        equals(result, expected, 0.001)
    }


    @Test
    fun rankTest1() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(2.0, 4.0, 6.0, 8.0)
            )
        )
        val expected = 1

        val result = matrix.rank()

        assertEquals(expected, result)
    }


    @Test
    fun rankTest2() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(2.0, 5.0, 4.0),
                doubleArrayOf(-3.0, 1.0, -2.0),
                doubleArrayOf(-1.0, 6.0, 2.0)
            )
        )
        val expected = 2

        val result = matrix.rank()

        assertEquals(expected, result)
    }


    @Test
    fun rankTest3() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(1.0, 2.0),
                doubleArrayOf(3.0, 6.0),
                doubleArrayOf(5.0, 10.0),
                doubleArrayOf(4.0, 8.0)
            )
        )
        val expected = 1

        val result = matrix.rank()

        assertEquals(expected, result)
    }


    @Test
    fun rankTest4() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(6.0, 2.0, 5.0),
                doubleArrayOf(-3.0, 4.0, -1.0),
                doubleArrayOf(1.0, 4.0, 3.0)
            )
        )
        val expected = 3

        val result = matrix.rank()

        assertEquals(expected, result)
    }


    @Test
    fun rankTest5() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(-1.0, 5.0, 4.0),
                doubleArrayOf(-2.0, 7.0, 5.0),
                doubleArrayOf(-3.0, 4.0, 1.0)
            )
        )
        val expected = 2

        val result = matrix.rank()

        assertEquals(expected, result)
    }


    @Test
    fun rankTest6() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(-2.0, 5.0, -1.0, 3.0),
                doubleArrayOf(2.0, 4.0, 6.0, 8.0),
                doubleArrayOf(-1.0, 7.0, 2.0, 7.0)
            )
        )
        val expected = 2

        val result = matrix.rank()

        assertEquals(expected, result)
    }

    @Test
    fun solveTest1() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(5.0, -3.0, 7.0),
                doubleArrayOf(-1.0, 4.0, 3.0),
                doubleArrayOf(6.0, -2.0, 5.0)
            )
        )
        val vectorB = doubleArrayOf(13.0, 13.0, 12.0)
        val result = doubleArrayOf(1.0, 2.0, 2.0)

        val solved = matrix.solve(vectorB)

        assertEquals(result.size, solved.size)
        for (i in solved.indices) {
            assertEquals(result[i], solved[i], 0.001)
        }
    }

    @Test
    fun solveTest2() {
        val matrix = Matrix(
            arrayOf(
                doubleArrayOf(6.0, 2.0, 5.0),
                doubleArrayOf(-3.0, 4.0, -1.0),
                doubleArrayOf(1.0, 4.0, 3.0)
            )
        )
        val vectorB = doubleArrayOf(1.0, 6.0, 6.0)
        val result = doubleArrayOf(-1.0, 1.0, 1.0)

        val solved = matrix.solve(vectorB)

        assertEquals(result.size, solved.size)
        for (i in solved.indices) {
            assertEquals(result[i], solved[i], 0.001)
        }
    }

    private fun equals(actual: Matrix, expected: Matrix, tolerance: Double = 0.001) {
        assertEquals(actual.rows, expected.rows)
        assertEquals(actual.columns, expected.columns)
        for (i in 0 until actual.rows) {
            for (j in 0 until actual.columns) {
                assertEquals(actual.data[i][j], expected.data[i][j], tolerance)
            }
        }
    }
}