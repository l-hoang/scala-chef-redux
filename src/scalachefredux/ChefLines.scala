package scalachefredux

/* File that contains the types of Chef lines possible in this system */

abstract sealed class ChefLine

case class Read(ingredient: String) extends ChefLine // Take
case class Push(ingredient: String, bowlNumber: Int) extends ChefLine // Put
case class Pop(bowlNumber: Int, ingredient: String) extends ChefLine // Fold

case class Add(ingredient: String, bowlNumber: Int) extends ChefLine
case class Subtract(ingredient: String, bowlNumber: Int) extends ChefLine
case class Multiply(ingredient: String, bowlNumber: Int) extends ChefLine
case class Divide(ingredient: String, bowlNumber: Int) extends ChefLine

case class AddDry(bowlNumber: Int) extends ChefLine

case class Liquefy(ingredient: String) extends ChefLine // Liquefy
case class LiquefyContents(bowlNumber: Int) extends ChefLine // Liquefy

case class ClearStack(bowlNumber: Int) extends ChefLine // Clean

case class CopyStack(bowlNumber: Int, dishNumber: Int) extends ChefLine // Pour


case object Break extends ChefLine // Set
case class PrintStacks(num: Int) extends ChefLine // Serves

case object Useless extends ChefLine // To appease type checker

// TODO

