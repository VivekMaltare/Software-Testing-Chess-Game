public class Queen extends ChessPiece{
    public Queen(int x, int y, COLOR color, ChessBoard chessBoard) {
        super(x, y, color, chessBoard);
    }

    @Override
    public boolean movePossible(int Xi, int Yi){
        if(VerifyMove(Xi,Yi)){
            return verifyQueenmove(Xi, Yi);
        }
        return false;
    }

    private boolean verifyQueenmove(int Xi, int Yi){
        return verifyStraightmove(Xi, Yi) || VerifyDiagmove(Xi, Yi);
    }
}
