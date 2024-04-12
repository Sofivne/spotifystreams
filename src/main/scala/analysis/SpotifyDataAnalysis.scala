package analysis

import models.Artist
import zio.ZIO
import zio.stream.{ZSink, ZStream}
import analysis.Interfaces.IDataAnalysis
import models.Artist.*
import models.Types._

object SpotifyDataAnalysis extends IDataAnalysis {

  /**
   * Calculates the average popularity of artists by genre
   * @param artistStream Stream of artists
   * @return
   */
  override def calculateAveragePopularityByGenre(artistStream: ZStream[Any, Throwable, Option[Artist]]): ZIO[Any, Throwable, Map[String, Double]]
  = {
    artistStream
      .collect { case Some(artist) => artist }
      .run(ZSink.foldLeft(Map.empty[String, (Double, Int)]) { (acc, artist) =>
        artist.genres.toList.foldLeft(acc) { (acc, genre) =>
          acc.updated(genre, acc.get(genre) match {
            case Some((popularity, count)) => (popularity + Popularity.toDouble(artist.popularity), count + 1)
            case None => (Popularity.toDouble(artist.popularity), 1)
          })
        }
      })
      .map(_.map { case (genre, (popularity, count)) => (genre, popularity / count.toDouble) })
  }

  /**
   * Calculates the average popularity of artists by age
   * @param artistStream Stream of artists
   * @return
   */
  override def calculateAgeDistribution(artistStream: ZStream[Any, Throwable, Option[Artist]]): ZIO[Any, Throwable, Map[String, Int]]
  = {
    artistStream
      .collect { case Some(artist) => artist.age }
      .run(ZSink.foldLeft(Map.empty[Age, Int]) { (acc, age) =>
        acc.updated(age, acc.get(age) match {
          case Some(count) => count + 1
          case None => 1
        })
      })
      .map(_.map { case (age, count) => (age.toString, count) })
  }

  /**
   * Calculates the frequency of genres
   * @param artistStream Stream of artists
   * @return
   */
  override def calculateGenreFrequency(artistStream: ZStream[Any, Throwable, Option[Artist]]): ZIO[Any, Throwable, Map[String, Int]] = {
    artistStream
      .collect {
        case Some(artist) => artist.genres.toList
      }
      .flatMap(ZStream.fromIterable(_))
      .run(ZSink.foldLeft(Map.empty[String, Int]) { (acc, genre) =>
        acc.get(genre) match {
          case Some(count) => acc.updated(genre, count + 1)
          case None => acc.updated(genre, 1)
        }
      })
  }

}