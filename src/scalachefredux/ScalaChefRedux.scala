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


import scala.language.implicitConversions
import scala.language.dynamics

/* Extend this class to start your DSL writing */
class ScalaChefRedux {

  val lineBuilder = new LineBuilder
  val programText = new ChefText

  ///////////
  // TITLE //
  ///////////
  /* Title parsing; Should only appear at the beginning of a program */

  object Title {
    def -(recipeTitle: String) = {
      if (recipeTitle == "") {
        throw new RuntimeException("ERROR: Recipe name can't be empty")
      }

      // Tell program text to save the current line as the start of a new
      // function
      programText functionStart recipeTitle

      // go into limbo mode (next thing should be Ingredients declaration)
      lineBuilder.modeLimbo
    }
  }

  object Take {
    // a, an, the, some follow take
    def a(ingredient: String) = {

    }
  }

  /* The push operation of the language. 
   * Put the <ingredient> into mixing bowl <number>; */
  object Put {
    def the(ingredient: String) = {
      lineBuilder.assertMethod
      // set ingredient + op
      lineBuilder setString ingredient
      lineBuilder setOp E_PUT

      // return the into object
      IntoGetter
    }

    // aliases for "the"
    def a(ingredient: String) = the(ingredient)
    def an(ingredient: String) = the(ingredient)
    def some(ingredient: String) = the(ingredient)

    object IntoGetter {
      /* "into mixing" leads into "bowl <number>" */
      def into(m: MixingWord) = BowlGetter
      /* "into the" leads into "mixing bowl" */
      def into(t: TheWord) = MixingGetter
    }

    object MixingGetter {
      def mixing(b: BowlWord) = {
        lineBuilder setStackNumber1 1
        programText addLine lineBuilder.finishLine
      }
    }

    object BowlGetter {
      /* bowl with number = into some particular stack */
      def bowl(bowlNumber: Int) = {
        lineBuilder setStackNumber1 bowlNumber
        programText addLine lineBuilder.finishLine
      }

      /* bowl with no number = by default stack 1 */
      def bowl = {
        lineBuilder setStackNumber1 1
        programText addLine lineBuilder.finishLine
      }
    }
  }

  object Fold {

  }

  object Add {

  }

  object Remove {

  }

  object Combine {

  }

  object Divide {

  }

  object Liquify {

  }

  object Stir {

  }

  object Mix {

  }

  object Clean {

  }

  object Pour {

  }


  // TODO verbs: implicits most likely

  object Set {

  }

  object Serve {

  }

  object Refrigerate {

  }

  object Serves {

  }

  /* Integers will be converted into this class, and the class will then
   * grab ingredient strings. */
  class IngredientGetter(num: Int) extends Dynamic {
    if (num < 0) {
      throw new 
      RuntimeException("ERROR: Ingredients in a recipe declaration cannot be negative.")
    }

    /* Ingredients classified as dry */

    def g(ingredient: String) = {
      lineBuilder.assertIngredient
      programText addIngredient (new ChefIngredient(ingredient, I_DRY, num))
    }

    def kg(ingredient: String) = {
      lineBuilder.assertIngredient
      programText addIngredient (new ChefIngredient(ingredient, I_DRY, num))
    }

    def pinch(ingredient: String) = {
      lineBuilder.assertIngredient

      if (num == 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_DRY, num))
      else
        throw new RuntimeException("ERROR: A pinch means a value of 1")
    }

    def pinches(ingredient: String) = {
      lineBuilder.assertIngredient

      if (num > 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_DRY, num))
      else
        throw new RuntimeException("ERROR: Pinches means a value greater than 1")
    }

    /* Ingredients classified as liquid */

    def ml(ingredient: String) = {
      lineBuilder.assertIngredient
      programText addIngredient (new ChefIngredient(ingredient, I_LIQUID, num))
    } 

    def l(ingredient: String) = {
      lineBuilder.assertIngredient
      programText addIngredient (new ChefIngredient(ingredient, I_LIQUID, num))
    }

    def dash(ingredient: String) = {
      lineBuilder.assertIngredient

      if (num == 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_LIQUID, num))
      else
        throw new RuntimeException("ERROR: A dash means a value of 1")
    }

    def dashes(ingredient: String) = {
      lineBuilder.assertIngredient

      if (num > 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_LIQUID, num))
      else
        throw new RuntimeException("ERROR: Dashes means a value greater than 1")
    }

    /* Ingredients classified as either */

    def cup(ingredient: String) = {
      lineBuilder.assertIngredient

      if (num == 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_EITHER, num))
      else
        throw new RuntimeException("ERROR: Cup means a value of 1")
    }

    def cups(ingredient: String) = {
      lineBuilder.assertIngredient

      if (num > 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_EITHER, num))
      else
        throw new RuntimeException("ERROR: Cups means a value greater than 1")
    }


    def teaspoon(ingredient: String) = {
        lineBuilder.assertIngredient

      if (num == 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_EITHER, num))
      else
        throw new RuntimeException("ERROR: Teaspoon means a value of 1")
    }

    def teaspoons(ingredient: String) = {
      lineBuilder.assertIngredient

      if (num > 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_EITHER, num))
      else
        throw new RuntimeException("ERROR: Teaspoons means a value greater than 1")
    }

    def tablespoon(ingredient: String) = {
      lineBuilder.assertIngredient

      if (num == 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_EITHER, num))
      else
        throw new RuntimeException("ERROR: Tablespoon means a value of 1")
    }

    def tablespoons(ingredient: String) = {
      lineBuilder.assertIngredient

      if (num > 1) 
        programText addIngredient (new ChefIngredient(ingredient, I_EITHER, num))
      else
        throw new RuntimeException("ERROR: Tablespoons means a value greater than 1")
    }

    // stands for "count"
    def ct(ingredient: String) = {
      lineBuilder.assertIngredient
      programText addIngredient (new ChefIngredient(ingredient, I_EITHER, num))
    }
  }

  implicit def int2IngredientGetter(i: Int) = new IngredientGetter(i)


  ///////////////////////
  // Mode change lines //
  ///////////////////////
  /* The purpose of the following is to signify a change in the current "mode"
   * a Chef program is in: mainly Title, Ingredient, or Method */
  /* Mode change to ingredient */
  def Ingredients {
    lineBuilder.modeIngredient
  }

  /* Mode change to method */
  def Method {
    lineBuilder.modeMethod
  }
}
