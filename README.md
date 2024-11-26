In this project work, we have focused on applying data flow graph-based testing to a chess game application. The primary objective was to evaluate and improve the software's functionality by examining the underlying code through various testing methodologies. To achieve this, we first identified and considered several key methods involved in the code base of the chess game. We then created control flow graphs and data flow graphs to visually represent the program's structure and execution paths. Control flow graphs are created for the sake of understanding the flow of program and data flow graphs are created to designate that flows with definitions and usage of variables. Using specialized testing tools, we identified DU (definition-use) paths and test paths, specifically focusing on all-def and all-du path coverage. Based on these analyses, we formulated and wrote comprehensive test cases to ensure thorough coverage and validation of the software's performance.
Here’s a brief description of major class files involved in our code base. Every file consists of some specific methods that are important and considered for testing purposes. 
•	King.java: Contains the logic for the King piece, including movement and castling functionality.
•	ChessBoard.java: Manages the state of the chessboard, including piece placement, checking for legal moves etc. 
•	ChessPiece.java: A base class for all chess pieces, defining common properties and methods like color, position, and basic move validation. 
•	Rook.java: Contains the specific movement logic for the Rook piece.
•	Knight.java: Contains the specific movement logic for the Knight piece.
•	Bishop.java: Contains the specific movement logic for the Bishop piece.
•	Pawn.java: Contains the specific movement logic for the Pawn piece, including special rules like en passant and promotion.
•	Queen.java: Contains the specific movement logic for the Queen piece.
•	ChessUtil.java: Utility class with helper variables for the chess game, used throughout the code.
•	ChessGame.java: Manages the game flow, alternating turns, detecting check and checkmate, and displaying the game state.
•	Main.java: Entry point for the application. 
•	Color.java: Enum class to represent colors(white and black).
•	ChessTest.java: Contains JUnit test cases to validate the functionality of various components in the chess game, ensuring correctness in the game logic, piece movements, and other features.
