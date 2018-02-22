# Java implementation of Infinity Loop Game 


## Features

* Generate a game level and store it in a .dat file.
* Check if game is solved.
* Implementation of a tree search algorithm to solve a game level.
* UI to display a given level.

## User guide 
* Compile the project with 
`mvn package compile`

* Launch each one of these commande to test features 
* Generator 
`java -jar phineloop-1.0-jar-with-dependencies.jar --generate wxh --output file`
* Checker 
`java -jar phineloop-1.0-jar-with-dependencies.jar --check file`
* Solver
`java -jar phineloop-1.0-jar-with-dependencies.jar --solve file --output filesolved`
## Result
* The search algorithm is useful to solve grid of up to 20x20 dimension.

## Team members
* M'CHICH Mohammed 
* OUAZZANI CHAHDI Nizar