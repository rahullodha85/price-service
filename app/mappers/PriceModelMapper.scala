package mappers

import com.mongodb.DBObject
import models.PriceModel

object PriceModelMapper {
  def fromLocalizedPriceMongoDocument(dbObject: DBObject): PriceModel = {
    val db_percent_off: AnyRef = dbObject.get("percent_off")
    val percent_off: Option[String] = if (db_percent_off == null) None else db_percent_off.asInstanceOf[Option[String]]
    PriceModel(
      product_code = dbObject.get("product_code").toString,
      upc_code = dbObject.get("upc_code").toString,
      price_status = dbObject.get("price_status").toString,
      current_price = dbObject.get("sale_price").asInstanceOf[Int],
      usd_current_price = None,
      msrp = dbObject.get("list_price").asInstanceOf[Int],
      usd_msrp = None,
      modified_on = Some(dbObject.get("modified_on").toString),
      markdown_price = dbObject.get("sale_price").asInstanceOf[Int],
      usd_markdown_price = None,
      privatesale_price = dbObject.get("privatesale_price").asInstanceOf[Int],
      usd_privatesale_price = None,
      list_price = dbObject.get("list_price").asInstanceOf[Int],
      usd_list_price = None,
      date_field = "",
      final_sale_status = dbObject.get("final_sale_status").toString,
      percent_off = percent_off
    )
  }
}
