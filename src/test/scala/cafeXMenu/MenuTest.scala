package cafeXMenu

import java.io.File
import org.junit.Test
import org.junit.Assert

/**
 * @author Chris
 *
 */
class MenuTest {
  // Menu file path
  val path = getClass.getResource("/menu.txt").getPath
  
  /**
   * Test that the correct number of items are loaded from file
   *
   */
  @Test def LoadTest {

    val amenu = Menu.fromFile(new File(path))

    Assert.assertTrue(s"${amenu.size} != 4",
        amenu.size == 4)
  }
  /**
   * Test that an order can be placed
   *
   */
  @Test def OrderSTest {

    val amenu = Menu.fromFile(new File(path))
    val ocount = amenu.orderS(List("Cola", "Coffee", "Cheese Sandwich"))

    Assert.assertTrue(ocount == 3)
  }
  /**
   * Test that an order can be placed
   * Pass in a list of purchased items that produces a total bill.
   * e.g. [“Cola”, “Coffee”, “Cheese Sandwich”] returns 3.5
   *
   */
  @Test def OrderPTest {

    val amenu = Menu.fromFile(new File(path))
    val oprice = amenu.orderP(List("Cola", "Coffee", "Cheese Sandwich"))

    Assert.assertTrue(oprice == 3.50)
  }
  /**
   * Test that items not on the menu aren't ordered
   *
   */
  @Test def OrderRubbishTest {

    val amenu = Menu.fromFile(new File(path))
    val ocount = amenu.orderS(List("Cla", "Cffee", "Chese Sandwich", "Stk Sandwich"))

    Assert.assertTrue(s"${ocount} != 0",
        ocount == 0)
  }
  /**
   * Test that the correct total is returned
   *
   */
  @Test def BillTest {

    val amenu = Menu.fromFile(new File(path))
    val ocount = amenu.orderS(List("Cola", "Coffee", "Cheese Sandwich"))

    val subtotal = amenu.bill()
    amenu.printBill()
    
    Assert.assertTrue(s"${subtotal._1} != 3.5 or ${subtotal._2} != 0.35",
        subtotal._1 == 3.5 && subtotal._2 == 0.35)
  }
  /**
   * Test that no service added to drinks
   * 
   * When all purchased items are drinks no service charge is applied
   * 
   */
  @Test def BillDrinksTest {

    val amenu = Menu.fromFile(new File(path))
    val ocount = amenu.orderS(List("Cola", "Coffee"))

    val subtotal = amenu.bill()
    amenu.printBill()
    
    Assert.assertTrue(s"${subtotal._1} != 1.5 or ${subtotal._2} != 0",
        subtotal._1 == 1.5 && subtotal._2 == 0)
  }
  /**
   * Test that cold food gets 10%
   * 
   * When purchased items include any food apply a service charge of 10% to the total bill (rounded to 2 decimal places)
   * 
   */
  @Test def BillColdFoodTest {

    val amenu = Menu.fromFile(new File(path))
    val ocount = amenu.orderS(List("Cola", "Coffee", "Cheese Sandwich"))

    val subtotal = amenu.bill()
    amenu.printBill()
    
    Assert.assertTrue(s"${subtotal._1} != 3.5 or ${subtotal._2} != 0.35",
        subtotal._1 == 3.5 && subtotal._2 == 0.35)
  }
  /**
   * Test that cold food gets 10% unlimited
   * 
   * When purchased items include any food apply a service charge of 10% to the total bill (rounded to 2 decimal places)
   * 
   */
  @Test def BillColdFoodUnlimitedTest {

    val amenu = Menu.fromFile(new File(path))
    // Crank order up to test service charge
    (1 to 100).foreach(c => amenu.orderS(List("Cola", "Coffee", "Cheese Sandwich")))

    val subtotal = amenu.bill()
    amenu.printBill()
    
    Assert.assertTrue(s"${subtotal._1} != 3.5 or ${subtotal._2} != 0.35",
        subtotal._1 == 350.0 && subtotal._2 == 35.0)
  }
  /**
   * Test that hot food gets 20%
   * 
   * When purchased items include any hot food apply a service charge of 20% to the total bill with a maximum £20 service charge
   *
   */
  @Test def BillHotFoodTest {

    val amenu = Menu.fromFile(new File(path))
    val ocount = amenu.orderS(List("Cola", "Coffee", "Steak Sandwich"))
    //amenu.printAll()
    val subtotal = amenu.bill()
    amenu.printBill()
    
    Assert.assertTrue(s"${subtotal._1} != 6.0 or ${subtotal._2} != 1.20",
        subtotal._1 == 6.0 && subtotal._2 == 1.20)
  }
  /**
   * Test that hot food gets 20% limited to £20
   * 
   * When purchased items include any hot food apply a service charge of 20% to the total bill with a maximum £20 service charge
   *
   */
  @Test def BillHotFoodLimitTest {
    
    val amenu = Menu.fromFile(new File(path))
    // Crank order up to test service charge
    (1 to 20).foreach(c => amenu.orderS(List("Cola", "Coffee", "Steak Sandwich")))
    
    val subtotal = amenu.bill()
    amenu.printBill()
    
    Assert.assertTrue(s"${subtotal._1} != 120.0 or ${subtotal._2} != 20.0",
        subtotal._1 == 120.0 && subtotal._2 == 20.00)
  }
}