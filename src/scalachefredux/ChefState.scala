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

package scalachefredux

import scala.collection.mutable

/* Holds current program state */
class ChefState {
  // track if a main recipe has been set + main recipe name
  var recipeSet = false
  var mainRecipe = ""

  // mixing bowls and baking dishes
  var currentBowls = new mutable.HashMap[Int, ChefStack]
  var currentDishes = new mutable.HashMap[Int, ChefStack]
  currentBowls(1) = new ChefStack
  currentDishes(1) = new ChefStack

  var currentIngredients = new mutable.HashMap[String, ChefIngredient]


  def assertSetRecipe = if (!recipeSet) {
    throw new RuntimeException("ERROR: unset recipe")
  }
  
  /* Set the main recipe from which to start execution when you begin running
   * the program */
  def setMainRecipe(recipe: String) = {
    if (!recipeSet) { 
      mainRecipe = recipe
      recipeSet = true
    } else {
      throw new RuntimeException("ERROR: trying to set a new main recipe")
    }
  }

  /* Get the main recipe */
  def getMainRecipe = if (recipeSet) mainRecipe else 
    throw new RuntimeException("ERROR: trying to get unset main recipe")

  /* Load the main recipe's ingredients (a copy at least) for use */
  def initializeIngredients(text: ChefText) = {
    assertSetRecipe
    currentIngredients = text getStartingIngredients mainRecipe
  }

  /* Make sure a certain bowl exists; if not create it */
  def assertBowlExistence(bowlNumber: Int) = {
    if (!(currentBowls contains bowlNumber)) {
      currentBowls(bowlNumber) = new ChefStack
    }
  }

  /* Make sure a certain dish exists; if not create it */
  def assertDishExistence(dishNumber: Int) = {
    if (!(currentDishes contains dishNumber)) {
      currentDishes(dishNumber) = new ChefStack
    }
  }

  /* Push a given ingredient into a certain bowl */
  def pushToBowl(ingredient: String, bowlNumber: Int) = {
    assertBowlExistence(bowlNumber)
    currentBowls(bowlNumber).push(currentIngredients(ingredient).deepCopy)
  }

  /* Liquefy an ingredient we have */
  def liquefyIngredient(ingredient: String) = {
    currentIngredients(ingredient).setInterpretation(I_LIQUID)
  }

  /* Convert interpretation of a bowl into liquid */
  def liquefyBowl(bowlNumber: Int) = {
    assertBowlExistence(bowlNumber)
    currentBowls(bowlNumber).liquefy
  }

  /* Clear a bowl */
  def clearBowl(bowlNumber: Int) = {
    assertBowlExistence(bowlNumber)
    currentBowls(bowlNumber).empty
  }

  /* Copy a bowl to a dish (stack to stack) */
  def bowlToDish(bowlNumber: Int, dishNumber: Int) = {
    assertBowlExistence(bowlNumber)
    assertDishExistence(dishNumber)

    val bIterator = currentBowls(bowlNumber).descendingIterator

    // copy over in reverse order
    while (bIterator.hasNext) {
      currentDishes(dishNumber) push bIterator.next.deepCopy
    }
  }

  /* Prints the contents of the baking dishes: note it destroys the baking
   * dishes in question as it alters them */
  def printDishes(numToPrint: Int) = {
    for (i <- 1 to numToPrint) {
      assertDishExistence(i)

      val currentDish = currentDishes(i)

      while (!currentDish.empty) {
        val ingredient = currentDish.pop
        ingredient.getInterpretation match {
          case I_LIQUID => printf("%c", ingredient.asChar)
          case I_DRY | I_EITHER => printf("%d", ingredient.asNumber)
          case I_NULL => 
            throw new RuntimeException("ERROR: ingredient in dish is null")
        }
      }
    }
  }
}
