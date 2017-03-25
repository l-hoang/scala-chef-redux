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

class ChefIngredient(name: String, interpretation: IState) = {
/* Class that represents a Chef ingredient. Has the ingredient quantity as well
 * as its interpretation. */

  var ingredientName = name
  var currentInterpretation = interpretation

  var initialized = false
  var quantity = 0

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
