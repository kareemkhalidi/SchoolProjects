Here is the submission for Assignment 2 by Kareem Khalidi and John Gauci.

MVC:

MVC is implemented with the subject, observer, and mediator classes. There is also a documentState
class that is used less of a state and more of a container for all document variables.
In this state is a single-dimension list of all glyphs in the document and important values.

Design Patterns:

1. Glyph and Composite: No deviation except additional variables in Glyph.

2. Formatting and Strategy: Since the documentState has a list of all glyphs, the compose function
builds a recursive composite structure based off the list of all glyphs for each PaintEvent.

3. Scrollbar and Decorator: This is implemented almost as close to the book, as it is transparent on
 top of the main composite, but does call draw on the rows of the main composite selectively. This
 is because the book solution of clipping graphical bounds did not provide full scroll functionality
 .

 4. NA

 5. NA

 6. Undo, Redo and Command: No deviation, a CommandList class is used to house the list of commands
 the book describes and add/undo/redo with it.

 7. Spell checking, hyphenation, Iterator, and Visitor: No deviation, the misspelled words are shown
 in a popup window.