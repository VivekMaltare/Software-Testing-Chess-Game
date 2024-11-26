import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChessTest {
    private static ChessGame chessGame;
    private static ChessBoard chessBoard;

    @BeforeEach
    public void setupTestGame() {
        chessGame = new ChessGame();
        chessBoard = chessGame.getChessBoard();
    }

    @Test
    public void isBoundedTest() {
        // Test Path: [1,2,3] : within bound
        assertEquals(true, chessBoard.isBounded(6, 7));

        // Test Path: [1,2,4] : out of bound
        assertEquals(false, chessBoard.isBounded(8, 7));
    }

    @Test
    public void verifyKnightMoveTest() {
        Knight knight = chessGame.addKnight(4, 4, COLOR.WHITE);

        // Test Path: [1, 2, 3] : knight is moving 2 step in x direction and 1 step in y direction
        assertEquals(true, knight.verifyKnightMove(6, 3));
        assertEquals(true, knight.verifyKnightMove(6, 5));
        assertEquals(true, knight.verifyKnightMove(2, 3));
        assertEquals(true, knight.verifyKnightMove(2, 5));

        // Test Path: [1, 2, 4, 5] : knight is moving 2 step in y direction and 1 step in x direction
        assertEquals(true, knight.verifyKnightMove(3, 2));
        assertEquals(true, knight.verifyKnightMove(5, 2));
        assertEquals(true, knight.verifyKnightMove(3, 6));
        assertEquals(true, knight.verifyKnightMove(3, 6));

        // Test Path: [1, 2, 4, 6] : invalid move for a knight piece
        assertEquals(false, knight.verifyKnightMove(5, 5));
    }

    @Test
    public void verifyPawnmoveTest() {
        Pawn whitePawn1 = chessGame.addPawn(0, 6, COLOR.WHITE);
        Pawn blackPawn1 = chessGame.addPawn(0, 1, COLOR.BLACK);

        // Test Path: [1, 2, 3, 5, 6, 8] : this is for black pawn moving 1 step forward and no piece at target location
        assertEquals(true, blackPawn1.verifyPawnmove(0, 2));

        // Test Path: [1, 2, 4, 5, 6, 8] : this is for white pawn moving 1 step forward and no piece at target location
        assertEquals(true, whitePawn1.verifyPawnmove(0, 5));

        // Test Path: [1, 2, 3, 5, 7, 11] : this is the black pawn with hasMoved == true, and it is trying to move 2 step forward
        blackPawn1.newPos(0, 2); // moved to 0,2
        assertEquals(false, blackPawn1.verifyPawnmove(0, 4)); // check movement from 0,2 to 0,4

        // Test Path: [1, 2, 4, 5, 7, 11] : this is the white pawn with hasMoved == true, and it is trying to move 2 step forward
        whitePawn1.newPos(0, 5); // moved to 0,5
        assertEquals(false, whitePawn1.verifyPawnmove(0, 3)); // check movement from 0,5 to 0,3


        // Test Path: [1, 2, 3, 5, 6, 9, 10] // if there is any opponent's piece diagonal to black pawn
        Queen whiteQueen = chessGame.addQueen(1, 3, COLOR.WHITE);
        assertEquals(true, blackPawn1.verifyPawnmove(1, 3));
        chessBoard.capturePiece(whiteQueen);

        // Test Path: [1, 2, 4, 5, 6, 9, 10] // if there is any opponent's piece diagonal to white pawn
        Queen blackQueen = chessGame.addQueen(1, 4, COLOR.BLACK);
        assertEquals(true, whitePawn1.verifyPawnmove(1, 4));
        chessBoard.capturePiece(blackQueen);

        // Test Path: [1, 2, 3, 5, 6, 9, 11]
        // black pawn wants to move diagonal but there is no piece in that location
        assertEquals(false, blackPawn1.verifyPawnmove(1, 3));
        // black pawn moves more than 1 step in horizontal direction
        assertEquals(false, blackPawn1.verifyPawnmove(2, 3));


        // Test Path: [1, 2, 4, 5, 6, 9, 11]
        // white pawn wants to move diagonal but there is no piece in that location
        assertEquals(false, whitePawn1.verifyPawnmove(1, 4));
        // white pawn moves more than 1 step in horizontal direction
        assertEquals(false, whitePawn1.verifyPawnmove(2, 4));


        // Test Path: [1, 2, 3, 5, 7, 12, 11] // black pawn wants to move its first move with more than two steps
        Pawn blackPawn2 = chessGame.addPawn(5, 1, COLOR.BLACK);
        assertEquals(false, blackPawn2.verifyPawnmove(5, 4));

        // Test Path: [1, 2, 4, 5, 7, 12, 11] // white pawn wants to move its first move with more than two steps
        Pawn whitePawn2 = chessGame.addPawn(7, 6, COLOR.WHITE);
        assertEquals(false, whitePawn2.verifyPawnmove(7, 3));

        // Test Path: [1, 2, 3, 5, 7, 12, 13, 11]
        // black pawn moves diagonal 2 steps
        assertEquals(false, blackPawn2.verifyPawnmove(4, 3));


        Rook whiteRook = chessGame.addRook(5, 2, COLOR.WHITE);
        Rook blackRook = chessGame.addRook(7, 5, COLOR.BLACK);
        Assertions.assertAll(
                // black pawn moves 2 steps with an opponent piece in between
                () -> Assertions.assertFalse(blackPawn2.verifyPawnmove(5, 3), "Test Case Failed -> " +
                        "test path: [1, 2, 3, 5, 7, 12, 13, 11], i.e  black pawn moves 2 steps with an opponent piece in between"),

                // white pawn moves 2 steps with an opponent piece in between
                () -> Assertions.assertFalse(whitePawn2.verifyPawnmove(7, 4), "Test Case Failed ->" +
                        " test path: [1, 2, 4, 5, 7, 12, 13, 11], i.e  white pawn moves 2 steps with an opponent piece in between")
        );

        // black pawn moves 2 steps with opponent piece in that location
        // Test Path: [1, 2, 3, 5, 7, 12, 13, 11]
        whiteRook.newPos(5, 3);
        assertEquals(false, blackPawn2.verifyPawnmove(5, 3));

        // Test Path: [1, 2, 4, 5, 7, 12, 13, 11]
        // white pawn moves diagonal 2 steps
        assertEquals(false, whitePawn2.verifyPawnmove(6, 4));

        // white pawn moves 2 steps with opponent piece in that location
        whiteRook.newPos(7, 4);
        assertEquals(false, whitePawn2.verifyPawnmove(7, 4));
        // Test Path: [1, 2, 4, 5, 7, 12, 13, 11]
        chessBoard.capturePiece(blackRook);
        chessBoard.capturePiece(whiteRook);

        // Test Path: [1, 2, 3, 5, 7, 12, 13, 14] : black pawn moves two-step forward
        assertEquals(true, blackPawn2.verifyPawnmove(5, 3));

        // Test Path: [1, 2, 4, 5, 7, 12, 13, 14]: white pawn moves two-step forward
        assertEquals(true, whitePawn2.verifyPawnmove(7, 4));
    }

    @Test
    public void verifyStraightmoveTest() {
        Rook blackRook = chessGame.addRook(6, 2, COLOR.BLACK);
        Rook whiteRook = chessGame.addRook(2, 6, COLOR.WHITE);

        // Test Path: [1, 2, 4, 16] : Rook does not move straight
        assertEquals(false, blackRook.verifyStraightmove(7, 4));

        // Test Path: [1, 2, 3, 6, 9] : Rook moves to same location it is already at
        assertEquals(false, blackRook.verifyStraightmove(6, 2));

        // Test Path: [1,2,3,5,10,11,13] : Rook moves one position vertically straight above
        assertEquals(true, whiteRook.verifyStraightmove(2, 5));

        // Test Path: [1,2,3,6,8,10,11,12,15,11,13] : Rook moves one position vertically straight down
        assertEquals(true, blackRook.verifyStraightmove(6, 3));

        // Test Path: [1,2,3,6,8,10,11,12,15,11,12,15,11,13] : Rook moves two position vertically straight down
        assertEquals(true, blackRook.verifyStraightmove(6, 4));

        // Test Path: [1,2,3,6,8,10,11,12,14] : There is opponent piece right below the rook
        Queen whiteQueen = chessGame.addQueen(6, 3, COLOR.WHITE);
        assertEquals(false, blackRook.verifyStraightmove(6, 6));
        chessBoard.capturePiece(whiteQueen);

        // Test Path: [1,2,3,5,10,11,12,14] : There is opponent piece right above the rook
        Queen blackQueen = chessGame.addQueen(2, 5, COLOR.BLACK);
        assertEquals(false, whiteRook.verifyStraightmove(2, 4));
        chessBoard.capturePiece(blackQueen);

        // Test Path: [1,2,3,6,8,10,11,12,15,11,13] : black Rook moves two positions straight down
        assertEquals(true, blackRook.verifyStraightmove(6, 4));

        // Test Path: [1,2,3,5,10,11,12,15,11,13]  : white Rook moves two positions straight up
        assertEquals(true, whiteRook.verifyStraightmove(2, 4));

        // Test Path: [1,2,3,5,10,11,12,14] : white rook moves up more than two position but finds opponents piece in its way
        blackQueen = chessGame.addQueen(2, 4, COLOR.BLACK);
        assertEquals(false, whiteRook.verifyStraightmove(2, 3));
        chessBoard.capturePiece(blackQueen);

        // Test Path: [1,2,3,5,10,11,12,15,11,12,15,11,13] : whiteRook moves more than two position straight up
        assertEquals(true, whiteRook.verifyStraightmove(2, 3));

        // moving the white and black to test for horizontal moves
        blackRook.newPos(4, 2);
        whiteRook.newPos(4, 5);

        // Test Path: [1,2,4,17,19,20,22,23,24] : Rook moves one position horizontally straight right
        assertEquals(true, blackRook.verifyStraightmove(5, 2));


        // Test Path: [1,2,4,17,18,22,23,24] : Rook moves one position horizontally straight left
        assertEquals(true, blackRook.verifyStraightmove(3, 2));

        // Test Path: [1,2,4,17,19,20,22,23,25,26] : there is opponent piece just right side
        whiteQueen = chessGame.addQueen(5, 2, COLOR.WHITE);
        assertEquals(false, blackRook.verifyStraightmove(6, 2));

        // Test Path: [1,2,4,17,18,22,23,25,26] : there is opponent piece just left side
        whiteQueen.newPos(3, 2);
        assertEquals(false, blackRook.verifyStraightmove(2, 2));

        // Test Path: [1,2,4,17,19,20,22,23,25,27,23,25,26]: black rook moves right more than two position but finds opponents piece in its way
        whiteQueen.newPos(6, 2);
        assertEquals(false, blackRook.verifyStraightmove(7, 2));

        // Test Path: [1,2,4,17,18,22,23,25,26]: black rook moves left more than two position but finds opponents piece in its way
        whiteQueen.newPos(2, 2);
        assertEquals(false, blackRook.verifyStraightmove(1, 2));
        chessBoard.capturePiece(whiteQueen);

        // Test Path: [1,2,4,17,18,22,23,25,27,23,25,27,23,25,27,23,24] : whiteRook moves more than two position straight up
        assertEquals(true, blackRook.verifyStraightmove(0, 2));
    }

    @Test
    public void VerifyDiagmoveTest() {
        Queen blackQueen = chessGame.addQueen(4, 4, COLOR.BLACK);

        // TestPath: [1,2,4]: queen moves non-diagonal
        assertEquals(false, blackQueen.VerifyDiagmove(4, 6));

        // Test Path: [1, 2, 3, 6, 7, 9, 10, 14, 15, 19, 20, 22, 24, 20, 22, 24, 20, 21] : queen moves diagonally from 4, 4 to 1, 1, with no opponent's piece on the path
        assertEquals(true, blackQueen.VerifyDiagmove(1, 1));

        // Test Path: [1, 2, 3, 6, 7, 9, 10, 14, 15, 19, 20, 22, 23] : queen moves diagonally from 4, 4 to 1, 1, with opponent's piece on the path
        Pawn whitePawn = chessGame.addPawn(2, 2, COLOR.WHITE);
        assertEquals(false, blackQueen.VerifyDiagmove(1, 1));

        // Test Path: [1, 2, 3, 6, 7, 9, 10, 14, 15, 19, 20, 22, 24, 20, 22, 23] : queen moves diagonally from 4, 4 to 1, 1, with opponent's piece is at 3, 3
        whitePawn.newPos(3, 3);
        assertEquals(false, blackQueen.VerifyDiagmove(1, 1));
    }

    @Test
    public void isKingInCheckTest() {
        King blacKing = chessGame.addKing(3, 2, COLOR.BLACK);

        // TestPath: [1, 2, 3, 4]: no opponent's piece to give on the king
        assertEquals(false, chessGame.isKingInCheck(COLOR.BLACK));


        // TestPath: [1, 2, 3, 5, 6]: white queen gives check to the black king
        Queen whiteQueen = chessGame.addQueen(5, 4, COLOR.WHITE);
        assertEquals(true, chessGame.isKingInCheck(COLOR.BLACK));
        chessBoard.capturePiece(whiteQueen);

        //no check TestPath: [1, 2, 3, 5, 7, 3, 4]
        Pawn whitePawn1 = chessGame.addPawn(6, 6, COLOR.WHITE);

        // TestPath: [1, 2, 3, 5, 7, 3, 5, 7, 3, 4]: checking for check with more than two opponent piece, no check
        Pawn whitePawn2 = chessGame.addPawn(5, 6, COLOR.WHITE);
        assertEquals(false, chessGame.isKingInCheck(COLOR.BLACK));
    }

    @Test
    public void isCheckMateTest() {
        King blackKing = chessGame.addKing(7, 7, COLOR.BLACK);
        Queen whiteQueen = chessGame.addQueen(5, 7, COLOR.WHITE);
        Queen whiteRook = chessGame.addQueen(7, 5, COLOR.WHITE);

        // TestPath: [1, 2, 3]: checkmate
        assertEquals(true, chessGame.isCheckMate(COLOR.BLACK));

        // TestPath: [1, 2, 4]: no checkmate
        chessBoard.capturePiece(whiteRook); // removing whiteRook to remove the checkmate on the king
        assertEquals(false, chessGame.isCheckMate(COLOR.BLACK));
    }

    @Test
    public void isGameOverTest() {
        // TestPath: [1, 2, 4, 6] : no checkmate, no game over
        chessGame.setCurrentPlayer(COLOR.BLACK);
        King blackKing = chessGame.addKing(7, 7, COLOR.BLACK);
        Queen whiteQueen = chessGame.addQueen(5, 7, COLOR.WHITE);
        assertEquals(false, chessGame.isGameOver());

//        // TestPath: [1, 2, 3, 7, 9] : checkMate
        Rook whiteRook = chessGame.addRook(6, 6, COLOR.WHITE);
        assertEquals(true, chessGame.isGameOver());

        blackKing.newPos(0,0);
        whiteQueen.newPos(2,1);
        chessBoard.removePiece(whiteRook);

//        // Act & Assert: The game should detect stalemate
//        //TestPath: [1, 2, 4, 5]
        assertEquals(true, chessGame.isGameOver());

//        for black player win TestPath: [1, 2, 3, 8, 9]
//        chessGame.setCurrentPlayer(COLOR.WHITE);
//        chessBoard.removePieceFromBoard(blackKing);
//        chessBoard.removePieceFromBoard(whiteQueen);
//
//        King whiteKing = chessGame.addKing(7, 7, COLOR.WHITE);
//        Knight blackKnight = chessGame.addKnight(5,5,COLOR.BLACK);
//        Rook blackRook = chessGame.addRook(6, 7, COLOR.BLACK);
//
//        assertEquals(true, chessGame.isGameOver());
    }

    @Test
    public void testCheckEnPassant() {

        // Create a white pawn and a black pawn
        Pawn whitePawn = new Pawn(4, 6, COLOR.WHITE, chessBoard); // White pawn at (4, 4)
        Pawn blackPawn = new Pawn(5, 4, COLOR.BLACK, chessBoard); // Black pawn at (5, 4)
        chessBoard.newPosition(whitePawn, 4, 6);
        chessBoard.newPosition(blackPawn, 5, 4);

        // Simulate black pawn's two-step move (from (5, 4) to (5, 6))
        blackPawn.newPos(5, 6); // Black pawn moves from (5, 4) to (5, 6)
        chessBoard.setMovedPiece(blackPawn); // Set the last moved piece

        //TestPath:[1, 2, 3, 4]
        //Queen blackQueen= chessGame.addQueen(5, 6, COLOR.BLACK);
        //blackQueen.newPos(5,6);
        //chessBoard.setMovedPiece(blackQueen);

        // Now white pawn attempts en passant
        boolean canCapture = whitePawn.checkEnPassant(5, 5); // White pawn attempts en passant

        // Assert that en passant is possible
        //TestPath:[1, 2, 3, 5, 6, 7]
        assertTrue(canCapture, "White pawn should be able to perform en passant");

        //TestPath: [1, 2, 4]
        assertFalse(whitePawn.checkEnPassant(6, 5));

        //TestPath: [1, 2, 3, 5, 4]
        blackPawn.newPos(5,7);
        canCapture = whitePawn.checkEnPassant(5, 5);
        assertFalse(canCapture, "White pawn should not be be able to perform en passant");

        //TestPath: [1, 2, 3, 5, 6, 4]
        blackPawn.newPos(6,6);
        assertFalse(whitePawn.checkEnPassant(5, 5));
    }

    @Test
    public void testWhitePawnPromotion() {

        // Create white pawn on the second-to-last rank (Y = 6)
        Pawn whitePawn = new Pawn(0, 6, COLOR.WHITE, chessBoard);
        chessBoard.newPosition(whitePawn, 0, 6);
        chessBoard.display();

        whitePawn.newPos(0,1);

        // Before promotion, the piece should be a pawn
        ChessPiece pieceBeforePromotion = chessBoard.piecePosition(0, 1);
        assertTrue(pieceBeforePromotion instanceof Pawn);

        // Call promotePawn method to promote the pawn to a Queen
        whitePawn.promotePawn();

        // After promotion, the piece should be a Queen
        ChessPiece pieceAfterPromotion = chessBoard.piecePosition(0, 1);

        //TestPath: [1, 2, 4]
        assertFalse(pieceAfterPromotion instanceof Queen,"Pawn not promoted to Queen at the opposite corner");

        // Move the white pawn to the promotion rank (Y = 0)
        whitePawn.newPos(0, 0);
        chessBoard.display();

        // Before promotion, the piece should be a pawn
        pieceBeforePromotion = chessBoard.piecePosition(0, 0);
        assertTrue(pieceBeforePromotion instanceof Pawn);

        // Call promotePawn method to promote the pawn to a Queen
        whitePawn.promotePawn();

        // After promotion, the piece should be a Queen
        pieceAfterPromotion = chessBoard.piecePosition(0, 0);

        //TestPath: [1, 2, 3, 4]
        assertTrue(pieceAfterPromotion instanceof Queen,"Successfully promoted to queen after reaching the end");
        chessBoard.display();

        //Check that the pawn was removed from the board (Y = 6)
        assertNull(chessBoard.piecePosition(0, 6));
    }
}