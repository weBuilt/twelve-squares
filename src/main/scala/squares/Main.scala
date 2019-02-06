package squares

object Main {

  case class Square(pos: Int, corners: Array[Int]) {
    def apply(corner: Int) = corners(corner - 1)

    override def toString: String = s"${corners.mkString(" ")}"
  }

  def appropriateFours(data: Set[Square]): Set[List[Square]] =
    for {
      _1 <- data
      _2 <- data - _1
      _4 <- data - _1 - _2
      _5 <- data - _1 - _2 - _4
      if _1(4) + _2(3) + _4(2) + _5(1) == 10
    } yield List(_1, _2, _4, _5)

  def main(args: Array[String]): Unit = {
    val fileName = args.headOption.getOrElse("input.txt")
    val rawData = io.Source.fromFile(fileName).getLines
    val data: Set[Square] = rawData.zipWithIndex.map {
      case (s, i) => Square(i, s.split(" ").map(_.toInt))
    }.toSet

    val fours = appropriateFours(data)

    val middleColumns = for {
      four <- fours
      (_1, _2, _4, _5) = (four(0), four(1), four(2), four(3))
      if _1(2) + _2(1) <= 10
      remaining = fours.filter(l =>
        four.toSet.intersect(l.toSet).isEmpty)
      anotherFour <- remaining
      (_8, _9, _11, _12) = (anotherFour(0), anotherFour(1), anotherFour(2), anotherFour(3))
      if _4(4) + _5(3) + _8(2) + _9(1) == 10
      if _11(4) + _12(3) <= 10
    } yield List(_1, _2, _4, _5, _8, _9, _11, _12)

    val solutions = for {
      used <- middleColumns
      remaining = data -- used
      (_1, _2, _4, _5, _8, _9, _11, _12) = (used(0), used(1), used(2), used(3), used(4), used(5), used(6), used(7))

      _3 <- remaining
      if _1(3) + _3(2) + _4(1) <= 10

      _6 <- remaining - _3
      if _2(4) + _5(2) + _6(1) <= 10

      _7 <- remaining - _3 - _6
      if _3(3) + _7(1) <= 10
      if _3(4) + _4(3) + _7(2) + _8(1) == 10
      if _7(4) + _8(3) + _11(1) <= 10

      _10 <- remaining - _3 - _6 - _7
      if _6(4) + _10(2) <= 10
      if _5(4) + _6(3) + _9(2) + _10(1) == 10
      if _9(4) + _10(3) + _12(2) <= 10
    } yield List(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12)

    println(solutions
      .map(_.mkString("\n"))
      .mkString("\n"))
  }

}
