public class Knight extends ChessPiece{
    public Knight(int x, int y, COLOR color, ChessBoard chessBoard) {
        super(x, y, color, chessBoard);
    }

    public boolean movePossible(int Xi, int Yi){
        if(VerifyMove(Xi,Yi)){
            return verifyKnightMove(Xi, Yi);
        }
        return false;
    }

    public boolean verifyKnightMove(int Xi, int Yi) {

        int currX = this.getX();
        int currY = this.getY();

        int absX = Math.abs(Xi - currX); // absolute difference between new X location and current X location
        int absY = Math.abs(Yi - currY); // absolute difference between new Y location and current Y location

        if (absX == 2 && absY == 1) {
            return true;
        }
        if (absX == 1 && absY == 2) {
            return true;
        }

        return false;
    }
}
