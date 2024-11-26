import java.util.ArrayList;
import java.util.Scanner;

public class ChessGame {
    private ChessBoard chessBoard;
    private COLOR currentPlayer;

    private ArrayList<ChessPiece> blackPieces, whitePieces;

    private King whiteKing, blackKing;

    public ChessGame() {
        this.chessBoard = new ChessBoard(this);
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
    }

    public void setCurrentPlayer(COLOR currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<ChessPiece> getBlackPieces() {
        return blackPieces;
    }

    public ArrayList<ChessPiece> getWhitePieces() {
        return whitePieces;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    /** Layout of Chess board with coordinate axes

                        BLACK PLAYER

                    0  1  2  3  4  5  6  7 → X
                0
                1
                2
                3
                4
                5
                6
                7
                ↓
                Y

                        WHITE PLAYER
    */


    private void gameSetup() {
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();

        currentPlayer = COLOR.WHITE; // set current player to white

        // put all the BLACK pawns
        addPawn(0, 1, COLOR.BLACK);
        addPawn(1, 1, COLOR.BLACK);
        addPawn(2, 1, COLOR.BLACK);
        addPawn(3, 1, COLOR.BLACK);
        addPawn(4, 1, COLOR.BLACK);
        addPawn(5, 1, COLOR.BLACK);
        addPawn(6, 1, COLOR.BLACK);
        addPawn(7, 1, COLOR.BLACK);


        // put black rooks
        addRook(0, 0, COLOR.BLACK);
        addRook(7, 0, COLOR.BLACK);

        // put black knight
        addKnight(1, 0, COLOR.BLACK);
        addKnight(6, 0, COLOR.BLACK);

        // put black bishops
        addBishop(2, 0, COLOR.BLACK);
        addBishop(5, 0, COLOR.BLACK);

        // put black queen
        addQueen(3, 0, COLOR.BLACK);

        // put black king
        addKing(4, 0, COLOR.BLACK);

        // put all the WHITE pawns
        addPawn(0, 6, COLOR.WHITE);
        addPawn(1, 6, COLOR.WHITE);
        addPawn(2, 6, COLOR.WHITE);
        addPawn(3, 6, COLOR.WHITE);
        addPawn(4, 6, COLOR.WHITE);
        addPawn(5, 6, COLOR.WHITE);
        addPawn(6, 6, COLOR.WHITE);
        addPawn(7, 6, COLOR.WHITE);


        // put white rooks
        addRook(0, 7, COLOR.WHITE);
        addRook(7, 7, COLOR.WHITE);

        // put white knight
        addKnight(1, 7, COLOR.WHITE);
        addKnight(6, 7, COLOR.WHITE);

        // put white bishops
        addBishop(2, 7, COLOR.WHITE);
        addBishop(5, 7, COLOR.WHITE);

        // put white queen
        addQueen(3, 7, COLOR.WHITE);

        // put white king
        addKing(4, 7, COLOR.WHITE);

    }

    public Pawn addPawn(int x, int y, COLOR color) {
        Pawn pawn = new Pawn(x, y, color, this.chessBoard);
        addPiece(pawn);
        return pawn;
    }

    public Rook addRook(int x, int y, COLOR color) {
        Rook rook = new Rook(x, y, color, this.chessBoard);
        addPiece(rook);
        return rook;
    }

    public Knight addKnight(int x, int y, COLOR color) {
        Knight knight = new Knight(x, y, color, this.chessBoard);
        addPiece(knight);
        return knight;
    }

    public Bishop addBishop(int x, int y, COLOR color) {
        Bishop bishop = new Bishop(x, y, color, this.chessBoard);
        addPiece(bishop);
        return bishop;
    }

    public Queen addQueen(int x, int y, COLOR color) {
        Queen queen = new Queen(x, y, color, this.chessBoard);
        addPiece(queen);
        return queen;
    }

    public King addKing(int x, int y, COLOR color) {
        King king = new King(x, y, color, this.chessBoard);
        if (color == COLOR.WHITE) {
            this.whiteKing = king;
        } else {
            this.blackKing = king;
        }
        addPiece(king);
        return king;
    }

    private void addPiece(ChessPiece piece) {
        if(piece.getColor() == COLOR.BLACK) {
            this.blackPieces.add(piece);
        } else {
            this.whitePieces.add(piece);
        }
    }


    // call this method to start the game
    public void start() {
        gameSetup();
        Scanner userInput = new Scanner(System.in);
        // while game is not over
        while(!isGameOver()) {
            this.chessBoard.display();

            System.out.println("Player turn: " + currentPlayer.toString());

            System.out.println("What piece to move?");
            System.out.print("X loc: ");
            int currX = userInput.nextInt();
            System.out.print("Y loc: ");
            int currY = userInput.nextInt();

            ChessPiece playerPiece = this.chessBoard.piecePosition(currX, currY);

            int newX;
            int newY;
            ChessPiece pieceAtXY = null;
            boolean moved;

            if(playerPiece == null) {
                System.out.println("No piece at given location!");
                continue;
            } else if(playerPiece.getColor() != currentPlayer) {
                System.out.println("You picked other player piece!");
                continue;
            } else {
                System.out.println("Where to place?");
                System.out.print("X loc: ");
                newX = userInput.nextInt();
                System.out.print("Y loc: ");
                newY = userInput.nextInt();

                pieceAtXY = this.chessBoard.piecePosition(newX, newY);

                moved = playerPiece.hasMoved;

                // check if a player can move playerPiece to new location
                if(!playerPiece.movePossible(newX, newY)) {
                    System.out.println("Invalid move!");
                    continue;
                } else {
                    playerPiece.newPos(newX, newY);
                }
            }

            // after the player's move the king should not have any check
            if(isKingInCheck(currentPlayer)) {
                reverseMove(playerPiece, pieceAtXY, currX, currY, newX, newY, moved);
                System.out.println("Check on the king, please play any other move!");
                continue;
            }

            // change the player turn if move is valid
            if(currentPlayer == COLOR.WHITE)
                currentPlayer = COLOR.BLACK;
            else if(currentPlayer ==  COLOR.BLACK)
                currentPlayer = COLOR.WHITE;
        }

    }

    // reverse the move played by a player
    private void reverseMove(ChessPiece playerPiece, ChessPiece pieceAtXY, int startX, int startY, int endX, int endY, boolean moved) {
        // reverse the move

        this.chessBoard.newPosition(playerPiece, startX, startY);
        playerPiece.setX(startX);
        playerPiece.setY(startY);
        playerPiece.hasMoved = moved;

        if(pieceAtXY != null) {
            this.chessBoard.newPosition(pieceAtXY, endX, endY);
            // put back the removed piece in. the arraylist
            if(pieceAtXY.getColor() == COLOR.WHITE) {
                this.whitePieces.add(pieceAtXY);
            } else {
                this.blackPieces.add(pieceAtXY);
            }
        } else {
            this.chessBoard.newPosition(null, endX, endY);
        }
    }

    // check if other player had given any check to current player
    public boolean isKingInCheck(COLOR currentPlayer) {
        King currentPlayerKing;
        ArrayList<ChessPiece> otherPlayerPieces;

        currentPlayerKing = currentPlayer == COLOR.WHITE ? this.whiteKing : this.blackKing;
        otherPlayerPieces = currentPlayer == COLOR.WHITE ? this.blackPieces : this.whitePieces;


        int kingX = currentPlayerKing.getX();
        int kingY = currentPlayerKing.getY();

        // if any of other player piece attack king then it is checked, else not check
        for(ChessPiece piece: otherPlayerPieces) {
            if(piece.movePossible(kingX, kingY)) {
                return true;
            }
        }
        return false;
    }

    // if check and no move to play then checkmate
    public boolean isCheckMate(COLOR currentPlayer) {
        if(isKingInCheck(currentPlayer) && !doesPlayerHaveAnyMove(currentPlayer)) {
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        COLOR currentPlayer = this.currentPlayer;
        if(isCheckMate(currentPlayer)) {
            this.chessBoard.display();
            if(currentPlayer == COLOR.WHITE) {
                System.out.println("CHECKMATE: Black Player Won");
            } else {
                System.out.println("CHECKMATE: White Player Won");
            }
            return true;
        } else if(!doesPlayerHaveAnyMove(currentPlayer)) {
            this.chessBoard.display();
            System.out.println("STALEMATE!");
            return true;
        }
        return false;
    }

    // check if a player have any moves to play
    // if not then either it is checkmate or stalemate
    private boolean doesPlayerHaveAnyMove(COLOR currentPlayer) {
        ArrayList<ChessPiece> currentPlayerPieces = currentPlayer == COLOR.WHITE ? this.whitePieces : this.blackPieces;

        for(int x = 0; x < ChessUtil.SIZE; x++) {
            for(int y = 0; y < ChessUtil.SIZE; y++) {
                for(ChessPiece piece: currentPlayerPieces) {

                    // check if piece has a move to x, y
                    if(piece.movePossible(x, y)) {
                        ChessPiece pieceAtXY = this.chessBoard.piecePosition(x, y);
                        int currX = piece.getX();
                        int currY = piece.getY();
                        boolean moved = piece.hasMoved;
                        piece.newPos(x, y);

                        // after the move check if king is still in check

                        if(!isKingInCheck(currentPlayer)) {

                            // reverse the play
                            reverseMove(piece, pieceAtXY, currX, currY, x, y, moved);
                            // if king is not in check after this move we will return true
                            return true;
                        } else {
                            // reverse the play
                            reverseMove(piece, pieceAtXY, currX, currY, x, y, moved);
                        }
                    }
                }
            }
        }

        return false;
    }
}
