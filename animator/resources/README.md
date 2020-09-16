## Design
The animator uses a ViewModel design, with each view (visual, text, SVG) implements a view interface. The view model contains methods that can only access any information about the model that might be necessary.

 - The text view just moves the code from the toString method to the writeToOutput method given by the view interface
 - The visual view creates an panel that extends the JPanel used to animate a single tick within the animation. The frame then calls to repaint the JPanel every *x* seconds, depending on animation speed. 
 - The SVG view, instead of going through each tick, goes through all of the present shapes (which are listed chronologically) and writes the appropriate XML corresponding to the shape's keyframes and motion.

The SVG and visual view have classes for each shape to draw (rectangle and ellipse). For example, the visual view has a class DrawRectangle, which takes in a Graphics2D and calls the appropriate method to draw a rectangle unto the given Graphics2D. Similar for an ellipse and SVG view. 
Since the assignment's minimum required shapes include these two, a context class was created to only be able call the classes to draw rectangles or ellipses. However, the design allows for other classes that take in the appropriate medium and perform the necessary action to draw the given shape.

The editor view is an entirely new class which implements the IView interface. This new view is controlled using a controller that takes in the IView and an AnimatorExcellencce interfaces as arguments. The controller implements an ActionListener, which is passed through to the buttons inside the editor view, which listens to when the buttons are pressed and calls the appropriate method, either for a method or a view. The same canvas that was used in the visual view is also used here, with no changes made to it. 

The controller has two methods; one for starting the animation and it's view and another to process a command. The commands are represented by an interface with a method that takes in a view and a model. This method then implements the necessary actions with the provided view and model. Each of the different actions available through the editor is its own class that implements this Control interface. 

Also, for the tests regards this editing view. Many of the methods from the view involved return information that was expected the user to return via input through dialog boxes or getting the index of a combo box. We didn't know how to mock all of these components, like the controller for Assignment 4 (though there was this library, Mockito that seemed to be able to do so).

For the JAR, make sure you have a folder called "source_animations" in the same directory as the JAR file in case testers move the JAR around to other locations. 
## Changes
- Added an abstract view class that implements the view interface, since the editor view needed new methods which would be unsupported operations for the rest of the views

- Added a few methods to the model to manipulate the list of key frames for shapes more directly, but used existing methods to replace the motion in between frames

- Created an controller interface that takes in a view and model and listens to actions, primarily buttons to allow modification of the model from the editor view

- Controller also takes in a ViewModel, in case the view doesn't need to modify information about the model

- The main class now uses a controller to start an animation rather than calling the method to make the view visible directly.


