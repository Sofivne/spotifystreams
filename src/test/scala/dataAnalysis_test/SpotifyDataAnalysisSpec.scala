package dataAnalysis_test

import zio.test.ZIOSpecDefault
import zio.test.*
import zio.test.Assertion.*
import zio.stream.ZStream
import zio.*
import models.Artist.*
import models.Types.*
import analysis.SpotifyDataAnalysis

object SpotifyDataAnalysisSpec extends ZIOSpecDefault {

  def artistStreamExample: ZStream[Any, Throwable, Option[Artist]] = ZStream(
    Some(Artist("Artist 1", ArtistId("1"), "Male", Age(Some(30)), Country(Some("USA")), Genres(List("Rock")), Popularity(Some(70)), Followers(Some(1000)), URI("uri1"))),
    Some(Artist("Artist 2", ArtistId("2"), "Female", Age(Some(25)), Country(Some("UK")), Genres(List("Pop")), Popularity(Some(80)), Followers(Some(2000)), URI("uri2"))),
    Some(Artist("Artist 3", ArtistId("3"), "Male", Age(Some(35)), Country(Some("Canada")), Genres(List("Rock", "Jazz")), Popularity(Some(60)), Followers(Some(1500)), URI("uri3")))
  )

  def spec: Spec[TestEnvironment with Scope, Any] = suite("SpotifyDataAnalysisSpec")(
    
    test("calculateAveragePopularityByGenre should calculate correct averages") {
      for {
        result <- SpotifyDataAnalysis.calculateAveragePopularityByGenre(artistStreamExample)
      } yield assertTrue(
        result("Rock") == 65.0 &&
          result("Pop") == 80.0 &&
          result("Jazz") == 60.0
      )
    },
    
    test("calculateGenreFrequency should calculate correct genre frequency") {
      for {
        result <- SpotifyDataAnalysis.calculateGenreFrequency(artistStreamExample)
      } yield assertTrue(
        result("Rock") == 2 &&
          result("Pop") == 1 &&
          result("Jazz") == 1
      )
    }
  )
}