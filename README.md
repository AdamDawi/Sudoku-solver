# <img src="https://github.com/AdamDawi/Sudoku-solver/assets/49430055/8d67cd54-0aad-41a5-87e9-ed9490b984d6" width="60" height="60" align="center" /> Sudoku Solver

Solve your Sudoku puzzles with Sudoku Solver, app built in Jetpack Compose using Kotlin and coroutines. The app allows you to input your own Sudoku puzzle and solve it with the click of a button.

## Features
### Main Screen 
- **Sudoku Grid:** Click on Sudoku grid cells and input numbers from 1 to 9.
  
- **Clear Selected Cell:** Button to clear the currently selected cell.
  
- **Solve Sudoku:** Button to solve the entire Sudoku puzzle with backtracikng alghoritm.
  
- **Clear All Cells:** Button to clear all cells simultaneously.

### User Experience
- **Optimized Recomposition:** Efficient recompositions through proper architecture and `rememberUpdatedState` for lambdas.

- **State Preservation:** Use of `rememberSaveable` to retain state across process death and configuration changes.

## ⚙️Technologies
### 📱 App:

- **Jetpack Compose:** Leverages Jetpack Compose's declarative UI toolkit to create a modern and responsive interface.🎨

- **Clean MVVM Architecture:** Separating the project into layers with use cases and using view model functionalities.🔧

- **Manual Dependency Injection:** Applied for managing dependencies without the use of frameworks.

- **Kotlin Coroutines:** Utilized for background Sudoku solving operations on the Default dispatcher.

- **State Flow and Mutable State:** Used in ViewModel to handle UI states.


## ✅ Testing
### Unit Testing:
- **JUnit4:** Used for unit testing.
  
- **Kotlin Coroutines Test:** Testing asynchronous code that uses coroutines.

### UI Testing:
- **AndroidJUnit4:** Used for UI testing.
  
- **MockK:** Mocking library used in UI tests.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/AdamDawi/Sudoku-solver
2. Open the project in Android Studio.
3. Be sure the versions in gradle are same as on github

## Here are some overview pictures:
![11](https://github.com/AdamDawi/Sudoku-solver/assets/49430055/b092ac22-1fe9-4c83-85cb-972c635f547a)
![22](https://github.com/AdamDawi/Sudoku-solver/assets/49430055/736a5e07-c96f-4196-ad2f-22a8e251bc9c)

## Requirements
Minimum version: Android 8.0 (API level 26) or later📱

Target version: Android 14 (API level 34) or later📱

## Author

Adam Dawidziuk🧑‍💻
