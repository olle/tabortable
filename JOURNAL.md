Development Journal
===================

## A diary of unforeseen events, dead-end ideas and life-sucking issues, caused by out-of-control circumstances and leaky abstractions; turtles - all the way down. ##

### 2016-02-08 - _The smell of a web framework_ ##

Oh man! I'm really getting the heebie-jeebies from Spring MVC's declarative
wank-fest. Just added the `@SessionAttributes`-annotation to my controller, and
got the most peculiar taste in the back of my throat. Oh hey, look, it's vomit.

This is a rabbit hole. But thanks to the almighty flying spaghetti monster, I'm
still sane enough to see it. For my taste, nothing good can come from sprinkling
your code with magic green-leaf-fairy-dust. For me the no-no is how testing
becomes (too) dependent on the framework tooling. It's a hook, line and sinker,
and I'm not that happy about getting caught in the foliage, just yet.

Sadly, I guess that most developers will just take the bitter medicine. And
for those who do go down the rabbit hole, probably most will never bother to
read or think about what's behind the curtain. Their motivation lies in another
place - but that's for another journal entry.

Ok, so I'm stuck. It's time to back out slowly and re-think this part of the
solution. I'm still in favor for the request mapping, and general utils for
servlet handling - let's see if I can find some structure there, that's more
transparent and better suited to plain-old-unit-testing. Imperative, service
oriented MVC is, well _a bit sucky_. Till next time.

### 2016-02-09 - _A New Hope_

Well, at least _a new try_. I'm going at it again, top-down, and will try to
keep to a clean constructor-injected, test-able, service structure - really,
this is as vanilla as it gets.

I'm trying out something a bit new, structurally separating Spring Controllers,
by their functional responsibility. A controller for table selection, sorting
etc and a minimal controller for index page requests. Curious to see how this
turns out.
