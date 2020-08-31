package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.CastleMove.KingSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player  {


    public WhitePlayer(final Board board, Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board,whiteStandardLegalMoves,blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals) {


//        if(this.isInCheck() || this.isCastled() || !(this.isKingSideCastleCapable() || this.isQueenSideCastleCapable())) {
//            return ImmutableList.of();
//        }

        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isIncheck()){
            //whites king side castle
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttackOnTile(61, opponentsLegals).isEmpty() &&
                            Player.calculateAttackOnTile(62,opponentsLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()){

                        //TODO ADD a castlemove
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook) rookTile.getPiece(),
                        rookTile.getTileCoordinate(), 61));

                    }
                }
            }

//            if(this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 60 && !this.isIncheck()) {
//                //whites king side castle
//                if(this.board.getTile(61) == null && this.board.getTile(62) == null) {
//                    final Piece kingSideRook = this.getActivePieces().;
//                    if(kingSideRook != null && kingSideRook.isFirstMove()) {
//                        if(Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() && Player.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
//                                kingSideRook.getPieceType().isRook()) {
//                            if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 52)) {
//                                kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook) kingSideRook, kingSideRook.getPiecePosition(), 61));
//                            }
//                        }
//                    }
//                }

            if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() &&
                    !this.board.getTile(57).isTileOccupied()){
                final Tile rookTile= this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        Player.calculateAttackOnTile(58,opponentsLegals).isEmpty() &&
                        Player.calculateAttackOnTile(59,opponentsLegals).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()){
                    //TODO add castle
                    kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) rookTile.getPiece(),
                            rookTile.getTileCoordinate(), 59));
                }
            }

        }


        return ImmutableList.copyOf(kingCastles);
    }
}
