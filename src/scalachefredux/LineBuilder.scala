package scalachefredux

class LineBuilder {
/* This class builds up Chef lines and returns them upon request. */
  
  abstract sealed class ParseMode

  case object M_TITLE extends ParseMode
  case object M_INGREDIENT extends ParseMode
  case object M_METHOD extends ParseMode

  case object M_LIMBO extends ParseMode


  /////////////////////
  // State variables //
  /////////////////////
  /* Variables/things that track the status of the line currently being built
   * as well as the state of the line builder itself */

  // Starts in title mode
  var mode: ParseMode = M_TITLE

  // Start with no op
  var currentOp: ChefOp = E_NONE

  // The current recipe being parsed by the line builder
  var currentRecipe = ""

  // Various things that need to be used to build lines
  var heldString = ""
  var heldNumber = -1
  var stackNumber1 = -1 
  var stackNumber2 = -1

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

  def assertLimbo = {
  /* Limbo assertion */
    mode match {
      case M_LIMBO =>
      case _ => throw new RuntimeException("Assert limbo mode failed")
    }
  }

  def assertNoOp = {
  /* Assert there is currently no op set in the line builder */
    currentOp match {
      case E_NONE =>
      case _ => throw new RuntimeException("Assert no op failed")
    }
  }

  ////////////////////
  // State changers //
  ////////////////////
  /* Methods to alter the state of the line builder */

  def modeTitle = {
  /* Switch to title mode */
    assertNoOp
    clearData
    mode = M_TITLE
  }

  def modeIngredient = {
  /* Switch to ingredient mode */
    assertLimbo
    assertNoOp
    clearData
    mode = M_INGREDIENT
  }

  def modeMethod = {
  /* Switch to method mode */
    assertNoOp
    clearData
    mode = M_METHOD
  }

  def modeLimbo = {
  /* Switch to limbo mode (i.e. needs to switch modes) */
    assertNoOp
    clearData
    mode = M_LIMBO
  }

  def setCurrentRecipe(recipe: String) = {
  /* Set the current recipe being parsed by the program */
    currentRecipe = recipe
  }

  def setOp(newOp: ChefOp) = {
  /* Change the current operation that the line builder is building */
    // TODO make it so certain ops can't be changed (i.e. most of them)
    currentOp = newOp
  }

  def setString(newString: String) = {
  /* Set the string the line builder is holding */
    heldString = newString
  }

  def setNumber(newNumber: Int) = {
  /* Set the number the line builder is holding */
    heldNumber = newNumber
  }

  def setStackNumber1(newNumber: Int) = {
  /* Set the first stack number. */
    stackNumber1 = newNumber
  }

  def setStackNumber2(newNumber: Int) = {
  /* Set the second stack number. */
    stackNumber2 = newNumber
  }

  def clearData = {
  /* Clear all currently held line state (except the title) by resetting it 
   * to some default value. */
    currentOp = E_NONE
    heldString = ""
    heldNumber = -1
    stackNumber1 = -1
    stackNumber2 = -1
  }

  //////////////////////
  // Access Interface //
  //////////////////////
  /* Methods used to get things from the line builder */

  def finishLine = {
  /* Return a line with the appropriate information filled in. */
    // TODO

    // clear the data in preparation for the next line
    clearData
  }
}
