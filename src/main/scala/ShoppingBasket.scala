import model._
import java.math.BigDecimal
import java.math.RoundingMode

case class CartItem(product: Product, quantity: Int)

class ShoppingBasket:
  private val currentPromotions: List[SpecialOffer] = List(
    WeeklyAppleDiscount(), 
    SoupBreadComboDeal()
  )
  
  def calculatePricing(items: List[String]): PricingResult =
    val productList = items.flatMap(Product.fromString)
    val subtotalAmount = computeSubtotal(productList)
    val appliedDiscounts = findApplicableDiscounts(productList)
    val finalTotal = subtotalAmount.subtract(
      appliedDiscounts.map(_.discountValue).foldLeft(BigDecimal.ZERO)((acc, disc) => acc.add(disc))
    ).setScale(2, RoundingMode.HALF_UP)
    
    PricingResult(subtotalAmount, appliedDiscounts, finalTotal)

  private def computeSubtotal(products: List[Product]): BigDecimal =
    val total = products.map(_.unitPrice).foldLeft(BigDecimal.ZERO)((acc, price) => acc.add(price))
    total.setScale(2, RoundingMode.HALF_UP)
  
  private def findApplicableDiscounts(products: List[Product]): List[AppliedDiscount] =
    currentPromotions.flatMap { promotion =>
      val discountAmount = promotion.calculateDiscount(products)
      if discountAmount.compareTo(BigDecimal.ZERO) > 0 then
        Some(AppliedDiscount(promotion.offerDescription, discountAmount))
      else
        None
    }

case class PricingResult(subtotal: BigDecimal, discounts: List[AppliedDiscount], total: BigDecimal)
case class AppliedDiscount(description: String, discountValue: BigDecimal)
