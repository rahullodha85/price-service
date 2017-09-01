package repos

import com.google.inject.Inject
import com.mongodb.casbah.commons.MongoDBObject
import constants.{ Banner, Constants }
import mappers.PriceModelMapper
import metrics.StatsDClientLike
import models.PriceModel
import play.Logger

class PriceBookRepo @Inject() (
    statsDClient:       StatsDClientLike,
    dbConnectorFactory: DBConnectorFactoryLike
) extends IPriceRepo {
  override def getPriceByProductCode(productCode: String, countryCode: String)(implicit banner: Banner.Value): Seq[PriceModel] = {
    Logger.debug(s"Getting price data by product code: ${productCode} with country ${countryCode}")

    val localizedPrices = statsDClient.time("getPriceByProductCode_query") {
      val db: DbConnector = dbConnectorFactory.getDb(banner)
      db.localizedPricesCollection
        .find(MongoDBObject(Constants.PRODUCT_CODE -> productCode, Constants.COUNTRY -> countryCode))
        .sort(MongoDBObject(Constants.UPC_CODE -> 1)).toList
    }

    localizedPrices.map(r => PriceModelMapper.fromLocalizedPriceMongoDocument(r))
  }

  override def getPriceByProductCodes(productCodes: Seq[String], countryCode: String)(implicit banner: Banner.Value): Seq[PriceModel] = {
    Logger.info(s"Getting price data by product codes: ${productCodes} with country ${countryCode}")

    val localizedPrices = statsDClient.time("getPriceByProductCodes_query") {
      dbConnectorFactory.getDb(banner).localizedPricesCollection
        .find(MongoDBObject(Constants.PRODUCT_CODE -> MongoDBObject("$in" -> productCodes), Constants.COUNTRY -> countryCode))
        .sort(MongoDBObject(Constants.UPC_CODE -> 1)).toList
    }

    localizedPrices.map(r => PriceModelMapper.fromLocalizedPriceMongoDocument(r))
  }

  override def getPriceByUpc(upcCode: String, countryCode: String)(implicit banner: Banner.Value): Option[PriceModel] = {
    Logger.debug(s"Getting price data by upc: ${upcCode} with country ${countryCode}")

    statsDClient.time("getPriceByUPC_query") {
      dbConnectorFactory.getDb.localizedPricesCollection
        .findOne(MongoDBObject(Constants.UPC_CODE -> upcCode, Constants.COUNTRY -> countryCode))
        .map(r => PriceModelMapper.fromLocalizedPriceMongoDocument(r))
    }

  }
}
