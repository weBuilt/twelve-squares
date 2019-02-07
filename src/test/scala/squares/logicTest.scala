package squares

import org.scalacheck._
import org.scalacheck.Prop.forAll
import squares.logic._

object logicTest extends Properties("logic") {

  val cornersGen: Gen[Array[Int]] =
    Gen.pick(4, 0 to 9).map(_.toArray)

  implicit val setOfSquaresGen: Gen[Set[Square]] =
    Gen.listOfN(12, cornersGen).map(lai =>
      lai.zipWithIndex.map {
        case (a, i) => Square(i, a)
      }.toSet)

  implicit val arbitrarySetOfSquares: Arbitrary[Set[Square]] = Arbitrary(setOfSquaresGen)

  property("all correct inputs should return either empty set or set of correct results") =
    forAll { (s: Set[Square]) =>
      val solutions = solve(s)
      solutions.isEmpty ||
        solutions.forall { list =>
          val l = Square(99, Array(0, 0, 0, 0)) :: list
          (l(1)(2) + l(2)(1) <= 10) &&
            (l(1)(3) + l(3)(2) + l(4)(1) <= 10) &&
            (l(1)(4) + l(2)(3) + l(4)(2) + l(5)(1) == 10) &&
            (l(2)(4) + l(5)(2) + l(6)(1) <= 10) &&
            (l(3)(3) + l(7)(1) <= 10) &&
            (l(3)(4) + l(4)(3) + l(7)(2) + l(8)(1) == 10) &&
            (l(4)(4) + l(5)(3) + l(8)(2) + l(9)(1) == 10) &&
            (l(5)(4) + l(6)(3) + l(9)(2) + l(10)(1) == 10) &&
            (l(6)(4) + l(10)(2) <= 10) &&
            (l(7)(4) + l(8)(3) + l(11)(1) <= 10) &&
            (l(8)(4) + l(9)(3) + l(11)(2) + l(12)(1) == 10) &&
            (l(9)(4) + l(10)(3) + l(12)(2) <= 10) &&
            (l(11)(4) + l(12)(3) <= 10)
        }
    }
}
