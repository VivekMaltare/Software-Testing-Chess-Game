public class Rook extends ChessPiece {
    private boolean hasMoved = false; // Track if the rook has moved

    public Rook(int x, int y, COLOR color, ChessBoard chessBoard) {
        super(x, y, color, chessBoard);
    }

    @Override
    public boolean movePossible(int Xi, int Yi) {
        if (VerifyMove(Xi, Yi)) {
            return verifyRookmove(Xi, Yi);
        }
        return false;
    }

    @Override
    public void newPos(int Xi, int Yi) {
        super.newPos(Xi, Yi);
        hasMoved = true; // Mark rook as moved
    }
    private boolean verifyRookmove(int Xi, int Yi) {
        return verifyStraightmove(Xi, Yi);
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
