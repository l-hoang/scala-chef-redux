package scalachefredux

/* This class is responsible for the actual running of the program */
class ChefRunner(state: ChefState, text: ChefText) {
  val programState = state
  val programText = text

  /* Run the program starting from line 1 */
  def run = {
    var currentLine = 1
    // TODO get rid of this, replace with something better
    var inFunction = false

    // get the last line of the main function
    val mainLastLine = programText getEndLine programState.getMainRecipe

    // TODO change this while true to something better?
    while (currentLine != mainLastLine || inFunction) {
      var nextLine = programText getLine currentLine

      nextLine match {
        case Push(ingredient, bowlNumber) => 
        case _ => throw new RuntimeException("Invalid line for ChefRunner")
      }

      currentLine += 1
    }
  }
}
