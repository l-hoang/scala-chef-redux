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

class ChefText {
/* Holds Chef lines as they are build by the line builder */
  
  class FunctionInfo {
  /* Stores function start line/end line */
    var startLine = -1
    var endLine = -1

    def setStartLine(s: Int) = {
    /* Set the start line of a function. It's the first line that the function
     * needs to run */
      startLine = s
    }

    def setEndLine(e: Int) = {
    /* Set the end line of a function. It's the first line of the NEXT function
     * (or a line that doesn't correspond to any function if it's the
     * end of the Chef program. */
      endLine = e
    }
  }

  // start at line 1
  var currentLine = 1 

  // current function being parsed
  var currentFunction = ""

  // Maps line numbers to actual ChefLines operations
  val lines = new mutable.HashMap[Int, ChefLine]
  // Maps function names to start/end
  val functions = new mutable.HashMap[String, FunctionInfo]


  ///////////
  // Lines //
  ///////////
  /* Functions to deal with saving Chef Lines and such */

  ///////////////
  // Functions //
  ///////////////
  /* Functions that deal with the functions of a Chef Program */

  def functionStart(functionName: String) = {
  /* Given a function name, mark the current line number as the start line
   * of the function */
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

    currentLine += 1
  }

  def endFunction = {
  /* Called at the end of parsing. Deals with saving the end of the last 
   * function. */
    functions(currentFunction) setEndLine currentLine
  }

  ///////////
  // Other //
  ///////////
  /* Things that deal with other things that should/need to be done */

  def consistencyCheck = {
  /* Make sure all of the lines/info in the text are consistent (e.g. all 
   * functions have a start and an end) */

    // TODO
  }
}
