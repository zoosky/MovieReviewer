package persistence

import models._
import sorm.{Entity, Instance}


object Db extends Instance(
  entities = Set(Entity[Movie](), Entity[Review]()),
  url = "jdbc:h2:mem:test"
)
