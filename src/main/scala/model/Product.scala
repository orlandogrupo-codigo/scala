package model

import java.math.BigDecimal
import java.math.RoundingMode

// Product catalog with pricing information
enum Product(val itemName: String, val unitPrice: BigDecimal):
  case Soup extends Product("Soup", BigDecimal("0.65"))
  case Bread extends Product("Bread", BigDecimal("0.80"))
  case Milk extends Product("Milk", BigDecimal("1.30"))
  case Apples extends Product("Apples", BigDecimal("1.00"))

// Factory method to create products from string input
object Product:
  def fromString(productName: String): Option[Product] = 
    Product.values.find(_.itemName.equalsIgnoreCase(productName))
