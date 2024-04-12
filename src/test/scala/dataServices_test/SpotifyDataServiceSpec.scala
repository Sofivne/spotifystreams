package dataServices_test

import zio.test.ZIOSpecDefault
import zio.test.*
import zio.test.Assertion.*
import zio.stream.*
import zio.*
import services.SpotifyDataService
import models.Artist.*
import models.Types.*
import com.github.tototoshi.csv.CSVReader

object SpotifyDataServiceSpec extends ZIOSpecDefault {

  def spec: Spec[TestEnvironment with Scope, Any] = suite("SpotifyDataServiceSpec")(
    test("createArtistStream should succeed with valid file") {
      val fileName = "Artists.csv"
      for {
        artistStream <- SpotifyDataService.createArtistStream(fileName).either
      } yield assertTrue(artistStream.isRight)
    },
    test("createArtistStream should fail with non-existent file") {
      val fileName = "non_existent_file.csv"
      for {
        artistStream <- SpotifyDataService.createArtistStream(fileName).either
      } yield assertTrue(artistStream.isLeft)
    },
    test("createArtistStream should correctly map CSV rows to Artist objects") {
      val fileName = "Artists.csv"
      for {
        artistStream <- SpotifyDataService.createArtistStream(fileName)
        artists <- artistStream.runCollect
      } yield assertTrue(artists.forall(_.isDefined) && artists.nonEmpty)
    }
  )
}
