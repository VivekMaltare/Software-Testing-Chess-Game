# Chess Game Application Testing

## Overview

In this project, we focused on applying **data flow graph-based testing** to a chess game application. The primary objective was to evaluate and improve the software's functionality by examining the underlying code through various testing methodologies.

To achieve this:
1. We identified and considered several key methods involved in the code base of the chess game.
2. Created **Control Flow Graphs (CFGs)** to understand the flow of the program.
3. Created **Data Flow Graphs (DFGs)** to represent variable definitions and their usage.
4. Used specialized testing tools to identify **DU (definition-use) paths** and **test paths**, focusing on:
   - **All-def path coverage**
   - **All-DU path coverage**
5. Formulated and wrote comprehensive test cases using JUnit to ensure thorough coverage and validation of the software's performance.

---

## Major Class Files

Here is a brief description of the major class files involved in our code base. Each file consists of specific methods that were considered for testing purposes:

- **`King.java`**  
  Contains the logic for the King piece, including movement and castling functionality.

- **`ChessBoard.java`**  
  Manages the state of the chessboard, including piece placement and checking for legal moves.

- **`ChessPiece.java`**  
  A base class for all chess pieces, defining common properties and methods like color, position, and basic move validation.

- **`Rook.java`**  
  Contains the specific movement logic for the Rook piece.

- **`Knight.java`**  
  Contains the specific movement logic for the Knight piece.

- **`Bishop.java`**  
  Contains the specific movement logic for the Bishop piece.

- **`Pawn.java`**  
  Contains the specific movement logic for the Pawn piece, including special rules like *en passant* and promotion.

- **`Queen.java`**  
  Contains the specific movement logic for the Queen piece.

- **`ChessUtil.java`**  
  Utility class with helper variables for the chess game, used throughout the code.

- **`ChessGame.java`**  
  Manages the game flow, alternating turns, detecting check and checkmate, and displaying the game state.

- **`Main.java`**  
  Entry point for the application.

- **`Color.java`**  
  Enum class to represent colors (white and black).

- **`ChessTest.java`**  
  Contains JUnit test cases to validate the functionality of various components in the chess game, ensuring correctness in the game logic, piece movements, and other features.

---

## Testing Approach

- **Control Flow Graphs (CFGs)**: Used to understand the flow of the program and identify execution paths.  
- **Data Flow Graphs (DFGs)**: Created to designate the flows with definitions and usage of variables.  
- **Coverage Metrics**: 
  - Focused on **All-def** and **All-DU path coverage**.  

Comprehensive test cases were formulated based on these analyses to ensure thorough testing of the application.
