import com.mongodb.DBObject
import com.mongodb.casbah.commons.{Imports, MongoDBObject}
import com.mongodb.casbah.{MongoClient, MongoClientURI}

object CanadianPricing {

  def convertToLocal(usCents: Int, country: String): Int = {
    if (country == "US") return usCents
    (usCents * 1.5).intValue
  }

  def transform(usPrice: DBObject, country: String): Imports.DBObject = {
    val salePriceUSD: Int = usPrice.get("markdown_price").asInstanceOf[Int]
    val listPriceUSD: Int = usPrice.get("list_price").asInstanceOf[Int]
    val privateSalePriceUSD: Int = usPrice.get("privatesale_price").asInstanceOf[Int]

    val salePriceLocal = convertToLocal(salePriceUSD, country)
    val listPriceLocal = convertToLocal(listPriceUSD, country)
    val privateSalePriceLocal = convertToLocal(privateSalePriceUSD, country)

    return MongoDBObject(
      "product_code" -> usPrice.get("product_code"),
      "upc_code" -> usPrice.get("upc_code"),
      "country"  -> country,
      "list_price" -> listPriceLocal,
      "sale_price" -> salePriceLocal,
      "privatesale_price" -> privateSalePriceLocal,
      "is_deleted" -> "N",
      "final_sale_status" -> usPrice.get("final_sale_status"),
      "modified_on" -> usPrice.get("modified_on"),
      "price_status" -> usPrice.get("price_status")
    )
  }

  def setupCollectionWithTestData(connectionString: String): Unit = {
    println("Setting up mock canadian prices...")

    val mongoClient = MongoClient(MongoClientURI(connectionString))
    val mongoDB = mongoClient.apply("saks_services")
    val usPricesCollection = mongoDB.apply("prices")
    val priceBookCollection = mongoDB.apply("price_book")
    priceBookCollection.remove(MongoDBObject()) //removes all existing entries
    val usPricesIterator = usPricesCollection.iterator
    while (usPricesIterator.hasNext) {
      val usPrice = usPricesIterator.next()
      val cadPrice = transform(usPrice, "CA")
      priceBookCollection.insert(cadPrice)
      val newUsPrice = transform(usPrice, "US")
      priceBookCollection.insert(newUsPrice)
    }
    priceBookCollection.createIndex(MongoDBObject("product_code" -> 1, "country" -> 1))
    priceBookCollection.createIndex(MongoDBObject("upc_code" -> 1, "country" -> 1))

    mongoClient.close()

  }
}
