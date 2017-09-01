package repos

import com.google.inject.{ ImplementedBy, Inject, Singleton }
import com.mongodb.casbah.{ MongoClient, MongoClientURI }
import constants.Banner
import constants.Constants._
import helpers.ConfigurationFactory
import play.Logger
import play.api.Configuration
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

class DbConnector @Inject() (configuration: Configuration, lifecycle: ApplicationLifecycle) {

  lifecycle.addStopHook {
    () =>
      {
        Logger.info("Closing mongo connection as price-sevice shutsdown")
        Future.successful(mongoClient.close())
      }
  }

  val mongoConfig = configuration.getConfig(MONGO_CONFIG).get
  val mongoClient = getMongoClient(mongoConfig)

  val (pricesCollection, localizedPricesCollection, intlRatesCollection) = initMongo

  private def initMongo = {
    val mongoDB = mongoClient.apply(mongoConfig.getString(DB_NAME).get)

    val pricesCollection = mongoDB.apply(mongoConfig.getString(DB_COLLECTION).get)
    val localizedPricesCollection = mongoDB.apply(mongoConfig.getString(LOCALIZED_PRICES_DB_COLLECTION).get)
    val intlRatesCollection = mongoDB.apply(mongoConfig.getString(DB_INTL_RATES_COLLECTION).get)

    (pricesCollection, localizedPricesCollection, intlRatesCollection)
  }

  protected def getMongoClient(mongoConfig: Configuration): MongoClient = {
    val mongoClientURI = MongoClientURI(mongoConfig.getString(MONGO_URI).get)
    MongoClient(mongoClientURI)
  }
}

@ImplementedBy(classOf[DBConnectorFactory])
trait DBConnectorFactoryLike {

  def getDb(implicit banner: Banner.Value): DbConnector

}

@Singleton
class DBConnectorFactory @Inject() (configFactory: ConfigurationFactory, lifecycle: ApplicationLifecycle) extends DBConnectorFactoryLike {

  private val bannerSpecificDbConnectors: Map[Banner.Value, DbConnector] = {
    Banner.values.toList.
      map { banner =>
        banner -> new DbConnector(configFactory.getConfig(banner), lifecycle)
      }.
      toMap
  }

  def getDb(implicit banner: Banner.Value): DbConnector = {
    bannerSpecificDbConnectors.get(banner).get
  }

}