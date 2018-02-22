package fr.dauphine.javaavance.phineloops.model;

/**
 * Using this interface allows the use of the Piece class without getting involved in its implementation. It brings some abstraction to the code
 */
public interface PieceContract {

    void rotate();
    Piece afterRotating();
    int getPieceType();
    int getPieceOrientation();
    boolean getPieceDirection(int position);
    String getPieceFormat();
    }
