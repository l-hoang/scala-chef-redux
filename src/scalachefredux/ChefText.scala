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

/* Holds Chef lines as they are build by the line builder */
class ChefText {
  /* Stores function start line/end line */
  class FunctionInfo {
    var startLine = -1
    var endLine = -1

    /* Set the start line of a function. It's the first line that the function
     * needs to run */
    def setStartLine(s: Int) = {
      startLine = s
    }

    /* Set the end line of a function. It's the first line of the NEXT function
     * (or a line that doesn't correspond to any function if it's the
     * end of the Chef program. */
    def setEndLine(e: Int) = {
      endLine = e
    }

    /* Get the start line */
    def getStartLine = startLine

    /* Get the end line */
    def getEndLine = endLine
  }

  // start at line 1
  var currentLine = 1 

  // current function being parsed
  var currentFunction = ""

  // Maps recipe to the ingredients they have/start with
  val recipeIngredients = new mutable.HashMap[String, 
                          mutable.HashMap[String, ChefIngredient]]


  // Maps line numbers to actual ChefLines operations
  val lines = new mutable.HashMap[Int, ChefLine]
  // Maps function names to start/end
  val functions = new mutable.HashMap[String, FunctionInfo]


  ///////////
  // Lines //
  ///////////
  /* Functions to deal with saving Chef Lines and such */

  /* Adds a Chef Line to the program text. */
  def addLine(newLine: ChefLine) = {
    lines(currentLine) = newLine 
    //println(newLine)
    currentLine += 1
  }

  /* Get the requested line number */
  def getLine(lineNumber: Int) = lines(lineNumber)

  /* Add a starting ingredient to the current function */
  def addIngredient(newIngredient: ChefIngredient) = {
    val ingredientName = newIngredient.ingredientName
    
    if (recipeIngredients(currentFunction) contains ingredientName) {
      throw new 
      RuntimeException("ERROR: Redeclaring an already existing ingredient.")
    }

    recipeIngredients(currentFunction)(ingredientName) = newIngredient
  }

  ///////////////
  // Functions //
  ///////////////
  /* Functions that deal with the functions of a Chef Program */

  /* Given a function name, mark the current line number as the start line
   * of the function */
  def functionStart(functionName: String) = {
    if (functions contains functionName) {
      throw new RuntimeException("ERROR: Redeclaring an already existing recipe.")
    }

    // if this is not the first function being declared, save the end of the
    // last function as the current line number
    if (!(currentFunction == "")) {
      functions(currentFunction) setEndLine currentLine
    }

    functions(functionName) = new FunctionInfo
    functions(functionName) setStartLine currentLine
    currentFunction = functionName
    //currentLine += 1 // TODO do I need to add a line? I shouldn't, right?

    // initialize start ingredients for this recipe
    recipeIngredients(functionName) = new mutable.HashMap[String, ChefIngredient]
  }

  /* Called at the end of parsing. Deals with saving the end of the last 
   * function. */
  def endFunction = {
    functions(currentFunction) setEndLine currentLine
  }

  /* Get the function start line for some function */
  def getStartLine(functionName: String) = {
    functions(functionName).getStartLine
  }

  /* Get the function end line for some function */
  def getEndLine(functionName: String) = {
    functions(functionName).getEndLine
  }

  ///////////
  // Other //
  ///////////
  /* Things that deal with other things that should/need to be done */

  /* Make sure all of the lines/info in the text are consistent (e.g. all 
   * functions have a start and an end) */
  def consistencyCheck = {
    for (i <- functions.keys) {
      if (functions(i).getStartLine == -1 || functions(i).getEndLine == -1) {
        throw new RuntimeException("ERROR: recipe " + i + " inconsistent")
      }
    }

    // TODO
  }

  /* Print all the Chef lines contained in this object. */
  def printLines = {
    for (i <- 1 to currentLine) {
      println("Line " + i + ": " + lines(i))
    }
  }
}
