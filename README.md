#  Shopping Basket

A Scala  program that prices a shopping basket with special offers, driven by unit tests.

## Products available

| Product    | Price     |
|------------|-----------|
| Soup       | £0.65     |
| Bread      | £0.80     |
| Milk       | £1.30     |
| Apples     | £1.00     |

## Active offers

- **Apples**: 10% de discount  
- **Soup + Bread**: Buy 2 cans of soup → bread at 50% off

## Use

```bash
sbt "run Main PriceBasket Apples Milk Bread Soup"

Examples

Bash# With discount on apples
sbt "run Main PriceBasket Apples Milk Bread"
→ Subtotal: £3.10
→ Apples 10% off: 10p
→ Total: £3.00

Bash# Soup and bread offer
sbt "run Main PriceBasket Soup Soup Bread"
→ Subtotal: £2.10
→ Buy 2 tins of soup get bread half price: 40p
→ Total: £1.70

Bash# No offers
sbt "run Main PriceBasket Milk"
→ Subtotal: £1.30
→ (No offers available)
→ Total: £1.30

Requirements

Scala 3
sbt
Java 21+

# Execute
sbt run

# Tests
sbt test

