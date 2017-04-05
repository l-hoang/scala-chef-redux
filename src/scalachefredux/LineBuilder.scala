package scalachefredux

class LineBuilder {
/* This class builds up Chef lines and returns them upon request. */
  
  abstract sealed class ParseMode

  case object M_TITLE extends ParseMode
  case object M_INGREDIENT extends ParseMode
  case object M_METHOD extends ParseMode
  case object M_LIMBO extends ParseMode
  case object M_END extends ParseMode
  case object M_DONE extends ParseMode


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
  
  // TODO kill if not needed
  case object NULLINGREDIENT extends ChefIngredient("null", I_NULL)
  
  // Various things that need to be used to build lines
  var heldString = ""
  var heldNumber = -1
  var stackNumber1 = -1 
  var stackNumber2 = -1
  // TODO kill if not needed
  var heldIngredient: ChefIngredient = NULLINGREDIENT

  // mark if a line is ready to be returned
  var lineFinished = false

  var first_marker = false

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

  /* Make sure the mode isn't end mode */
  def assertNotDone = {
    mode match {
      case M_DONE => throw new RuntimeException("Assert not done failed")
      case _ => 
    }
  }

  /* Line finished assertion */
  def assertLineFinished = {
    if (!lineFinished) {
      throw new RuntimeException("ERROR: line not marked finished")
    }
  }

  ////////////////////
  // State changers //
  ////////////////////
  /* Methods to alter the state of the line builder */

  /* Switch to title mode */
  def modeTitle = {
    assertNotDone
    assertNoOp
    clearData
    mode = M_TITLE
  }

  /* Switch to ingredient mode */
  def modeIngredient = {
    assertNotDone
    assertLimbo
    assertNoOp
    clearData
    mode = M_INGREDIENT
  }

  /* Switch to method mode */
  def modeMethod = {
    assertNotDone
    assertNoOp
    assertIngredient
    clearData
    mode = M_METHOD
  }

  /* Switch to limbo mode (i.e. needs to switch modes) */
  def modeLimbo = {
    assertNotDone
    assertNoOp
    clearData
    mode = M_LIMBO
  }

  /* Switch to end mode (i.e. needs to switch to title mode if another 
   * function exists) */
  def modeEnd = {
    assertNoOp
    clearData
    mode = M_END
  }

  /* Set to this mode = can't do anything else */
  def modeDone = {
    assertNoOp
    clearData
    mode = M_DONE
  }

  /* Set the current recipe being parsed by the program */
  def setCurrentRecipe(recipe: String) = currentRecipe = recipe

  /* Change the current operation that the line builder is building */
  def setOp(newOp: ChefOp) = {
    assertMethod
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
  // TODO kill if not needed
  def setIngredient(newIngredient: ChefIngredient) = {
    assertIngredient
    heldIngredient = newIngredient
  }

  /* Set lineFinished to true */
  def setFinished = lineFinished = true

  /* Clear all currently held line state (except the title) by resetting it 
   * to some default value. */
  def clearData = {
    currentOp = E_NONE
    heldIngredient = NULLINGREDIENT // TODO kill if not needed
    heldString = ""
    heldNumber = -1
    stackNumber1 = -1
    stackNumber2 = -1
    lineFinished = false
  }

  //////////////////////
  // Access Interface //
  //////////////////////
  /* Methods used to access the line builder */



  /* Return a line with the appropriate information filled in. */
  def finishLine = {
    // TODO
    assertLineFinished
    var toReturn: ChefLine = currentOp match {
      case E_TAKE => Read(heldString)
      case E_PUT => Push(heldString, stackNumber1)
      case E_FOLD => Pop(stackNumber1, heldString)
      case E_LIQUEFY => Liquefy(heldString)
      case E_LIQUEFY_CONTENTS => LiquefyContents(stackNumber1)
      case E_POUR => CopyStack(stackNumber1, stackNumber2)
      case E_CLEAN => ClearStack(stackNumber1)
      case E_SERVES => PrintStacks(heldNumber)
      case _ => throw new RuntimeException("Valid op not set for finish line")
    }

    if (currentOp == E_SERVES) {
      clearData
      modeEnd
    } else {
      clearData
    }

    toReturn
  }
}
