# My Personal Project

## A Café

This application will act as a *cafe menu*. It will feature cafe drinks and the price of the 
drinks. Users will be able to add new drinks with the corresponding price and be able to search
for preexisting menu items. Users may also add a price limit to their search. 

This application can be used by the baristas and the customers of the cafe. Baristas will be 
able to add **new** menu items, along with the price, while the customer is able to view the 
menu, check the price of specific menu items and to see which items fit *their* price limits. 
This project is interesting to me because during quarantine, I've been experimenting with lots of
new cafe drinks and baking recipes, so I incorporated some recipes that I absolutely **love**.

## User Stories  

As a user, I want to be able to add items to the menu with its name and price.  
As a user, I want to be able to see the names of all the drinks on the menu.  
As a user, I want to be able to view the prices of menu items.  
As a user, I want to be able to put a filter on certain price points on the menu.  
As a user, I want to be able to save my menu to file.  
As a user, I want to be able to load my menu from file.  

## Phase 4: Task 2
Test and design a class in your model package that is robust.  You must have at least one method that throws a 
checked exception.  You must have one test for the case where the exception is expected and another where the 
exception is not expected.

The addMenuItem method in the Menu class throws a DuplicateItemException when an item added has the same name as a 
preexisting item in the menu.

## Phase 4: Task 3

If I had more time to work on the project, I could refactor parts of the classes in the UI package to improve cohesion.
This could improve design and make the code more readable. I would not necessarily refactor the design in the model 
package as it is fairly simple. I would also refactor methods in the UI package so that they were shorter and more 
precise. This would also improve the readability and cohesion in the code and make it easier to change the functionality 
in the code later on.
