package game.pieces;

import base.Vector2;
import base.Window;
import server.game.GameState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Extends Piece, simulates a chess Knight
 *
 * @author Logan Sandlin
 */
public final class Knight extends Piece {

    /**
     * Constructor for Knight
     */
    private Knight(final int boardRow, final int boardColumn, final ChessColor chessColor, final int row, final int column) {
        super(boardRow, boardColumn, chessColor, row, column, 'n');
    }

    /**
     * Used to make a Chess Piece without worrying about UI stuff
     *
     * @param boardRow    the board row
     * @param boardColumn the board column
     * @param chessColor  the piece color
     * @return new Knight
     * @author Logan Sandlin
     * @author John Gauci
     */
    public static Knight newKnight(final int boardRow, final int boardColumn, final ChessColor chessColor) {
        return new Knight(boardRow, boardColumn, chessColor, boardRow, boardColumn);
    }

    void getIntrinsicMoves(final int col, final int row, final int xDirection, final int yDirection,
                           final Collection<Vector2> intrinsicMoves) {
        int destinationRow = row;
        int destinationColumn = col;
        int count = 0;
        while (destinationColumn < 8 && destinationRow < 8 && destinationColumn >= 0 && destinationRow >= 0 && count < 1) {
            intrinsicMoves.add(new Vector2(destinationColumn, destinationRow));
            destinationColumn += xDirection;
            destinationRow += yDirection;
            count++;
        }
    }

    /**
     * Returns the potential moves for a Bishop
     *
     * @param gameState the current game state
     * @return potential moves
     * @author Logan Sandlin
     */
    public ArrayList<Vector2> getPotentialMoves(final GameState gameState) {
        final ArrayList<Vector2> potentialMoves = new ArrayList<>(8);
        final int col = this.getBoardColumn();
        final int row = this.getBoardRow();
        final Queue<Vector2> possibleMoves = new LinkedList<>();
        this.getIntrinsicMoves(col + 2, row + 1, 0, 0, possibleMoves);
        this.pruneCollisions(gameState, possibleMoves, potentialMoves);
        this.getIntrinsicMoves(col - 2, row + 1, -1, 1, possibleMoves);
        this.pruneCollisions(gameState, possibleMoves, potentialMoves);
        this.getIntrinsicMoves(col + 2, row - 1, 1, -1, possibleMoves);
        this.pruneCollisions(gameState, possibleMoves, potentialMoves);
        this.getIntrinsicMoves(col - 2, row - 1, -1, -1, possibleMoves);
        this.pruneCollisions(gameState, possibleMoves, potentialMoves);
        this.getIntrinsicMoves(col + 1, row + 2, 0, 0, possibleMoves);
        this.pruneCollisions(gameState, possibleMoves, potentialMoves);
        this.getIntrinsicMoves(col - 1, row + 2, -1, 1, possibleMoves);
        this.pruneCollisions(gameState, possibleMoves, potentialMoves);
        this.getIntrinsicMoves(col + 1, row - 2, 1, -1, possibleMoves);
        this.pruneCollisions(gameState, possibleMoves, potentialMoves);
        this.getIntrinsicMoves(col - 1, row - 2, -1, -1, possibleMoves);
        this.pruneCollisions(gameState, possibleMoves, potentialMoves);

        return potentialMoves;
    }

    /**
     * Draws the chess piece in the UI
     *
     * @param window the window to draw the entity in
     * @author Kareem Khalidi
     */
    @Override
    public void draw(final Window window) {
        switch (this.getChessColor()) {
            case WHITE -> window.drawPicture(this.getRow(), this.getColumn(), this.getWidth(),
                    this.getHeight(), 1);
            case BLACK -> window.drawPicture(this.getRow(), this.getColumn(), this.getWidth(),
                    this.getHeight(), 7);
        }
    }
}
