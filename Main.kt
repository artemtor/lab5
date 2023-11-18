import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Double, val y: Double)

class Triangle(val A: Point, val B: Point, val C: Point) {
    init {
        require(!areCollinear(A, B, C)) { "Точки не могут лежать на одной прямой." }
    }

    companion object {
        private fun areCollinear(A: Point, B: Point, C: Point): Boolean {
            return ((B.y - A.y) * (C.x - B.x) - (B.x - A.x) * (C.y - B.y)) == 0.0
        }
    }
}

class Circle(val center: Point, val radius: Double)

fun calculateCircleProperties(triangle: Triangle): Circle {
    val midAB = Point((triangle.A.x + triangle.B.x) / 2, (triangle.A.y + triangle.B.y) / 2)
    val midBC = Point((triangle.B.x + triangle.C.x) / 2, (triangle.B.y + triangle.C.y) / 2)

    val slopeAB = if (triangle.B.x - triangle.A.x != 0.0) (triangle.B.y - triangle.A.y) / (triangle.B.x - triangle.A.x) else Double.POSITIVE_INFINITY
    val slopeBC = if (triangle.C.x - triangle.B.x != 0.0) (triangle.C.y - triangle.B.y) / (triangle.C.x - triangle.B.x) else Double.POSITIVE_INFINITY

    val perpendicularSlopeAB = if (slopeAB != 0.0 && slopeAB != Double.POSITIVE_INFINITY) -1 / slopeAB else Double.POSITIVE_INFINITY
    val perpendicularSlopeBC = if (slopeBC != 0.0 && slopeBC != Double.POSITIVE_INFINITY) -1 / slopeBC else Double.POSITIVE_INFINITY

    val center = calculateIntersection(midAB, perpendicularSlopeAB, midBC, perpendicularSlopeBC)

    val radius = sqrt((center.x - triangle.A.x).pow(2) + (center.y - triangle.A.y).pow(2))

    return Circle(center, radius)
}

fun calculateIntersection(point1: Point, slope1: Double, point2: Point, slope2: Double): Point {
    val x = (slope1 * point1.x - slope2 * point2.x + point2.y - point1.y) / (slope1 - slope2)
    val y = slope1 * (x - point1.x) + point1.y
    return Point(x, y)
}

fun main() {
    try {
        println("Введите координаты точки A:")
        val pointA = readPoint()

        println("Введите координаты точки B:")
        val pointB = readPoint()

        println("Введите координаты точки C:")
        val pointC = readPoint()

        val triangle = Triangle(pointA, pointB, pointC)

        val circle = calculateCircleProperties(triangle)

        println("Центр окружности: (${circle.center.x}, ${circle.center.y})")
        println("Радиус окружности: ${circle.radius}")
    } catch (e: IllegalArgumentException) {
        println("Ошибка: ${e.message}")
    }
}

fun readPoint(): Point {
    print("Введите координату x: ")
    val x = readLine()!!.toDouble()

    print("Введите координату y: ")
    val y = readLine()!!.toDouble()

    return Point(x, y)
}