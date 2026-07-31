# Fishing Game 🎣

A fishing simulation where players catch fish by typing randomly generated keyboard inputs, build a collection, and compete for the highest total fish weight across rounds.

## Demo

<img width="600" alt="fishing-demo" src="https://github.com/user-attachments/assets/4837aea5-f602-4662-8a98-347549d45a54" />


## Overview

Each round has two challenges:
1. Catch the largest fish in the pond within a limited number of attempts.
2. Achieve the highest total fish weight across rounds.

Between rounds, players can manage their collection, review statistics, and save their progress.

The typing mechanic also provides lightweight keyboard practice by encouraging players to recognize key positions and improve typing familiarity.

## Project Details

**Role:** Developer & Designer  
**Timeline:** Jan-Apr 2024  
**Type:** Personal Project  

## Features

### Fishing Mechanics 
- Catch fish by pressing the correct randomly generated key
- Compete for the largest fish and highest total weight per round
- Track fish weight and performance across rounds

### Fish Collection 
- Add caught fish to your collection
- Release unwanted fish through checkbox selection
- View collection summaries

### Statistics 
- Track total rounds played
- Record the largest fish caught and frequency of largest catches
- Calculate average total weight across rounds
- Compare performance between rounds

### Save & Load 
- Save and load game progress
- Preserve collection data and round history

## Technologies

- **Java**
- **Java Swing** for GUI development
- **JUnit** for testing
- **JSON** for persistence

## Design

Built using object-oriented programming principles with a focus on maintainability and separation of responsibilities.

| Component | Responsibility |
|---|---|
| `Fish` | Represents fish attributes such as weight and type |
| `Fishes` | Manages fish collections and fish-related operations |
| `RoundSummary` | Stores data and statistics for completed rounds |
| `TotalRounds` | Tracks statistics across multiple rounds |
| GUI Components | Handles user interaction and visual display |

<img width="600" alt="UML Design Diagram" src="https://github.com/user-attachments/assets/d91390be-4259-44ff-b73f-98f3ded94e99" />

## Design Considerations

- Applied the **Single Responsibility Principle** by separating game logic, UI, and data management responsibilities.
- Extracted reusable helper methods to reduce duplicated logic (e.g. `showPopup()`, `createCard()`).
- Evaluated design patterns such as Singleton and Observer to identify opportunities for improving state management and reducing coupling.
- Identified areas for future refactoring, including reducing dependencies around fish management.

## What I Learned

Through this project, I strengthened my understanding of:

- Object-oriented design and class relationships
- Building interactive desktop applications with Java Swing
- Managing application state and persistence
- Applying and evaluating software design principles (SRP, Observer, Singleton)
- Writing maintainable and extensible code

## Future Improvements

- Refactor fish management to further reduce coupling and improve separation of responsibilities
- Explore Observer Pattern for better state updates between components
- Add custom exceptions for improved error handling
- Introduce more fish varieties and difficulty levels
- Improve animations and visual feedback
- Expand gameplay mechanics

## How to Run

1. Clone the repository:

```bash
git clone <repository-url>
```

2. Open the project in IntelliJ IDEA or another Java IDE.

3. Choose a version to run:

### GUI Version
Run: 
```bash
GameGUI.java
```
This launches the Java Swing interface for the fishing game.

### Terminal Version

Run:

```bash
Main.java
```

This launches the console-based version of the fishing game.
