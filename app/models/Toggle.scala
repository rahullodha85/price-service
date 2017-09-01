package models

import play.api.libs.json._

case class Toggle(
  toggle_name:  String,
  toggle_state: Boolean
)

object Toggle {
  implicit val resultFormat = Json.format[Toggle]
}
