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

    // initialize the starting ingredients
    programState initializeIngredients programText

    // TODO change this while true to something better?
    while (currentLine != mainLastLine || inFunction) {
      var nextLine = programText getLine currentLine

      nextLine match {
        case Read(ingredient) => print("read " + ingredient);
          programState.take(ingredient)

        case Push(ingredient, bowlNumber) => println("push " + ingredient);
          programState.pushToBowl(ingredient, bowlNumber)

        case Pop(bowlNumber, ingredient) => println("pop " + bowlNumber);
          programState.popToIngredient(bowlNumber, ingredient)
        
        case Add(ingredient, bowlNumber) => println("add " + ingredient);
          programState.addOp(ingredient, bowlNumber)

        case Subtract(ingredient, bowlNumber) => println("subtract " + ingredient);
          programState.subOp(ingredient, bowlNumber)

        case Multiply(ingredient, bowlNumber) => println("multiply " + ingredient);
          programState.mulOp(ingredient, bowlNumber)

        case Divide(ingredient, bowlNumber) => println("divide " + ingredient);
          programState.divOp(ingredient, bowlNumber)

        case AddDry(bowlNumber) => println("add dry " + bowlNumber);
          programState.addDry(bowlNumber)

        case Liquefy(ingredient) => println("liquefy " + ingredient);
          programState.liquefyIngredient(ingredient)

        case LiquefyContents(bowlNumber) => println("liquefy " + bowlNumber);
          programState.liquefyBowl(bowlNumber)

        case ClearStack(bowlNumber) => println("clear " + bowlNumber);
          programState.clearBowl(bowlNumber)

        case CopyStack(bowlNumber, dishNumber) => 
          println("copy " + bowlNumber + dishNumber)
          programState.bowlToDish(bowlNumber, dishNumber)

        case PrintStacks(numToPrint) => println("print stacks " + numToPrint);
          programState.printDishes(numToPrint)

        case x => throw new RuntimeException("Invalid line for ChefRunner " + x)
      }

      currentLine += 1
    }
  }
}
