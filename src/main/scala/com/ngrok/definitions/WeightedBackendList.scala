package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[WeightedBackendList]] resource.
  *
  * @constructor create a new WeightedBackendList.
  * @param backends the list of all Weighted backends on this account
  * @param uri URI of the Weighted backends list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class WeightedBackendList(
  backends: List[WeightedBackend],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object WeightedBackendList {
  implicit val encodeWeightedBackendList: io.circe.Encoder[WeightedBackendList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("backends", value.backends.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeWeightedBackendList: io.circe.Decoder[WeightedBackendList] = (c: io.circe.HCursor) =>
    for {
      backends    <- c.downField("backends").as[List[WeightedBackend]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield WeightedBackendList(
      backends,
      uri,
      nextPageUri
    )
}
