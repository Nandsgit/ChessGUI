package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(PieceType pieceType, final int piecePosition, final Alliance pieceAlliance){
        this.pieceType = pieceType;
        this.pieceAlliance=pieceAlliance;
        this.piecePosition=piecePosition;
        //to do
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();
    }

    private  int computeHashCode(){
        int result = pieceType.hashCode();
        result = 31 *result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result= 31 * result + (isFirstMove ? 1: 0);
        return result;
    }

    @Override
    public boolean equals(final Object other){

        if(this==other){
            return true;
        }

        if(!(other instanceof Piece)){
            return false;
        }

        final Piece otherPiece= (Piece) other;
        return piecePosition== otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
                pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove== otherPiece.isFirstMove();

    }

    @Override
    public int hashCode(){
       return this.cachedHashCode;

    }

    public int getPiecePosition(){
        return this.piecePosition;
    }
    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
    public abstract Piece movePiece(Move move);

    public PieceType getPieceType() {
        return this.pieceType;
    }


    public enum PieceType{

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        Brook("R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        Queen("Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        King("K") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        private String pieceName;

        PieceType(final String pieceName){
            this.pieceName=pieceName;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();
    }

}

//package com.chess.engine.pieces;
//
//import com.chess.engine.Alliance;
//import com.chess.engine.board.Board;
//import com.chess.engine.board.Move;
//
//import java.util.Collection;
//
//public abstract class Piece {
//
//    final PieceType pieceType;
//    final Alliance pieceAlliance;
//    final int piecePosition;
//    private final boolean isFirstMove;
//    private final int cachedHashCode;
//
//    Piece(final PieceType type,
//          final Alliance alliance,
//          final int piecePosition,
//          final boolean isFirstMove) {
//        this.pieceType = type;
//        this.piecePosition = piecePosition;
//        this.pieceAlliance = alliance;
//        this.isFirstMove = isFirstMove;
//        this.cachedHashCode = computeHashCode();
//    }
//
//    public PieceType getPieceType() {
//        return this.pieceType;
//    }
//
//    public Alliance getPieceAllegiance() {
//        return this.pieceAlliance;
//    }
//
//    public int getPiecePosition() {
//        return this.piecePosition;
//    }
//
//    public boolean isFirstMove() {
//        return this.isFirstMove;
//    }
//
//    public int getPieceValue() {
//        return this.pieceType.getPieceValue();
//    }
//    public abstract int locationBonus();
//
//    public abstract Piece movePiece(Move move);
//
//    public abstract Collection<Move> calculateLegalMoves(final Board board);
//
//    @Override
//    public boolean equals(final Object other) {
//        if (this == other) {
//            return true;
//        }
//        if (!(other instanceof Piece)) {
//            return false;
//        }
//        final Piece otherPiece = (Piece) other;
//        return piecePosition == otherPiece.piecePosition && pieceType == otherPiece.pieceType &&
//                pieceAlliance == otherPiece.pieceAlliance && isFirstMove == otherPiece.isFirstMove;
//    }
//
//    @Override
//    public int hashCode() {
//        return cachedHashCode;
//    }
//
//    private int computeHashCode() {
//        int result = pieceType.hashCode();
//        result = 31 * result + pieceAlliance.hashCode();
//        result = 31 * result + piecePosition;
//        result = 31 * result + (isFirstMove ? 1 : 0);
//        return result;
//    }
//
//
//    public enum PieceType {
//
//        PAWN(100, "P") {
//            @Override
//            public boolean isPawn() {
//                return true;
//            }
//
//            @Override
//            public boolean isBishop() {
//                return false;
//            }
//
//            @Override
//            public boolean isRook() {
//                return false;
//            }
//
//            @Override
//            public boolean isKing() {
//                return false;
//            }
//        },
//        KNIGHT(320, "N") {
//            @Override
//            public boolean isPawn() {
//                return false;
//            }
//
//            @Override
//            public boolean isBishop() {
//                return false;
//            }
//
//            @Override
//            public boolean isRook() {
//                return false;
//            }
//
//            @Override
//            public boolean isKing() {
//                return false;
//            }
//        },
//        BISHOP(350, "B") {
//            @Override
//            public boolean isPawn() {
//                return false;
//            }
//
//            @Override
//            public boolean isBishop() {
//                return true;
//            }
//
//            @Override
//            public boolean isRook() {
//                return false;
//            }
//
//            @Override
//            public boolean isKing() {
//                return false;
//            }
//        },
//        ROOK(500, "R") {
//            @Override
//            public boolean isPawn() {
//                return false;
//            }
//
//            @Override
//            public boolean isBishop() {
//                return false;
//            }
//
//            @Override
//            public boolean isRook() {
//                return true;
//            }
//
//            @Override
//            public boolean isKing() {
//                return false;
//            }
//        },
//        QUEEN(900, "Q") {
//            @Override
//            public boolean isPawn() {
//                return false;
//            }
//
//            @Override
//            public boolean isBishop() {
//                return false;
//            }
//
//            @Override
//            public boolean isRook() {
//                return false;
//            }
//
//            @Override
//            public boolean isKing() {
//                return false;
//            }
//        },
//        KING(20000, "K") {
//            @Override
//            public boolean isPawn() {
//                return false;
//            }
//
//            @Override
//            public boolean isBishop() {
//                return false;
//            }
//
//            @Override
//            public boolean isRook() {
//                return false;
//            }
//
//            @Override
//            public boolean isKing() {
//                return true;
//            }
//        };
//
//        private final int value;
//        private final String pieceName;
//
//        public int getPieceValue() {
//            return this.value;
//        }
//
//        @Override
//        public String toString() {
//            return this.pieceName;
//        }
//
//        PieceType(final int val, final String pieceName) {
//            this.value = val;
//            this.pieceName = pieceName;
//        }
//
//        public abstract boolean isPawn();
//        public abstract boolean isBishop();
//        public abstract boolean isRook();
//        public abstract boolean isKing();
//
//    }
//
//}