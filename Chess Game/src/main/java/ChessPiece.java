public class ChessPiece {

    private int x; // x coordinate
    private int y; // y coordinate

    private COLOR color;
    protected ChessBoard chessBoard;

    protected boolean hasMoved; // to check if the piece has moved atleast once in the game



    public ChessPiece(int x, int y, COLOR color, ChessBoard chessBoard) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.chessBoard = chessBoard;
        this.hasMoved = false;

        chessBoard.newPosition(this, x, y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public COLOR getColor() {
        return color;
    }

    public int getY() {
        return y;
    }

    public boolean movePossible(int Xi, int Yi){
        return VerifyMove(Xi, Yi);
    }

    protected boolean VerifyMove(int toNewX, int toNewY){
        if (chessBoard.isBounded(toNewX, toNewY)){
            ChessPiece pieceAtNewXY = chessBoard.piecePosition(toNewX, toNewY);

            if (pieceAtNewXY == null) return true;
            if (pieceAtNewXY.getColor() != this.color) return true;
        }
        return false;
    }

    //  call this method to move the current from current location to new location Xi, Yi
    // returns false if move is not possible
    public void newPos(int Xi, int Yi) {

        // check if move to new location is invalid
//        if(!this.canMoveTo(Xi, Yi))
//            return false;

        int currX = this.getX();
        int currY= this.getY();

        // check if the current piece is at the  given
        if(this.chessBoard.piecePosition(currX, currY) == this) {
            this.chessBoard.removePiece(this); // remove the piece from the current location
        } else {
            System.out.println("Invalid move: piece location is not current!");
            return;
        }

        // update the location of piece
        this.x = Xi;
        this.y = Yi;


        // check if whether the target location has any piece

        ChessPiece targetLocationPiece = this.chessBoard.piecePosition(Xi, Yi);

        // remove the piece at target location
        if(targetLocationPiece != null) {
            this.chessBoard.capturePiece(targetLocationPiece);
        }

        // place piece
        this.chessBoard.newPosition(this, Xi, Yi);

        // update the hasMoved property of a piece
        this.hasMoved = true;
    }

    protected boolean verifyStraightmove(int Xi, int Yi) {
        int currX = this.getX();
        int currY = this.getY();

        int xStart;
        int yStart;
        int xFinish;
        int yFinish;

        // Fixing positon of X
        if (currX == Xi){
            if (currY > Yi){
                yStart = Yi;
                yFinish = currY;
            }
            else if (Yi > currY){
                yStart = currY;
                yFinish = Yi;
            }
            else return false;

            // Loop to determine if any piece is between piece at newXY location and current location of the piece to be moved .
            yStart++;
            for(; yStart < yFinish; yStart++){
                if (chessBoard.piecePosition(currX, yStart) != null){
                    return false;
                }
            }
            return true;
        }

        // Fixing position of Y
        if (currY == Yi){
            if (currX > Xi){
                xStart = Xi;
                xFinish = currX;
            }
            else if (Xi > currX){
                xStart = currX;
                xFinish = Xi;
            }
            else return false;

            // Loop to determine if any piece is between piece at newXY location and current location of the piece to be moved .

            xStart++;
            for(; xStart < xFinish; xStart++){
                if (chessBoard.piecePosition(xStart, currY) != null){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    protected boolean VerifyDiagmove(int Xi, int Yi) {
        int newX = this.getX();
        int newY = this.getY();

        int orientation;

        int xStart;
        int yStart;
        int xFinish;

        int xTotal = Math.abs(Xi - newX);
        int yTotal = Math.abs(Yi - newY);

        //Check if new position is diagonal
        if (xTotal == yTotal){

            // check the orientation of move
            if(((newX > Xi) && (newY < Yi)) || ((newX < Xi) && (newY > Yi))) {
                orientation = -1; // orientation is in positive direction
            } else if (((newX < Xi) && (newY < Yi)) || ((newX > Xi) && (newY > Yi))) {
                orientation = 1; // orientation is in negative direction
            } else {
                return false;
            }


            if (Xi < newX){
                xStart = Xi;
                xFinish = newX;
            }
            else if (Xi > newX) {
                xStart = newX;
                xFinish = Xi;
            } else {
                return false;
            }

            if (Yi < newY){
                yStart = (orientation == -1) ? newY : Yi;

            }
            else if (Yi > newY){
                yStart = (orientation == 1) ? newY : Yi;
            } else {
                return false;
            }

            xStart++;

            // y movement of a piece depends on the orientation of the move
            yStart = yStart + orientation;

            // Loop to see if any piece is in between
            for(;xStart < xFinish;){
                if (chessBoard.piecePosition(xStart, yStart) != null){
                    return false;
                }
                xStart++;
                yStart = yStart + orientation;
            }
            return true;
        }
        return false;
    }

}
