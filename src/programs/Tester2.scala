package programs

import scalachefredux._
import scala.language.postfixOps

object Tester2 extends ScalaChefRedux {
  def main(args: Array[String]) = {
    Title - "Recipe 1";

    // add some ingredients
    Ingredients;
    100 g "flour";
    250 g "butter";
    1 ct "egg";
    Method;

    "Sift" the "flour";
    Put the "flour" into the mixing bowl;
    Serve wth "Recipe 2";
    Stir fr 2 minutes;
    Remove the "egg";
    "Rub" the "flour" until "sifted";
    Stir fr 2 minutes;
    Fold the "butter" into the mixing bowl;
    Pour the contents of the mixing bowl into the baking dish; 

    Recipe serves 1;

    // hello world
    Title - "Recipe 2";

    Ingredients;
    1 cup "white sugar";
    1 cup "brown sugar";
    1 ct "vanilla bean";

    Method;
    Fold the "white sugar" into the mixing bowl;
    Put the "white sugar" into the mixing bowl;
    Fold the "brown sugar" into the mixing bowl;
    Clean the bowl;
    Put the "white sugar" into the mixing bowl;
    Remove the "vanilla bean";
    Fold the "white sugar" into the mixing bowl;
    "Melt" the "white sugar";
    Put the "vanilla bean" into the mixing bowl;
    Refrigerate;

    Enjoy your meal;
  }

}
