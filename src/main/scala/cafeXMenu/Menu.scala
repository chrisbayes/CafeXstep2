/**
 * @author Chris
 *
 */
package cafeXMenu
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import java.io.File

/**
 * Class for handling Cafe X Menu
 *
 *
 */
class Menu {

  /**
   * Collection of MenuItems
   */
  private val items: ArrayBuffer[MenuItem] = ArrayBuffer()

  /**
   * Add MenuItem to collection
   * @param item
   * @return void
   */
  def add(item: MenuItem) = items += item

  /**
   * Get collection size
   * @return collection size
   */
  def size: Int = items.size

  /**
   * Find menu items by name
   * @param name
   * @return matching menu items
   */
  def findByName(name: String): Seq[MenuItem] =
    items.filter(_.name == name)

  /**
   * Place an order
   * @param names of items in order i.e. ["Cola", "Coffee", "Cheese Sandwich"]
   * @return count of items added
   */
  def orderS(names: List[String]) =
    items.filter(i => names.contains(i.name)).map({ i => i.quantity += 1
      i }).size
      
  /**
   * Place an order
   * @param names of items in order i.e. ["Cola", "Coffee", "Cheese Sandwich"]
   * @return price of items added
   */
  def orderP(names: List[String]) =
    items.filter(i => names.contains(i.name)).map({ i => i.quantity += 1
      i }).map(i => i.price * i.quantity).sum

  /**
   * Calculate the total price of ordered items
   * @return price of ordered items
   */
  def bill() = {
    // Calculate total
    val sub = items.map(i => i.price * i.quantity).sum
    // Check food types
    var service = if (items.filter(i => i.quantity > 0 && i.ttype == "Hot" && i.ftype == "Food").size > 0){
      // Hot Food gets 20% service charge limited to £20
      Math.min(sub * 20/100, 20.0)
    }else if (items.filter(i => i.quantity > 0 && i.ftype == "Food").size > 0){
      // Cold Food gets 10% service charge. Not limited
      sub * 10/100
    }else
      // Only Drink gets zero service charge
      0
    // Add service rounded to 2 decimal places
    (sub, Math.round(service*100.0)/100.0)
  }

  /**
   * Print menu
   * 
   */
  def printAll() =
    items.map(i => println(s"${i.id}\t${i.name}\t ${i.price}\t ${i.ttype}\t ${i.ftype}\t ${i.quantity}"))
  /**
   * Print bill
   * 
   */
  def printBill() = {
    items.filter(_.quantity > 0).map(i => println(f"${i.id}\t${i.name}\t ${i.price}%.2f\t ${i.ttype}\t ${i.ftype}\t ${i.quantity}"))
    val subtotal = this.bill()
    println(f"Subtotal\t${subtotal._1}%.2f\tService Charge\t${subtotal._2}%.2f\tTotal\t${subtotal._1 + subtotal._2}%.2f")
  }
}

/**
 * Menu instance
 *
 *
 */
object Menu {

  /**
   * Populate menu collection from file
   * @param f text file
   * @return a new menu
   */
  def fromFile(file: File) = {
    val newMenu = new Menu
    val itemRegex = "(.*)?[,](.*)?[,](.*)?[,](.*)?[,](.*)?".r

    Source.fromFile(file).getLines() foreach { line =>
      line match {
        case itemRegex(id, name, tp, fd, price) =>
          newMenu.add(new MenuItem(id.toInt, name, tp, fd, price.toDouble))
        case _ =>
          println("not matched: " + line)
      }
    }
    newMenu
  }
}