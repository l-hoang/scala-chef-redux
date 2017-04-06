package programs

import scalachefredux._
import scala.language.postfixOps

object Tester3 extends ScalaChefRedux {
  def main(args: Array[String]) = {
    Title - "stir";

    // add some ingredients
    Ingredients;

    0 ct "filler";
    1 ct "tofu";
    72 g "haricot beans" ; // dry
    101 ct "eggs";
    108 g "lard"; // dry
    111 cups "oil";
    32 ct "zucchinis";
    119 ml "water";
    114 g "red salmon"; // dry
    100 g "dijon mustard"; // dry
    33 ct "potatoes";

    Method;
    // put hello into the bowl
    Put some "oil" into the mixing bowl;
    Put some "lard" into the mixing bowl;
    Put some "lard" into the mixing bowl;
    Put the "eggs" into the mixing bowl;
    Put the "haricot beans" into the mixing bowl;

    // make it all text
    Liquefy the contents of the mixing bowl;

    // should result in ellho
    Stir fr 3 minutes;

    Pour the contents of the mixing bowl into the baking dish; 

    Recipe serves 1;

    Enjoy your meal;
  }

}
