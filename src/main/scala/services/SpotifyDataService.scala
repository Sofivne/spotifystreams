package services

import com.github.tototoshi.csv.*
import models.Artist._
import zio.*
import zio.Console.*
import zio.stream.*
import utils.CSVReaderUtils
import models.Types._

object SpotifyDataService {

  /**
   * Create a stream of artists from a csv file with using ZIO, it return succeed if the file is found, 
   * it try to open the file and return a stream of artists
   * @param fileName the name of the file
   * @return
   */
  def createArtistStream(fileName: String): ZIO[Any, Throwable, ZStream[Any, Throwable, Option[Artist]]] =
    for {
      url <- ZIO.succeed(getClass.getClassLoader.getResource(fileName))
      source <- ZIO.fromTry(CSVReaderUtils.openCSVReader(url.getFile))
      artists <- ZIO.succeed(ZStream.fromIterator(source.toStream.drop(1).iterator))
    } yield mappingStreamAsArtist(artists)


  /**
   * Mapping a stream of list of string to a stream of artist
   * @param stream "stream of list of string"
   * @return
   */
  private def mappingStreamAsArtist(stream: ZStream[Any, Throwable, List[String]]): ZStream[Any, Throwable, Option[Artist]] =
    stream.map {
      case name :: id :: gender :: age :: country :: genres :: popularity :: followers :: uri :: Nil =>
        val _age = Age(age.toIntOption)
        val _country = Country(Option(country).filterNot(_.isEmpty))
        val _genres = Genres(genres.split(',').filterNot(_.isEmpty).toList)
        val _popularity = Popularity(popularity.toIntOption)
        val _followers = Followers(followers.toIntOption)
        Some(Artist(name, ArtistId(id), gender, _age, _country, _genres, _popularity, _followers, URI(uri)))
      case _ =>
        None
    }
}

