package scalachefredux

/* File that contains the types of Chef lines possible in this system */

abstract sealed class ChefLine

case class Read(ingredient: String) extends ChefLine // Take
case class Push(bowlNumber: Int) extends ChefLine // Put
case class Pop(bowlNumber: Int) extends ChefLine // Fold

case class Liquify(ingredient: String) extends ChefLine // Liquify
case class LiquifyBowl(bowlNumber: Int) extends ChefLine // Liquify

case class CopyStack(bowlNumber: Int, dishNumber: Int) extends ChefLine // Pour

case object Break extends ChefLine // Set

case class PrintStacks(num: Int) extends ChefLine // Serves

case class Useless extends ChefLine // To appease type checker

// TODO

