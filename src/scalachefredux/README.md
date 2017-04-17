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
}

abstract sealed class SimpleType
object simple extends SimpleType

object ThingGetter {
  def things(i: Int) = {

  }
}
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

## The Dynamic Class

The Dynamic class can provide a bit more options in terms of where you want your
arguments to be placed in a line.



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
