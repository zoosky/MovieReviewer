package controllers

import javax.inject.{Inject, Singleton}

import models._
import persistence.Db
import io.circe.generic.auto._
import io.circe.parser._
import cats.implicits._
import io.circe.syntax._
import play.api.mvc.{Action, Controller}

@Singleton
class ReviewManager @Inject() extends Controller {

  def getReviews(title: String) = Action {
    Db.query[Movie].whereEqual("title", title)
      .fetchOne()
      .map(movie => Ok(movie.reviews.asJson.noSpaces))
      .getOrElse(BadRequest("Movie does not exist"))
  }

  def addReview(title: String) = Action { request =>
    val reviewJson = request.body.asJson.map(_.toString).getOrElse("")

    val decodedReview = decode[Review](reviewJson).toOption

    def isValidReview(review: Review): Boolean =
      review.rating >= 0 && review.rating <= 10 &&
        review.title.trim().length() > 0 &&
        review.title.trim().length() < 20 &&
        review.message.trim().length() < 200

    Db.query[Movie]
      .whereEqual("title", title)
      .fetchOne()
      .flatMap(movie => {
        decodedReview.filter(isValidReview)
          .map(Db.save)
          .map(review =>
            Db.save(movie.copy(reviews = movie.reviews + review))
          )
      })
      .map(_ => Ok("Review created"))
      .getOrElse(BadRequest("Could not create Review"))
  }

  def deleteReview(title: String) = Action { request =>
    Db.query[Review].whereEqual("title", title)
      .fetchOne()
      .map(review => Db.delete(review))
      .map(_ => Ok("Review deleted"))
      .getOrElse(BadRequest("Could not find Review to be deleted"))
  }

  def editReview(title: String) = Action { request =>
    val reviewJson = request.body.asJson.map(_.toString).getOrElse("")

    val decodedReview = decode[Review](reviewJson).toOption

    Db.query[Review]
      .whereEqual("title", title)
      .fetchOne()
      .flatMap(oldReview =>
        decodedReview.map(newReview => Db.save(oldReview.copy(
          title = newReview.title,
          rating = newReview.rating,
          message = newReview.message))))
      .map(_ => Ok("Review updated"))
      .getOrElse(BadRequest("Could not update Review"))
  }
}
