import model._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.math.BigDecimal
import java.math.RoundingMode

class ShoppingBasketTest extends AnyFlatSpec with Matchers:
  
  val basketPricer = new ShoppingBasket()
  
  "ShoppingBasket" should "calculate subtotal correctly for single items" in {
    val result = basketPricer.calculatePricing(List("Apples"))
    result.subtotal should be(BigDecimal("1.00"))
    result.total should be(BigDecimal("0.90")) // 10% discount
  }
  
  it should "calculate subtotal with no discounts" in {
    val result = basketPricer.calculatePricing(List("Milk"))
    result.subtotal should be(BigDecimal("1.30"))
    result.discounts should be(empty)
    result.total should be(BigDecimal("1.30"))
  }
  
  it should "apply apples 10% discount" in {
    val result = basketPricer.calculatePricing(List("Apples", "Milk", "Bread"))
    result.subtotal should be(BigDecimal("3.10"))
    result.discounts.length should be(1)
    result.discounts.head.description should be("Apples 10% off")
    result.discounts.head.discountValue should be(BigDecimal("0.10"))
    result.total should be(BigDecimal("3.00"))
  }
  
  it should "apply soup and bread offer" in {
    val result = basketPricer.calculatePricing(List("Soup", "Soup", "Bread"))
    result.subtotal should be(BigDecimal("2.10")) // 0.65 + 0.65 + 0.80
    result.discounts.length should be(1)
    result.discounts.head.description should be("Buy 2 tins of soup get bread half price")
    result.discounts.head.discountValue should be(BigDecimal("0.40")) 
    result.total should be(BigDecimal("1.70"))
  }
  
  it should "apply multiple offers" in {
    val result = basketPricer.calculatePricing(List("Apples", "Soup", "Soup", "Bread"))
    result.subtotal should be(BigDecimal("3.10")) // 1.00 + 0.65 + 0.65 + 0.80
    result.discounts.length should be(2)
    val appleDiscount = result.discounts.find(_.description.contains("Apples")).get
    val soupDiscount = result.discounts.find(_.description.contains("soup")).get
    appleDiscount.discountValue should be(BigDecimal("0.10"))
    soupDiscount.discountValue should be(BigDecimal("0.40"))
    result.total should be(BigDecimal("2.60"))
  }
  
  it should "handle multiple breads with soup offer" in {
    val result = basketPricer.calculatePricing(List("Soup", "Soup", "Soup", "Bread", "Bread"))
    result.subtotal should be(BigDecimal("3.55")) 
    result.discounts.length should be(1)
    result.discounts.head.discountValue should be(BigDecimal("0.40")) //one bread half price
    result.total should be(BigDecimal("3.15"))
  }
  
  it should "handle empty basket" in {
    val result = basketPricer.calculatePricing(List())
    result.subtotal should be(BigDecimal("0.00"))
    result.discounts should be(empty)
    result.total should be(BigDecimal("0.00"))
  }
  
  it should "ignore invalid items" in {
    val result = basketPricer.calculatePricing(List("InvalidItem", "Apples"))
    result.subtotal should be(BigDecimal("1.00"))
    result.total should be(BigDecimal("0.90"))
  }
