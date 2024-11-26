public class ChessBoard {
        private ChessPiece[][] board; // board 2d array contains chess pieces

        private ChessGame game;

    // Fields to track the last move
    private ChessPiece lastMovedPiece;

        public ChessBoard(ChessGame game) {
            board = new ChessPiece[ChessUtil.SIZE][ChessUtil.SIZE]; // 8x8 chess board
            this.game = game;
            // Initialize last move fields
            lastMovedPiece = null;
        }

        public boolean isBounded(int x, int y) {
            if(x >= 0 && y >= 0 && x < ChessUtil.SIZE && y < ChessUtil.SIZE)
                return true;
            return false;
        }

        // if x and y are within bound returns the pieces at that location
        public ChessPiece piecePosition(int x, int y) {
            if(isBounded(x, y))
                return board[x][y];
            return null;
        }

        public ChessPiece[][] getBoard() {
            return board;
        }

        public void newPosition(ChessPiece piece, int x, int y) {
            if(this.isBounded(x, y)) {
                // Update last move details
                setMovedPiece(piece);
                this.board[x][y]= piece;
            }
        }

        // this will make the piece location to null
        public void removePiece(ChessPiece piece) {
            board[piece.getX()][piece.getY()] = null;
        }

        public void capturePiece(ChessPiece piece) {
            this.removePiece(piece);
            if(piece.getColor() == COLOR.WHITE) {
                this.game.getWhitePieces().remove(piece);
            } else {
                this.game.getBlackPieces().remove(piece);
            }

            // Update the last moved piece to the capturing piece
            setMovedPiece(piece);
        }

        public boolean isSquareUnderAttack(int x, int y, COLOR color) {
            // Iterate through all pieces and check if any piece of the opposite color can move to (x, y)
            for (ChessPiece[] row : board) {
                for (ChessPiece piece : row) {
                    if (piece != null && piece.getColor() != color && piece.movePossible(x, y)) {
                        return true;
                    }
                }
            }
            return false;
        }
        public void display() {
            for(int y = 0; y<ChessUtil.SIZE; y++) {
                for(int x= 0; x<ChessUtil.SIZE; x++) {
                    if(this.board[x][y] == null) {
                        System.out.print("_ ");
                    } else {
                        ChessPiece piece = this.piecePosition(x, y);
                        if(piece instanceof Pawn) {
                            System.out.print(piece.getColor() == COLOR.WHITE ? ChessUtil.WHITE_PAWN + " " : ChessUtil.BLACK_PAWN + " ");
                        } else if (piece instanceof King) {
                            System.out.print(piece.getColor() == COLOR.WHITE ? ChessUtil.WHITE_KING + " " : ChessUtil.BLACK_KING + " ");
                        } else if (piece instanceof Queen) {
                            System.out.print(piece.getColor() == COLOR.WHITE ? ChessUtil.WHITE_QUEEN + " " : ChessUtil.BLACK_QUEEN + " ");
                        } else if (piece instanceof Knight) {
                            System.out.print(piece.getColor() == COLOR.WHITE ? ChessUtil.WHITE_KNIGHT + " " : ChessUtil.BLACK_KNIGHT + " ");
                        } else if (piece instanceof Rook) {
                            System.out.print(piece.getColor() == COLOR.WHITE ? ChessUtil.WHITE_ROOK + " " : ChessUtil.BLACK_ROOK + " ");
                        } else if (piece instanceof Bishop) {
                            System.out.print(piece.getColor() == COLOR.WHITE ? ChessUtil.WHITE_BISHOP + " " : ChessUtil.BLACK_BISHOP + " ");
                        }
                    }
                }
                System.out.println();
            }
        }

    // This method returns the most recently moved piece
    public void setMovedPiece(ChessPiece piece) {
        this.lastMovedPiece = piece;
    }

    public ChessPiece getMovedPiece() {
        return this.lastMovedPiece;
    }
}
