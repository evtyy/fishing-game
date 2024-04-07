# My Personal Project: Fishing Game

## What will the application do?
This is a fishing game with some challenges. To catch fish, the player needs to press the correct key (letter) that shows up. 
The player has 2 challenges each round:
1. Try to catch the largest fish in the pond within a certain number of tries
2. Try to score the highest total weight in comparison to other rounds (console version)
## Who will use it?
**Anyone** of any age can enjoy this fishing game! In general, it can be quick game to pass time. It can also be used 
as a typing game, where the player uses proper keyboard finger placement to memorize key positions. There's flexibility
in how the player wants to use the game. 


## Why is this project of interest to you?
I find fishing games relaxing, whether fishing is the main aspect 
or is a mini game within the actual game, like Dave the Diver and Stardew Valley.
I think it would be interesting to learn and understand they are designed. 
With game familiarity, I can remain enthusiastic and eager to incorporate learning outcomes 
and maybe continue adding functionality after completion.

## User Stories
### Phase 3:
- As a user, I want to be able to add fish to my collection by typing and clicking a button
- As a user, I want to be able to release caught fish by clicking a checkbox and button
- As a user, I want to be able to view a summary of fish caught
- As a user, I want to be able to save my game if I choose to
- As a user, I want to be able to load my previous game if I choose to
### Phase 2:
- As a user, I want to be able to add fish to my collection
- As a user, I want to be able to release caught fish
- As a user, I want to be able to view a list of the fish I've caught
- As a user, I want to be able to view a list of all rounds and its details (if largest fish was caught, total weight of all fish)
- As a user, I want to be able to save my sessions if I choose to
- As a user, I want to be able to load my previous sessions if I choose to
- As a user, I want to be able to be able to see how many times largest was caught / round played
- As a user, I want to be able to see average total weight

# Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by typing the correct letter and clicking the "Catch" button
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by choosing release option, checking a box next to the fish you want to release and then the "Confirm" button
- You can locate my visual component (the fish) by clicking "new game" button and observing how it changes with user input
- You can save the state of my application by clicking "yes" when given the option to save game
- You can reload the state of my application by clicking "load game" and the "load" button

### Phase 4: Task 2 (Sample of Events)
Thu Apr 04 13:30:06 PDT 2024
Caught fish with weight: 21 lb

Thu Apr 04 13:30:10 PDT 2024
Caught fish with weight: 14 lb

Thu Apr 04 13:30:13 PDT 2024
Caught fish with weight: 2 lb

Thu Apr 04 13:30:20 PDT 2024
Released fish with weight: 14 lb

Thu Apr 04 13:30:20 PDT 2024
Released fish with weight: 2 lb

### Phase 4: Task 3 (Design Reflection)
Some ways that I could refactor my code to make more robust, intuitive, and easier for future improvements involve 
re-evaluating if each class follows the Single Responsibility Principle (SRP) and consider implementing the Observer or
Singleton Pattern. Looking at my UML diagram, a lot of classes are associated with `Fishes` — high coupling. This 
prompts me to reconsider whether `Fishes` adheres to the SRP and if it should be an abstract class or interface with 
new subclasses like `FishesCaught` and `FishesTotal` to handle the two lists of `Fish`. Then, I can consider 
implementing the Observer Pattern. To illustrate, classes that keep track of the state of `FishesCaught` (Subject), like
`RoundSummary` and `NewGamePanel` can be Observers. Moreover, I tried implementing the Singleton Pattern, where 
`TotalRounds` is the Singleton because if the user decides to play multiple rounds, there should only be one instance of
`TotalRounds` that keeps track of all rounds played. `TotalRounds` can’t be a static class because `NewGamePanel` and 
`TerminalGame` need to use it as an object and modify it. However, I realized the global state caused my previous 
passing tests fail and broke some of the functionality like viewing statistics across rounds played in the console game.
If I had more time, I could probably find a way to implement the Singleton Pattern.

Additional ways include adding exceptions, removing code duplication to improve coupling, and considering the One 
Method, One Responsibility design principle. To get rid of the REQUIRES clause of 
`removeRoundSummary(RoundSummary roundSummary)` in `TotalRounds` and `releaseFish(Fish fish)` in `Fishes`, I would 
create an exception like `NotFoundException` to handle the case when the list doesn't contain the given object. For 
code duplication in the same class, I've already extracted duplicated code to a new method, like 
`showPopup(String message, int delayTime)` in `NewGamePanel` and `createCard(String cardName)` in ParentPanel. For code 
duplication in different classes, like `isLargestCaught()` in `Fishes` and `RoundSummary` that only differ slightly, I 
could use `Fishes.isLargestCaught()` in `RoundSummary` like `RoundSummary.getFishCaughtThisRound().isLargestCaught()`. 
Similarly, to follow the One Method, One Responsibility design principle, for complex methods, I can continue to extract
helper methods with intuitive names to help with code readability, like `NewGamePanel.fishCaught()` and 
`NewGamePanel.fishSwamAway()` for `NewGamePanel.catchButtonActionListener()`. These are a few refactoring strategies I 
can use to increase robustness, readability, and ease for future additions of functionality.

