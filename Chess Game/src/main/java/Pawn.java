public class Pawn extends ChessPiece{

    public Pawn(int x, int y, COLOR color, ChessBoard chessBoard) {
        super(x, y, color, chessBoard);
    }

    @Override
    public boolean movePossible(int Xi, int Yi){
        if(VerifyMove(Xi,Yi)){
            return verifyPawnmove(Xi, Yi);
        }
        return false;
    }

    public boolean verifyPawnmove(int toNewX, int toNewY){
        int moveOneStep;
        int moveTwoStep;

        int currX = this.getX();
        int currY = this.getY();

        COLOR currColor = this.getColor();

        ChessPiece pieceAtNewXY = chessBoard.piecePosition(toNewX, toNewY);
        boolean hasMoved = this.hasMoved;

        if (currColor == COLOR.BLACK) {
            moveOneStep = 1;
            moveTwoStep = 2;
        } else {
            moveOneStep = -1;
            moveTwoStep = -2;
        }

        // Conditions for moving the pawn one step forward
        if ((toNewY - currY) == moveOneStep) {
            // to move one step straight forward check if there is no piece at the new location
            if (toNewX == currX && pieceAtNewXY == null) {
                promotePawn();
                return true;
            }

            // to move one step diagonally check if there is a piece already at that new location
            if (Math.abs(currX - toNewX) == 1 && pieceAtNewXY != null) {
                promotePawn();
                return true;
            }
        }
        // Conditions for moving the pawn two steps forward
        // this is only possible if the pawn has never moved before in the entire game play
        else if (!hasMoved) {
            if ((toNewY - currY) == moveTwoStep) {
                // Ensure there is no piece between current position and destination
                int intermediateY = currY + (moveOneStep); // Intermediate step (either +1 or -1 depending on color)
                ChessPiece pieceAtIntermediate = chessBoard.piecePosition(currX, intermediateY);

                if (toNewX == currX && pieceAtNewXY == null && pieceAtIntermediate == null) {
                    promotePawn();
                    return true;
                }
            }
        }
        return false;
    }

    // Add pawn promotion logic
    public void promotePawn() {
        int currentX = this.getX();
        int currentY = this.getY();

        // Check if the pawn has reached the promotion row
        if ((this.getColor() == COLOR.WHITE && currentY == 0) || (this.getColor() == COLOR.BLACK && currentY == 7)) {

            // Remove the pawn first to clear the spot
            chessBoard.removePiece(this);

            // Promote the pawn to a new piece (e.g., Queen)
            ChessPiece promotedPiece = new Queen(currentX, currentY, this.getColor(), chessBoard);

            // Place the promoted piece (Queen) on the board
            chessBoard.newPosition(promotedPiece, currentX, currentY);

        }
    }

    public boolean checkEnPassant(int toNewX, int toNewY) {
        int currX = this.getX();
        int currY = this.getY();
        int moveDirection = (getColor() == COLOR.BLACK) ? 1 : -1; // Direction based on pawn color

        // Ensure target square is diagonal and one step forward (based on color)
        if (Math.abs(currX - toNewX) == 1 && (toNewY - currY) == moveDirection) {

            // Check the last moved piece on the board
            ChessPiece lastMovedPiece = chessBoard.getMovedPiece();

            if (lastMovedPiece instanceof Pawn) {
                Pawn opponentPawn = (Pawn) lastMovedPiece;

                // Check that the opponent's pawn moved two squares forward in its last move
                if ((opponentPawn.getColor() == COLOR.BLACK && opponentPawn.getY() == 6 && this.getY() == 6) ||
                        (opponentPawn.getColor() == COLOR.WHITE && opponentPawn.getY() == 3 && this.getY() == 3)) {

                    // Ensure the opponent's pawn is in the correct column next to the current pawn
                    if (opponentPawn.getX() == toNewX) {
                        return true; // Valid en passant
                    }
                }
            }
        }
        return false; // No valid en passant
    }


    public void enPassantCapture(int toNewX, int toNewY) {
        // Determine the direction based on the color of the pawn
        // Black pawn moves downward (y increases), White pawn moves upwards (y decreases)
        int opponentY = (getColor() == COLOR.BLACK) ? toNewY - 1 : toNewY + 1;

        // Now, check for the opponent pawn at the previous position where it just passed
        ChessPiece opponentPawn = chessBoard.piecePosition(toNewX, opponentY);

        // Log for debugging
        System.out.println("Checking for opponent pawn at: (" + toNewX + ", " + opponentY + ")");
        System.out.println("Opponent Pawn: " + opponentPawn);

        // En Passant logic: if an opponent pawn is found in the previous position and it belongs to the opposite color
        if (opponentPawn instanceof Pawn && opponentPawn.getColor() != this.getColor()) {
            // Remove the opponent pawn (en passant)
            chessBoard.removePiece(opponentPawn);

            // Move the capturing pawn to the new position (toNewY)
            this.newPos(toNewX, toNewY);

            // Log success
            System.out.println("En Passant capture successful!");
        } else {
            // Log failure if no opponent pawn is found at the expected position
            System.out.println("No opponent pawn found or invalid capture position.");
        }
    }
}
