import model._
import java.math.BigDecimal
import java.math.RoundingMode

object Main:
  private def displayMoney(amount: BigDecimal): String =
    f"GBP${amount.setScale(2, RoundingMode.HALF_UP)}"
  
  private def displayDiscount(amount: BigDecimal): String =
    val penceValue = amount.multiply(BigDecimal("100")).intValue()
    s"${penceValue}p"
  
  def main(args: Array[String]): Unit =
    val cleanArgs = if (args.length > 0 && args(0) == "Main") {
         args.tail
        } else {
         args
        }
    
    // validate input
    if cleanArgs.length < 1 || cleanArgs(0) != "PriceBasket" then
      println("Usage: PriceBasket item1 item2 item3 ...")
      println("Available items: Soup, Bread, Milk, Apples")
      sys.exit(1)
    
    // shopping process
    val shoppingItems = cleanArgs.tail.toList
    val basketCalculator = new ShoppingBasket()
    val pricingResult = basketCalculator.calculatePricing(shoppingItems)
    
    // results
    println(s"Subtotal: ${displayMoney(pricingResult.subtotal)}")
    
    if pricingResult.discounts.isEmpty then
      println("(No offers available)")
    else
      pricingResult.discounts.foreach { discount =>
        println(s"${discount.description}: ${displayDiscount(discount.discountValue)}")
      }
    
    println(s"Total price: ${displayMoney(pricingResult.total)}")
