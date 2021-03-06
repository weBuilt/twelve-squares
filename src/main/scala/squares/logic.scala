package squares

object logic {
  case class Square(initialPos: Int, corners: Array[Int]) {
    def apply(corner: Int): Int = corners(corner - 1)

    override def toString: String = s"${corners.mkString(" ")}"
  }

  /**       -------------
    *       |1   2|1   2|
    *       |  1  |  2  |
    *       |3   4|3   4|
    * -------------------------
    * |1   2|1   2|1   2|1   2|
    * |  3  |  4  |  5  |  6  |
    * |3   4|3   4|3   4|3   4|
    * -------------------------
    * |1   2|1   2|1   2|1   2|
    * |  7  |  8  |  9  | 10  |
    * |3   4|3   4|3   4|3   4|
    * -------------------------
    *       |1   2|1   2|
    *       | 11  | 12  |
    *       |3   4|3   4|
    *       -------------
    *
    * number of square in center, number of corner in corner
    *
    * @param data Set of 12 squares
    * @return Set of possible solutions. Empty if none
    */

  def solve(data: Set[Square]): Set[List[Square]] =
    if ( !{
      val sum = data.foldLeft(0)((s, sq) =>
        s + sq.corners.sum)
      (sum >= 50) && (sum <= 202)
    }) Set[List[Square]]()                           //filter all data that have no solution cause of sum of numbers
    else {
      val allFoursThatSumsTo10: Set[List[Square]] =           //find all fours of squares that can form bigger square
        for {                                                 //sum of 4 corners in center must be 10
          _1 <- data                                          //|1   2|1   2|
          _2 <- data - _1                                     //| _1  | _2  |
          if _1(2) + _2(1) <= 10                              //|3   4|3   4|
          _4 <- data - _1 - _2                                //-------------
          _5 <- data - _1 - _2 - _4                           //|1   2|1   2|
          if _1(4) + _2(3) + _4(2) + _5(1) == 10              //| _4  |  _5 |
          if _4(4) + _5(3) <= 10                              //|3   4|3   4|
          if _1(3) + _4(1) <= 10                              //
          if _2(4) + _5(2) <= 10                              //and checking sum of 2 adjacent corners are <= 10
       } yield List(_1, _2, _4, _5)                           //to make strictest filter

      val allEightsThatFitsToMiddle: Set[List[Square]] = for {//find all pairs of big squares that can form middle colons
        four <- allFoursThatSumsTo10                          //of result and check sums of _4(3) & _8(1) and _5(4)&_9(2)
        (_1, _2, _4, _5) = (four(0), four(1), four(2), four(3))// so they can form tens in future
        remaining = allFoursThatSumsTo10.filter(l =>          //    ---------
          four.toSet.intersect(l.toSet).isEmpty)              //    | 1 | 2 |
        anotherFour <- remaining                              //-----------------
        (_8, _9, _11, _12) = (anotherFour(0),                 //|///| 4 | 5 |///|
                              anotherFour(1),                 //-----------------
                              anotherFour(2),                 //|///| 8 | 9 |///|
                              anotherFour(3))                 //-----------------
        if _4(4) + _5(3) + _8(2) + _9(1) == 10                //    |11 | 12|
        if _4(3) + _8(1) <= 10                                //    ---------
        if _5(4) + _9(2) <= 10
     } yield List(_1, _2, _4, _5, _8, _9, _11, _12)

      val solutions = for {                           //for all eights of squares that forms middle colons
        used <- allEightsThatFitsToMiddle             //use unused 4 squares in all combinations and simply
        remaining = data -- used                      //check correctness of sums
        (_1, _2, _4, _5,
        _8, _9, _11, _12) = (
          used(0), used(1), used(2), used(3),
          used(4), used(5), used(6), used(7))

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

      solutions
    }

}
