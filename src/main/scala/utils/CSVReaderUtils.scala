package utils

import com.github.tototoshi.csv.*
import java.io.FileNotFoundException
import scala.util.{Try, Success, Failure}

object CSVReaderUtils {

  /**
   * Try to open a file with CSVReader and return a Try[CSVReader]
   * @param filename
   * @return
   */
  def openCSVReader(filename: String): Try[CSVReader] = {
    Try(CSVReader.open(filename)) match {
      case Success(reader) => Success(reader)
      case Failure(e) => Failure(new FileNotFoundException(s"Le fichier $filename n'existe pas"))
    }
  }
}
