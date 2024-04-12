package models_test

import zio.test.ZIOSpecDefault
import zio.test.*
import zio.test.Assertion.*
import models.Artist.*
import models.Types._

object ArtistSpec extends ZIOSpecDefault {

    def spec: Spec[Any, Nothing] = suite("ExampleSpec")(
      test ("Artist should be created with all fields") {
        val artist = Artist(
          name = "Artist Name",
          ID = ArtistId("1"),
          gender = "male",
          age = Age(Some(25)),
          country = Country(Some("USA")),
          genres = Genres(List("Rock")),
          popularity = Popularity(Some(50)),
          followers = Followers(Some(1000)),
          URI = URI("uri1")
        )
        assertTrue(artist.name == "Artist Name")
        assertTrue(artist.ID == ArtistId("1"), artist.gender == "male", artist.age == Age(Some(25)), artist.country == Country(Some("USA")), artist.genres == Genres(List("Rock")), artist.popularity == Popularity(Some(50)), artist.followers == Followers(Some(1000)), artist.URI == URI("uri1"))
      },
      test("One of elements of Artist has None"){
        val artist = Artist(
          name = "Artist Name",
          ID = ArtistId("1"),
          gender = "M",
          age = Age(Some(25)),
          country = Country(None),
          genres = Genres(List("Rock")),
          popularity = Popularity(None),
          followers = Followers(Some(1000)),
          URI = URI("uri1")
        )

        assertTrue(artist.country == Country(None))
      }
  )


}
