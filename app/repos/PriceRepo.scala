package repos

import com.google.inject.Inject
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.conversions.scala.{ RegisterConversionHelpers, RegisterJodaTimeConversionHelpers }
import constants.Constants._
import constants.{ Banner, Constants }
import metrics.StatsDClientLike
import models.PriceModel
import play.Logger
import play.api.libs.json.Json

class PriceRepo @Inject() (
    statsDClient:       StatsDClientLike,
    dbConnectorFactory: DBConnectorFactoryLike
) extends IPriceRepo {

  RegisterConversionHelpers()
  RegisterJodaTimeConversionHelpers()

  override def getPriceByProductCode(productCode: String, countryCode: String)(implicit banner: Banner.Value): Seq[PriceModel] = {
    Logger.info(s"Getting price data by product code: ${productCode}")

    val price = statsDClient.time("getPriceByProductCode_query") {
      dbConnectorFactory.getDb.pricesCollection.find(MongoDBObject(Constants.PRODUCT_CODE -> productCode)).toList
    }

    price.map(r => Json.parse(r.toString).as[PriceModel])
  }

  override def getPriceByProductCodes(productCodes: Seq[String], countryCode: String)(implicit banner: Banner.Value): Seq[PriceModel] = {
    Logger.info(s"Getting price data by product codes: ${productCodes}")
    statsDClient.time("getPriceByProductCodes") {

      val prices = statsDClient.time("getPriceByProductCodes_query") {
        val query = PRODUCT_CODE $in productCodes
        val excludeIDQuery = MongoDBObject("_id" -> 0)
        dbConnectorFactory.getDb.pricesCollection.find(query, excludeIDQuery).toList
      }

      prices.map(r => Json.parse(r.toString).as[PriceModel])
    }
  }

  override def getPriceByUpc(upcCode: String, countryCode: String)(implicit banner: Banner.Value): Option[PriceModel] = {
    Logger.info(s"Getting price data by upc code: ${upcCode}")
    statsDClient.time("getPriceByUpc_query") {
      dbConnectorFactory.getDb.pricesCollection.findOne(MongoDBObject(Constants.UPC_CODE -> upcCode))
        .map(r => Json.parse(r.toString).as[PriceModel])
    }
  }

}
