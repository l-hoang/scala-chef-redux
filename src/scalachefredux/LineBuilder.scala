package scalachefredux

class LineBuilder {
/* This class builds up Chef lines and returns them upon request. */
  
  abstract sealed class ParseMode

  case object M_TITLE extends ParseMode
  case object M_INGREDIENT extends ParseMode
  case object M_METHOD extends ParseMode

  // Starts in title mode
  var mode: ParseMode = M_TITLE

  ////////////////////
  // Mode swtichers //
  ////////////////////
  /* Switch line builder modes to know what kind of lines it is currently able
   * to parse */
  def modeTitle = {
  /* Switch to title mode */
    mode = M_TITLE
  }

  def modeIngredient = {
  /* Switch to ingredient mode */
    mode = M_INGREDIENT
  }

  def modeMethod = {
  /* Switch to method mode */
    mode = M_METHOD
  }

  ////////////////
  // Assertions //
  ////////////////
  /* Assert certain things about the current line builder state */

  def assertTitle = {
  /* Title assertion */
    mode match {
      case M_TITLE =>
      case _ => throw new RuntimeException("Assert title mode failed")
    }
  }

  def assertIngredient = {
  /* Ingredient assertion */
    mode match {
      case M_INGREDIENT =>
      case _ => throw new RuntimeException("Assert ingredient mode failed")
    }
  }

  def assertMethod = {
  /* Method assertion */
    mode match {
      case M_METHOD =>
      case _ => throw new RuntimeException("Assert method mode failed")
    }
  }

}
