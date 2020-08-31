package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isIncheck;


    public Player(final Board board,
                  final Collection<Move> legalMoves, final Collection<Move> opponentsMoves) {

        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastle(legalMoves, opponentsMoves)));
        this.isIncheck= !Player.calculateAttackOnTile(this.playerKing.getPiecePosition(),opponentsMoves).isEmpty();


    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    protected static Collection<Move> calculateAttackOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves= new ArrayList<>();

        for (final Move move: moves) {
            if(piecePosition== move.getDesinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    protected King establishKing() {
        for(final Piece piece : getActivePieces()) {
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here! " + this.getAlliance()+ " king could not be established!");
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    public boolean isIncheck(){

        return this.isIncheck;
    }

    protected boolean hasEscapeMoves() {

        for(final Move move: this.legalMoves){
            final MoveTransition transition= makeMove(move);
            if(transition.geMoveStatus().isDone()){
                return true;
            }
        }
        return false;


    }

    public boolean isInCheckMate(){
        return this.isIncheck  && !hasEscapeMoves();
    }

    public boolean isStaleMate(){
        return !this.isIncheck && !hasEscapeMoves();
    }

    public boolean isCastle(){
        return false;
    }

    public MoveTransition makeMove( final Move move){


        if(!isMoveLegal(move)){
            return new MoveTransition(this.board, move,MoveStatus.ILLEGAL_MOVE);
        }

        final Board transiitonBoard = move.execute();

        final Collection<Move> kingAttacks= Player.calculateAttackOnTile(transiitonBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transiitonBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board,move,MoveStatus.LEAVES_PLAYER_INCHECK);
        }

        return new MoveTransition(transiitonBoard,move,MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastle(Collection<Move> playerLegals, Collection<Move> opponenetsLegal);

}
