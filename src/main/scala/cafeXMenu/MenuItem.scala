package cafeXMenu

/** MenuItem class
 * @param i id of item
 * @param n name of item
 * @param t type of item (hot or cold)
 * @param f type of item (food or drink)
 * @param p price of item
 *
 */
class MenuItem(i: Int, n: String, t: String, f: String, p: Double) {
  val id = i
  val name = n
  val ttype = t
  val ftype = f
  val price = p
  var quantity = 0
}

