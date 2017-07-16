package controllers

import javax.inject.{Inject, Singleton}

import models.Movie
import persistence.Db
import io.circe.generic.auto._
import io.circe.parser._
import cats.implicits._
import io.circe.syntax._
import play.api.mvc.{Action, Controller}

@Singleton
class MovieManager @Inject() extends Controller {

  def searchMovie(query: String) = Action {
    val suggestions: List[Movie] = Db.query[Movie].whereLike("title",s"%${query.toLowerCase()}%")
      .fetch()
      .toList

    Ok(suggestions.asJson.noSpaces)
  }

  def getAllMovies = Action {
    val movies: List[Movie] = Db.query[Movie].fetch().toList

    Ok(movies.asJson.noSpaces)
  }

  def addMovie() = Action { request =>
    val movieJson = request.body.asJson.map(_.toString).getOrElse("")

    val decodedMovie = decode[Movie](movieJson).toOption

    def isValidMovie(movie: Movie): Boolean =
      movie.title.trim().length() > 0 &&
        movie.title.trim().length() < 20 &&
        movie.releaseYear > 1890

    decodedMovie
      .filter(isValidMovie)
      .map(movie => Db.save(movie))
      .map(_ => Ok("Movie created"))
      .getOrElse(BadRequest("Could not create Movie"))
  }

  def deleteMovie(title: String) = Action { request =>
    Db.query[Movie].whereEqual("title", title)
      .fetchOne()
      .map(movie => Db.delete(movie))
      .map(_ => Ok("Movie deleted"))
      .getOrElse(BadRequest("Could not find Movie to be deleted"))
  }

  def editMovie(title: String) = Action { request =>
    val movieJson = request.body.asJson.map(_.toString).getOrElse("")

    val decodedMovie = decode[Movie](movieJson).toOption

    Db.query[Movie]
      .whereEqual("title", title)
      .fetchOne()
      .flatMap(oldMovie =>
        decodedMovie.map(newReview => Db.save(oldMovie.copy(
          title = newReview.title,
          director = newReview.director,
          description = newReview.description,
          releaseYear = newReview.releaseYear,
          reviews = newReview.reviews))))
      .map(_ => Ok("Movie updated"))
      .getOrElse(BadRequest("Could not update Movie"))
  }
}
