package models
import Types._
object Artist {

  /**
   * Case class for modeling an artist with the following attributes (all of them are optional and use the opaque types defined above):
   * 
   * @param name "name" of the artist
   * @param ID "id" of the artist
   * @param gender "gender" of the artist
   * @param age "age" of the artist
   * @param country "country" of the artist
   * @param genres "genres" of the artist 
   * @param popularity "popularity" of the artist
   * @param followers "followers" of the artist
   * @param URI "uri" of the artist
   */
  final case class Artist(
                           name: String,
                           ID: ArtistId,
                           gender: String,
                           age: Age,
                           country: Country,
                           genres: Genres,
                           popularity: Popularity,
                           followers: Followers,
                           URI: URI
                         )
}