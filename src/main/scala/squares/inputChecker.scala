package squares

object inputChecker {

  import logic.Square
  import io.Source

  val INVALID_INPUT_EXCEPTION = new Exception("invalid input")

  /**
    *
    * @param fileName of input file. Defaults to input.txt in root
    * @return Either set of results or Exception on failure. Set is empty if none
    */
  def apply(fileName: String): Either[Throwable, Set[Square]] = {
    try {
      val rawData = Source.fromFile(fileName).getLines()
      val data: Set[Square] = rawData.zipWithIndex.map {
        case (s, i) => Square(i, s.split(" ").map(_.toInt))
      }.toSet
      if ((data.size != 12) &&
        (!data.forall(s => (s.corners.length == 4) &&
          s.corners.forall(i => i >= 0 && i < 10))))
        throw INVALID_INPUT_EXCEPTION
      Right(data)
    } catch {
      case e =>
        Left(e)
    }
  }

}
