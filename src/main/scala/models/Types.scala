package models

object Types {
  opaque type ArtistId = String
  opaque type Genres = List[String]
  opaque type Age = Option[Int]
  opaque type Country = Option[String]
  opaque type Popularity = Option[Int]
  opaque type Followers = Option[Int]
  opaque type URI = String

  object ArtistId {
    def apply(rawId: String): ArtistId = rawId
  }

  object Age {
    def apply(rawAge: Option[Int]): Age = rawAge
  }

  object Country {
    def apply(rawCountry: Option[String]): Country = rawCountry
  }

  object Popularity {
    def apply(rawPopularity: Option[Int]): Popularity = rawPopularity

    def toDouble(popularity: Popularity): Double = popularity.getOrElse(0).toDouble
  }

  object Followers {
    def apply(rawFollowers: Option[Int]): Followers = rawFollowers
  }

  object URI {
    def apply(rawURI: String): URI = rawURI
  }

  object Genres {
    def apply(genres: List[String]): Genres = genres

    extension (g: Genres) def toList: List[String] = g
  }

}
