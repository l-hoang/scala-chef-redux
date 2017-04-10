# Scala-Chef-Redux

Redux of Eric, Zane, and my CS345H's Scala-Chef implementation.
https://github.com/l-hoang/scala-chef

Original Chef spec
http://www.dangermouse.net/esoteric/chef.html

## Main Differences from ScalaChef

* Arguably better organized code
* DSL cleanup instead of trying to strictly follow Chef
* Different syntax
* Different interpreation of how division is done (relative to the stack)
* Apparently better memory usage 

## Main Differences from Chef

* No heaped or level commands
* No cooking time specification
* `<num> <ingredient>` doesn't work; need to do `<num> ct <ingredient>`

TODO?

## How to Compile

The following assumes your working directly is the `src` directory, and a `bin`
directory exists in the directory above `src`.

There are dependences in the files, so you will have to compile them in an
order that satisfies the dependences. Here is one such order.

```
scalac -d ../bin/ -cp ../bin scalachefredux/Enums.scala
scalac -d ../bin/ -cp ../bin scalachefredux/ChefLines.scala
scalac -d ../bin/ -cp ../bin scalachefredux/ChefHelpers.scala
scalac -d ../bin/ -cp ../bin scalachefredux/ChefText.scala
scalac -d ../bin/ -cp ../bin scalachefredux/ChefState.scala
scalac -d ../bin/ -cp ../bin scalachefredux/LineBuilder.scala
scalac -d ../bin/ -cp ../bin scalachefredux/ChefRunner.scala
scalac -d ../bin/ -cp ../bin scalachefredux/ScalaChefRedux.scala
```

## Writing a ScalaChefRedux Program

The file you write your program in should include the following. The class
path should point to the `bin` directory so that you have access to the
`scalachefredux` class.

```
import scalachefredux._
import scala.language.postfixOps
```

Have your main method extend the ScalaChefRedux class, as in the example
below.

```
object Tester extends ScalaChefRedux {
```

You have access to ScalaChef syntax in this object due to this extension.
See the example programs and the original Chef spec in order to get an idea for
the syntax/how it works.

## Syntax

Syntax is similar, but not exactly the same as Chef. The operations are the 
ones specified in the Chef spec, so I will not go over them here. I will simply
go over the syntax.

If you see ", that means that they need to be included in the code you write.

http://www.dangermouse.net/esoteric/chef.html

All lines must be followed by a semi-colon for maximum safety (otherwise, 
I do not guarantee the code will function).

`Title - "<insert title here>"`

`Ingredients`

`Method`

`Take the/a/an/some  "<ingredient>" from the refrigerator`


`Put the/a/an/some "<ingredient>" into mixing bowl <number>`

`Put the/a/an/some "<ingredient>" into the mixing bowl`


`Fold the/a/an/some "<ingredient>" into the mixing bowl`

`Fold the/a/an/some "<ingredient>" into mixing bowl <number>`


`Add the/a/an/some "<ingredient>" [to the mixing bowl]`

`Add the/a/an/some "<ingredient>" [to mixing bowl <number>]`


`Remove the/a/an/some "<ingredient>" [from the mixing bowl]`

`Remove the/a/an/some "<ingredient>" [from mixing bowl <number>]`


`Combine the/a/an/some "<ingredient>" [into the mixing bowl]`

`Combine the/a/an/some "<ingredient>" [into mixing bowl <number>]`


`Divide the/a/an/some "<ingredient>" [into the mixing bowl]`

`Divide the/a/an/some "<ingredient>" [into mixing bowl <number>]` 


`Add dry ingredients to the mixing bowl`

`Add dry ingredients to mixing bowl <number>`


`Liquefy the "<ingredient>"`

`Liquefy the contents of the mixing bowl`

`Liquefy the contents of mixing bowl <number>`


`Stir for <number> minutes`

`Stir bowl <number> _for <number> minutes`

`Stir the bowl _for <number> minutes`

`Stir the/a/an/some "<ingredient>" into the mixing bowl`

`Stir the/a/an/some "<ingredient>" into mixing bowl <number>`


`Mix the bowl well` 

`Mix bowl <number> well`


`Clean the bowl`

`Clean bowl <number>` 


`Pour the contents of the mixing bowl into the baking dish`

`Pour the contents of mixing bowl <number> into the baking dish`

`Pour the contents of the mixing bowl into baking dish <number>`

`Pour the contents of mixing bowl <number> into baking dish <number>`


`"<verb>" the/a/an/some "<ingredient>"`

`"<verb>" the/a/an/some "<ingredient>" until "<verbed>"`

`"<verb>" until "<verbed>"`


`Set aside`


`Serve _with "<auxiliary-recipe>"`


`Refrigerate now`

`Refrigerate for <number> hours`
