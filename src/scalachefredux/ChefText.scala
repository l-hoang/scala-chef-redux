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
      startLine = s
    }

    def setEndLine(e: Int) = {
      endLine = e
    }
  }

  // start at line 1
  var currentLine = 1 

  // Maps line numbers to actual ChefLines
  val lines = new mutable.HashMap[Int, ChefLine]
  // Maps function names to start/end
  val functions = new mutable.HashMap[String, FunctionInfo]
}