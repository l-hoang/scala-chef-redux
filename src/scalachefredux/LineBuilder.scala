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
  
  case object NULLINGREDIENT extends ChefIngredient("null", I_NULL)
  
  // Various things that need to be used to build lines
  var heldString = ""
  var heldNumber = -1
  var stackNumber1 = -1 
  var stackNumber2 = -1
  var heldIngredient: ChefIngredient = NULLINGREDIENT


  ////////////////
  // Assertions //
  ////////////////
  /* Assert certain things about the current line builder state */

  /* Title assertion */
  def assertTitle = {
    mode match {
      case M_TITLE =>
      case _ => throw new RuntimeException("Assert title mode failed")
    }
  }

  /* Ingredient assertion */
  def assertIngredient = {
    mode match {
      case M_INGREDIENT =>
      case _ => throw new RuntimeException("Assert ingredient mode failed")
    }
  }

  /* Method assertion */
  def assertMethod = {
    mode match {
      case M_METHOD =>
      case _ => throw new RuntimeException("Assert method mode failed")
    }
  }

  /* Limbo assertion */
  def assertLimbo = {
    mode match {
      case M_LIMBO =>
      case _ => throw new RuntimeException("Assert limbo mode failed")
    }
  }

  /* Assert there is currently no op set in the line builder */
  def assertNoOp = {
    currentOp match {
      case E_NONE =>
      case _ => throw new RuntimeException("Assert no op failed")
    }
  }

  ////////////////////
  // State changers //
  ////////////////////
  /* Methods to alter the state of the line builder */

  /* Switch to title mode */
  def modeTitle = {
    assertNoOp
    clearData
    mode = M_TITLE
  }

  /* Switch to ingredient mode */
  def modeIngredient = {
    assertLimbo
    assertNoOp
    clearData
    mode = M_INGREDIENT
  }

  /* Switch to method mode */
  def modeMethod = {
    assertNoOp
    clearData
    mode = M_METHOD
  }

  /* Switch to limbo mode (i.e. needs to switch modes) */
  def modeLimbo = {
    assertNoOp
    clearData
    mode = M_LIMBO
  }

  /* Set the current recipe being parsed by the program */
  def setCurrentRecipe(recipe: String) = currentRecipe = recipe

  /* Change the current operation that the line builder is building */
  def setOp(newOp: ChefOp) = {
    // TODO make it so certain ops can't be changed (i.e. most of them)
    currentOp = newOp
  }

  /* Set the string the line builder is holding */
  def setString(newString: String) = heldString = newString
  /* Set the number the line builder is holding */
  def setNumber(newNumber: Int) = heldNumber = newNumber 
  /* Set the first stack number. */
  def setStackNumber1(newNumber: Int) = stackNumber1 = newNumber
  /* Set the second stack number. */
  def setStackNumber2(newNumber: Int) = stackNumber2 = newNumber

  /* Set the held ingredient. */
  def setIngredient(newIngredient: ChefIngredient) = {
    assertIngredient
    heldIngredient = newIngredient
  }

  /* Clear all currently held line state (except the title) by resetting it 
   * to some default value. */
  def clearData = {
    currentOp = E_NONE
    heldIngredient = NULLINGREDIENT
    heldString = ""
    heldNumber = -1
    stackNumber1 = -1
    stackNumber2 = -1
  }

  //////////////////////
  // Access Interface //
  //////////////////////
  /* Methods used to get things from the line builder */

  /* Return a line with the appropriate information filled in. */
  def finishLine = {
    // TODO

    // clear the data in preparation for the next line
    clearData
  }
}
