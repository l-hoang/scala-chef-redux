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

  /* Push a given ingredient into a certain bowl */
  def pushToBowl(ingredient: String, bowlNumber: Int) =
    currentBowls(bowlNumber).push(currentIngredients(ingredient).deepCopy)
}
