package model

import java.math.BigDecimal
import java.math.RoundingMode

// Base trait for all special offers and discounts
sealed trait SpecialOffer:
  def offerDescription: String
  def calculateDiscount(cartItems: List[Product]): BigDecimal

// 10% discount on apples this week
case class WeeklyAppleDiscount() extends SpecialOffer:
  val offerDescription = "Apples 10% off"
  
  def calculateDiscount(cartItems: List[Product]): BigDecimal =
    val appleQuantity = cartItems.count(_ == Product.Apples)
    val appleTotalPrice = Product.Apples.unitPrice.multiply(BigDecimal(appleQuantity))
    appleTotalPrice.multiply(BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP)

// Buy 2 soups, get bread half price
case class SoupBreadComboDeal() extends SpecialOffer:
  val offerDescription = "Buy 2 tins of soup get bread half price"
  
  def calculateDiscount(cartItems: List[Product]): BigDecimal =
    val soupQuantity = cartItems.count(_ == Product.Soup)
    val breadQuantity = cartItems.count(_ == Product.Bread)
    val eligibleBreads = math.min(soupQuantity / 2, breadQuantity)
    val breadDiscountAmount = Product.Bread.unitPrice.multiply(BigDecimal("0.50"))
    breadDiscountAmount.multiply(BigDecimal(eligibleBreads)).setScale(2, RoundingMode.HALF_UP)
