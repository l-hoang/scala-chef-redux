package scalachefredux

/* File that contains the types of Chef lines possible in this system */

abstract sealed class ChefLine

case class PutLine() extends ChefLine

// TODO

