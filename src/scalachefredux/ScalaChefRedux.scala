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
      lineBuilder.assertTitle

      // Tell program text to save the current line as the start of a new
      // function
      programText newFunction recipeTitle

      // go into limbo mode (next thing should be ingredients)
      lineBuilder.modeLimbo
    }
  }

  object Take {

  }

  object Put {

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

  ///////////////////////
  // Mode change lines //
  ///////////////////////
  /* The purpose of the following is to signify a change in the current "mode"
   * a Chef program is in: mainly Title, Ingredient, or Method */
  def Ingredients {
  /* Mode change to ingredient */
    lineBuilder.modeIngredient

  }

  def Method {
  /* Mode change to method */
    lineBuilder.modeMethod
  }
}
