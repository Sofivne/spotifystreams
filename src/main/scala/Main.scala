import services.SpotifyDataService.createArtistStream
import analysis.SpotifyDataAnalysis
import models.Artist.Artist
import zio._
import zio.Console.printLine
import zio.stream.ZStream

object Main extends ZIOAppDefault {

  /**
   * Main program
   * @return  A ZIO effect that will run the program
   */
  override val run: ZIO[Any & ZIOAppArgs & Scope, Throwable, Unit] =
    for {

      // It read artists from a CSV file into a stream "artistStream"
      artistStream <- createArtistStream("Artists.csv")

      // It Collect the stream into a list "artistList"
      artistList <- artistStream.runCollect.map(_.flatten)

      // Convert the list of artists into a stream and wrapping each artist in an Option
      optionArtistStream = ZStream.fromIterable(artistList.map(Option(_)))

      // Number of artists to display, if you want change it
      nbArtists = 10

      // Calculate the age distribution, the frequency of genres and the average popularity by genre of the artists
      // and take the first 10 elements
      ageDistributionZIO: ZIO[Any, Throwable, String] =
        SpotifyDataAnalysis.calculateAgeDistribution(optionArtistStream)
          .map(_.take(nbArtists).mkString("\n"))

      genreFrequencyZIO: ZIO[Any, Throwable, String] =
        SpotifyDataAnalysis.calculateGenreFrequency(optionArtistStream)
          .map(_.take(nbArtists).mkString("\n"))

      avgPopularityByGenreZIO: ZIO[Any, Throwable, String] =
        SpotifyDataAnalysis.calculateAveragePopularityByGenre(optionArtistStream)
          .map(_.take(nbArtists).mkString("\n"))


      // get and run all analysis in parallel and print the results
      results <- ZIO.collectAllPar(
        List(ageDistributionZIO, genreFrequencyZIO, avgPopularityByGenreZIO)
      )

      (ageDistribution, genreFrequency, avgPopularityByGenre) =
        (results.head, results(1), results(2))

      _ <- printLine("Age distribution :")
      _ <- printLine(ageDistribution)
      _ <- printLine("\nFrequency of genres :")
      _ <- printLine(genreFrequency)
      _ <- printLine("\nAverage popularity by genre :")
      _ <- printLine(avgPopularityByGenre)

    } yield ()
}
