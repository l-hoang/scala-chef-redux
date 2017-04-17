# Internal DSL Construction Overview

This is a brief overview of how you can construct your objects/methods 
in Scala such that you "parse" your internal DSL through object/method calls
through a bunch of examples.

## Example 1

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

## Example 2

## Final Notes

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
