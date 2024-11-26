public class Bishop extends ChessPiece{
    public Bishop(int x, int y, COLOR color, ChessBoard chessBoard) {
        super(x, y, color, chessBoard);
    }

    @Override
    public boolean movePossible(int Xi, int Yi){
        if(VerifyMove(Xi,Yi)){
            return checkBishopMovement(Xi, Yi);
        }
        return false;
    }

    private boolean checkBishopMovement(int toNewX, int toNewY) {
        return VerifyDiagmove(toNewX, toNewY);
    }
}
