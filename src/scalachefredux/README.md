# Internal DSL Construction Overview

This is a brief overview of how you can construct your objects/methods 
in Scala such that you "parse" your internal DSL through object/method calls
through a bunch of examples.

## Example 1: 3 Token Line

I want to parse the following.
```
please make something
```

First, in Scala, the above line will be translated into the following:

```
please.make(something)
```

Therefore, to "parse" this line, we need to have a `please` object
with a `make` method that takes a `something` object. Define the 
`please` object.

```
object please {

}
```

It now needs a `make` method that takes a `something` object. We need
to define the `something` object with some type that we can use to specify
as the argument type to `make`. One way to do it is the following:

```
object please {

}

abstract sealed class SomethingType
object something extends SomethingType
```

We have declared `something` as an object of type `SomethingType`. We can
now construct the `make` method.

```
object please {
  def make(s: SomethingType) = {
    // do things here
  }
}

abstract sealed class SomethingType
object something extends SomethingType
```

In this construction, arguments **must** be the 3rd token of the
line since the first token is a set-in-stone object and the second token
is a set-in-stone method.


## Example 2: 2 Token Line

```
make something
```

For this, you can define a `make` object with a `something` method that
doesn't take an argument.

```
object make {
  def something = {
    // do stuff here
  }
}
```

In this construction, your argument is the 2nd token of the line. It
may require the use of parenthesis, however, which may not be preferred.

```
make (something)
```

## Example 3: 5 Token Line

```
make some simple things 345
```

Scala interprets the above as the following:

```
(make.some(simple)).things(345)
```

We can handle `make.some(simple)` similar to how we handled
the 3 token line:

```
object make {
  def some(s: SimpleType) = {
    // do things here
  }
}

abstract sealed class SimpleType
object simple extends SimpleType
```

We need to handle the call to the method `things` with argument 345.
The object `make.some(simple)`, then, needs to have a `things` method
that accepts an integer argument. To accomplish this, you have to return
an object with that property.

We define such an object:

```
object make {
  def some(s: SimpleType) = {
    // do things here

    ThingGetter
  }

  object ThingGetter {
    def things(i: Int) = {
  
    }
  }
}

abstract sealed class SimpleType
object simple extends SimpleType

```

I added `ThingGetter` to the end of the `some` method to that it is returned
from that method. On successful completion of the first method call, then, we
are left with the following:

```
ThingGetter.things(345)
```

This will parse correctly and call the `things` method with argument 345.


In this construction, without parenthesis usage, your arguments must be the
3rd and 5th token of the line.

## Example 4: 4 Token Line

```
clean up this mess
```

Scala sees the above as the following:

```
(clean.up(this)).mess
```

`clean.up(this)` needs to return a class that has a mess method that takes no arguments.

```
object clean {
  def up(t: ThisType) = {
    // do things here

    MessGetter
  }

  object MessGetter {
    def mess = {
  
    }
  }
}

abstract sealed class ThisType
object this extends ThisType

```

In this scheme, only the 3rd token can act as your variable. The other 3 
tokens must be predefined in the code in order for the parsing to occur.

## Example 5: 1 Token Line

```
clean
```

All you should have to do is define the function `clean`.

```
def clean = {
  // do things here
}
```

## 6 Tokens and Beyond

You should be able to generalize the above examples to any number of tokens.

Keep in mind that the first 3 tokens of a line will always be grouped into 
`object.method(argument)`. Afterwards, every 2 tokens will be grouped into a 
method call with some argument (if an argument exists). You can have 
arbitrarily long chains such as this:

```
(((object.method(argument)).method(argument)).method(argument)).method(argument)
```

Each successive method call needs to return an object that as a method with the
name of the next token, and the method must take an argument of the token 
after that if your line allows a token after that.

## Limitations

### 3-5-7-9-... Arguments 

If you want to keep your lines parenthesis-free, then Scala will generally 
always parse the lines above with the 3-2-2-2-2... calling order. The 
consequence of this is that your arguments generaly can only appear as the 
3rd-5th-7th-...  tokens of a line.

One way to work around this is to add filler words. Say I wanted to make the
second token of this line an argument.

```
Take 30 pieces
```

Using the constructions above, 30 cannot be your argument. What you do is to move
it to the 3rd token slot with a filler word.

```
Please take 30 pieces
```

You could then parse this line with the following construction:

```
object Please {
  def take(num: Int) = {
    // do things

    PieceGetter
  }

  object PieceGetter {
    def pieces = {
      // do things
    }
  }
}
```

The consequence is that it makes your lines unnecessarily verbose, and it may
stray from an original specification if you are converting another language 
into a DSL.

### Reserved Keywords

Say I wanted to have the following line in my internal DSL.

```
Clean while bored
```

This line is not possible: `while` is a reserved keyword for loops in Scala.
Generally speaking, reserved Scala keywords cannot become keywords in your DSL.


## The Dynamic Class: Another Possibility for 3 Token Lines

The Dynamic class is able to provide one more particular option for 3 token
lines: if 2nd token is a string and the 3rd token a string/number, 
it allows you to use the 2nd token as an argument and potentially
the 3rd token as well.

```
goto first house
```

Say `first` is an argument in this line. The Dynamic class
will allow us to parse it. (I assume `goto` isn't a keyword in Scala;
I would think `goto`s don't exist, but who knows.)

```
object goto extends Dynamic {
  object house

  def applyDynamic(firstArg: String)(args: Any*) = {
    // in the above example, firstArg would contain "first",
    // and args(0) would contain "house"

  }
}
```

Your object must extend `Dynamic`. You must also define the `applyDynamic`
method with the above signature (though `firstArg` and `args` can probably
be named something else). Additionally, you define the 3rd token
as an object (here I defined `object house`). 

Alternatively, if your 3rd token is a string wrapped in quotations or
an integer, then you can use the 3rd token as an argument as well:


```
clean first 100
clean first "testing"
```

With the above 2 lines, the same code from above would work without needing
to define a testing or 100 object: `args(0)` would contain 100 and "testing"
respectively.


I believe you cannot extend this to any more than 3 tokens without parenthesis.
Say I wanted to do something like this:

```
clean first 100 houses
```

Recall Scala reads this as `(clean.first(100)).houses`. `applyDynamic` works
by treating the 2nd token as a method call even if the method is undefined
(note we didn't have to define `first` as a method). This method call
can take an arbitrary number of arguments, but such arguments
must be wrapped in parens. Therefore, without parens, Dynamic does not 
get around the 3-2-2-2... call structure Scala has for parsing its
space separated lines.

If you only had 3 tokens, then Scala handles it since you 
have your `object.method(argument)` construction, which works with `applyDynamic`.
The second argument is your method call, and the third is the argument. Since
the method call itself is translated into a String, you can use it as an
argument as well.


## Final Notes

These notes don't go in any particular section. **However, they contain
potentially important information. Please read it over.**

* Note that in some cases Scala will require parenthesis around your arguments.
Usually the way around this if you don't like the parenthesis is to construct
your syntax such that you have your arguments that aren't "keywords" go
in the 3rd-5th-7th-9th-... slots of your lines.

* Say you have these things:

```
please make something
hello

please make something
hello world
```

Scala will interpret these as the following:

```
(please.make(something)).hello

(please.make(something)).hello(world)
```

The new line between `something` and `hello` basically does not exist to Scala.
To prevent this from happening, you can make it so it's a blank line between
`something` and `hello` or add a semi-colon to the end of the line.

```
please make something;
hello

please make something

hello world
```

Both options aren't aesthetically pleasing, but I believe they're the only
ways to prevent this from happening.

* You **cannot** have an object and a function in the same scope share the
same name in Scala, which may prevent certain syntactical constructions.
