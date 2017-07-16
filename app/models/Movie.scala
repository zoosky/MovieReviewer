package models

case class Movie(title: String, director: String, description: String, releaseYear: Int, reviews: Set[Review])
