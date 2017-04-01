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

package programs

import scalachefredux._
import scala.language.postfixOps

object Tester extends ScalaChefRedux {
  def main(args: Array[String]) = {
    Title - "hello thar";

    // add some ingredients
    Ingredients;

    1 ct "tofu";
    72 g "haricot_beans" ;
    101 ct "eggs";
    108 g "lard";
    111 cups "oil";
    32 ct "zucchinis";
    119 ml "water";
    114 g "red_salmon";
    100 g "dijon_mustard";
    33 ct "potatoes";

    Method;
  }
}
