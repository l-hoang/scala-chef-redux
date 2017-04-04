/* Original license for Scala-Chef

The MIT License (MIT)
Copyright (c) 2014-2016 Zane Urbanski, Eric Yu, Loc Hoang

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/* This file holds helper classes that should be globally visible. They aid
 * in various functions. */

package scalachefredux

import scala.collection.mutable
import java.util.ArrayDeque

class ChefIngredient(name: String, interpretation: IState, initValue: Int = -1, 
                     copy: Boolean = false, cInitialized: Boolean = false) {
/* Class that represents a Chef ingredient. Has the ingredient quantity as well
 * as its interpretation. */

  def ingredientName = name
  var currentInterpretation = interpretation

  var initialized = false
  var quantity = 0

  if (initValue != -1) {
    initialized = true
    quantity = initValue
  }

  /* if this ingredient was made from a copy, then take its passed in 
   * values */
  if (copy) {
    quantity = initValue
    initialized = cInitialized
  }

  /* Return a new copy of the ingredient */
  def deepCopy = {
    new ChefIngredient(ingredientName, currentInterpretation, quantity, true,
                       initialized)
  }

  /////////////
  // Setters //
  /////////////

  def setValue(newValue: Int) = {
  /* Set the quantity of this ingredient */
    quantity = newValue
    initialized = true
  }

  def setInterpretation(newInterpretation: IState) = {
  /* Change the intrepretation of this ingredient */
    currentInterpretation = newInterpretation
  }

  /////////////
  // Getters //
  /////////////

  def asNumber = {
  /* Return the value of this ingredient as a number */
    assertInitialized
    quantity
  }

  def asChar = {
  /* Return the value of this ingredient as a Unicode char */
    assertInitialized
    quantity.toChar
  }

  ////////////////
  // Assertions //
  ////////////////

  def assertInitialized = {
  /* Assert that this ingredient has been assigned a value */
    if (!initialized) {
      throw new RuntimeException("ERROR: ingredient not initialized")
    }
  }
}

/* A wrapper class for a Java deque that is used to represent a Chef stack 
 * (dish or bowl) */
class ChefStack {
  val javaDeque = new ArrayDeque[ChefIngredient]

  /* put at front of deque */
  def push(i: ChefIngredient) = javaDeque push i
  /* pop from the front of the deque */
  def pop = javaDeque.pop

  /* Change the interpretation of all ingredients in this stack to liquid */
  def liquefy = {
    val thisIterator = javaDeque.iterator

    while (thisIterator.hasNext) {
      (thisIterator.next) setInterpretation I_LIQUID
    }
  }

  /* Move an ingredient stirNum places down the stack */
  def stir(stirNum: Int) = {
    // TODO
  }

  /* Randomize the order of the stack */
  def mix = {
    // TODO
  }

  /* Empty the stack */
  def clean = {
    javaDeque.clear
  }

  def deepCopy(otherStack: ChefStack) = {
    if(!javaDeque.isEmpty) {
      throw new RuntimeException("ERROR: can only copy into this stack if empty")
    }

    val otherIterator = otherStack.iterator

    while (otherIterator.hasNext) {
      javaDeque add (otherIterator.next.deepCopy)
    }
  }

  /* Return an iterator to the Java deque (order is same as popping it) */
  def iterator = javaDeque.iterator
  /* Return an backward iterator to the Java deque (pop from bottom) */
  def descendingIterator = javaDeque.descendingIterator
}

/* Contains helper functions that are used throughout Scala-Chef redux */
object HelperFunctions {
  /* Returns a deep copy of a mutable.HashMap holding ingredients */
  def deepCopyIngredients(toCopy: mutable.HashMap[String, ChefIngredient]) = {
    val mapIterator = toCopy.keys
  
    val copy = new mutable.HashMap[String, ChefIngredient] 
  
    for (key <- mapIterator) {
      copy(key) = toCopy(key).deepCopy
    }
  
    copy
  }
}
