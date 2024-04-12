package analysis.Interfaces

import models.Artist
import models.Artist._
import zio.ZIO
import zio.stream.ZStream

trait IDataAnalysis {
  def calculateAveragePopularityByGenre(artistStream: ZStream[Any, Throwable, Option[Artist]]): ZIO[Any, Throwable, Map[String, Double]]
  def calculateAgeDistribution(artistStream: ZStream[Any, Throwable, Option[Artist]]): ZIO[Any, Throwable, Map[String, Int]]
  def calculateGenreFrequency(artistStream: ZStream[Any, Throwable, Option[Artist]]): ZIO[Any, Throwable, Map[String, Int]]
}
