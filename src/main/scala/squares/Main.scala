package squares

object Main {

 import logic.solve

  def main(args: Array[String]): Unit = {
    val fileName = args.headOption.getOrElse("input.txt")
    inputChecker(fileName) match {
      case Right(data) =>
        println(solve(data)
          .map(_.mkString("\n"))
          .mkString("\n\n"))
      case Left(e) =>
        e match {
          case _ : java.io.FileNotFoundException =>
            println("file not found\nplease use correct path or put input.txt into root")
          case inputChecker.INVALID_INPUT_EXCEPTION =>
            println("invalid input\nmust be 12 lines of 4 digits, separated by space")
          case _ => e.printStackTrace()
        }
    }
  }

}
