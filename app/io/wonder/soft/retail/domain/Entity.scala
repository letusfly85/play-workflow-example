package io.wonder.soft.retail.domain

trait Entity

object Entity {
  import org.joda.time.DateTime
  import org.joda.time.format.DateTimeFormat
  import play.api.libs.json.Reads._
  import play.api.libs.json._

  val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

  implicit def jodaDateReads = Reads[DateTime](js =>
    js.validate[String].map[DateTime](dtString =>
      DateTime.parse(dtString, DateTimeFormat.forPattern(dateFormat))
    )
  )

  implicit def jodaDateWrites: Writes[DateTime] = new Writes[DateTime] {
    def writes(d: DateTime): JsValue = JsString(d.toString())
  }
}
