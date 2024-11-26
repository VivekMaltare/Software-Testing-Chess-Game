public class King extends ChessPiece {
    private boolean hasMoved = false; // Track if the king has moved

    public King(int x, int y, COLOR color, ChessBoard chessBoard) {
        super(x, y, color, chessBoard);
    }

    @Override
    public boolean movePossible(int Xi, int Yi) {
        if (VerifyMove(Xi, Yi)) {
            if (verifyKingMove(Xi, Yi)) {
                return true;
            }
            // Handle castling logic
            if (!hasMoved && canCastle(Xi, Yi)) {
                return true;
            }
        }
        return false;
    }
    public boolean verifyKingMove(int Xi, int Yi) {
        int absX = Math.abs(Xi - this.getX()); // absolute difference between new X location and current X location
        int absY = Math.abs(Yi - this.getY()); // absolute difference between new Y location and current Y location

        if (absX <= 1 && absY <= 1) {
            return absX != 0 || absY != 0;
        }
        return false;
    }

    private boolean canCastle(int Xi, int Yi) {
        if (getY() != Yi) {
            return false; // Castling only occurs in the same row
        }

        int rookX = (Xi > getX()) ? 7 : 0; // Determine kingside or queenside rook
        ChessPiece rook = chessBoard.piecePosition(rookX, getY());

        if (!(rook instanceof Rook) || ((Rook) rook).hasMoved()) {
            return false; // Rook must be present and unmoved
        }

        // Check if all squares between king and rook are empty and not under attack
        int direction = (Xi > getX()) ? 1 : -1; // Direction: right (1) or left (-1)
        for (int i = getX() + direction; i != rookX; i += direction) {
            if (chessBoard.piecePosition(i, getY()) != null || chessBoard.isSquareUnderAttack(i, getY(), this.getColor())) {
                return false;
            }
        }

        return !chessBoard.isSquareUnderAttack(getX(), getY(), this.getColor()); // King shouldn't be in check
    }

    // Method to update the king's position and status
    @Override
    public void newPos(int Xi, int Yi) {
        if (canCastle(Xi, Yi)) {
            performCastling(Xi);
        }
        super.newPos(Xi, Yi);
        hasMoved = true;
    }

    private void performCastling(int Xi) {
        int rookX = (Xi > getX()) ? 7 : 0; // Determine rook's position (kingside or queenside)
        int rookNewX = (Xi > getX()) ? 5 : 3; // New position for the rook
        ChessPiece rook = chessBoard.piecePosition(rookX, getY());
        rook.newPos(rookNewX, getY()); // Move the rook to its new position
    }
}
