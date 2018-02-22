
package fr.dauphine.javaavance.phineloops.model;

import java.util.Arrays;

/**
 * Holds the pieces of the grid implementation
 * Each piece will implement the PieceContract so that the grid's use of Piece could be independant from the piece implementation (abstraction)
 */
public class Piece implements PieceContract{
    /**
     * These two variables correspond to the table given in the assignment
     * They will help us write the output file we will generate in one of the grid functions.
     */
    private int pieceType;
    private int pieceOrientation;

    /**
     * Marks the possible orientation of the piece considering its type and category.
     * The variable will give us information about the active ends of the piece in this order [Top, right, bottom, left]. Each variable
     * of the table will take true if the pieceType allows the piece to have a connection in this direction.
     */
    private boolean[] pieceDirection= new boolean[4];
    /**
     * Holds the unicode of the piece depending on its orientation ond type. We use this variable to make it easy to print the grid into the terminal.
     */
    private String pieceFormat;



    /**
     * Constroctur of copy
     * Initiliazes a piece with the parameters of another one
     * @param p a piece given as an input
     */
    public Piece (Piece p){
        pieceType=p.pieceType;
        pieceDirection=p.pieceDirection;
        pieceFormat=p.pieceFormat;
    }

    /**
     * Initializes a piece based on a code of each piece
     * @param binaryCodeOfPiece a code given as an input that we will convert into the corresponding Piece
     *                          this variable's meaning will be discussed on the report
     *                          its interest is to make the code for recognizing a piece clean (less loops and switch cases)
     *
     *                          For each category, there are one or many ways for the piece's position in the grid considering its orientation
     *                          There is a total of 16 possibles positions in this case.
     *                          Considering the project assignment there are 16 different type of piece which are {0,10,11,12,13,20,21,30,31,32,33,40,50,51,52,53}
     *                          Each type depends on the pieceCategory and its orientation. Based on this we will encode each one of the 16 cases with the binaryCodeOfPiece
     *
     */
    public Piece (int binaryCodeOfPiece){
        switch (binaryCodeOfPiece){
            case 0 :
                pieceType=0;
                pieceOrientation=0;
                fillOrientation(false,false,false,false);
                pieceFormat="\u0489";
                break;
            //1x
            case 1 :
                pieceType=1;
                pieceOrientation=0;
                fillOrientation(true,false,false,false);
                pieceFormat="\u21E7";
                break;
            case 2:
                pieceType=1;
                pieceOrientation=1;
                fillOrientation(false,true,false,false);
                pieceFormat="\u21E8";
                break;
            case 4:
                pieceType=1;
                pieceOrientation=2;
                fillOrientation(false,false,true,false);
                pieceFormat="\u21E9";
                break;
            case 8 :
                pieceType=1;
                pieceOrientation=3;
                fillOrientation(false,false,false,true);
                pieceFormat="\u21E6";
                break;

            //2x
            case 5:
                pieceType=2;
                pieceOrientation=0;
                fillOrientation(true,false,true,false);
                pieceFormat="\u2503";
                break;
            case 10:
                pieceType=2;
                pieceOrientation=1;
                fillOrientation(false,true,false,true);
                pieceFormat="\u2501";
                break;
            //3x
            case 11 :
                pieceType=3;
                pieceOrientation=0;
                fillOrientation(true,true,false,true);
                pieceFormat="\u2569";


                break;
            case 7:
                pieceType=3;
                pieceOrientation=1;
                fillOrientation(true,true,true,false);
                pieceFormat="\u2560";

                break;
            case 14:
                pieceType=3;
                pieceOrientation=2;
                fillOrientation(false,true,true,true);
                pieceFormat="\u2532";
                break;
            case 13:
                pieceType=3;
                pieceOrientation=3;
                fillOrientation(true,false,true,true);
                pieceFormat="\u2528";
                break;
            //4x
            case 15 :
                pieceType=4;
                pieceOrientation=0;
                fillOrientation(true,true,true,true);
                pieceFormat="\u256C";
                break;
            //5x
            case 3 :
                pieceType=5;
                pieceOrientation=0;
                fillOrientation(true,true,false,false);
                pieceFormat="\u2517";
                break;
            case 6:
                pieceType=5;
                pieceOrientation=1;
                fillOrientation(false,true,true,false);
                pieceFormat="\u250F";
                break;
            case 12:
                pieceType=5;
                pieceOrientation=2;
                fillOrientation(false,false,true,true);
                pieceFormat="\u2513";
                break;
            case 9:
                pieceType=5;
                pieceOrientation=3;
                fillOrientation(true,false,false,true);
                pieceFormat="\u251B";
                break;
        }
    }

    /**
     *Another constructor to create a Piece based on its type and orientation, it will be helpful while reading a grid from an input file
     * @param type of the Piece
     * @param orientation of the Piece
     */
    public Piece(int type,int orientation){
        switch (type){
            case 0:
                fillOrientation(false,false,false,false);
                pieceFormat="\u0489";
                break;
            case 1:
                switch (orientation){
                    case 0:
                        fillOrientation(true,false,false,false);
                        pieceFormat="\u21E7";
                        break;
                    case 1:
                        fillOrientation(false,true,false,false);
                        pieceFormat="\u21E8";
                        break;
                    case 2:
                        fillOrientation(false,false,true,false);
                        pieceFormat="\u21E9";
                        break;
                    case 3:
                        fillOrientation(false,false,false,true);
                        pieceFormat="\u21E6";
                        break;

                }
                break;
            case 2:
                switch (orientation){
                    case 0:
                        fillOrientation(true,false,true,false);
                        pieceFormat="\u2503";
                        break;
                    case 1:
                        fillOrientation(false,true,false,true);
                        pieceFormat="\u2501";
                        break;
                }
                break;
            case 3:
                switch (orientation){
                    case 0:
                        fillOrientation(true,true,false,true);
                        pieceFormat="\u2569";

                        break;
                    case 1:
                        fillOrientation(true,true,true,false);
                        pieceFormat="\u2560";
                        break;
                    case 2:
                        fillOrientation(false,true,true,true);
                        pieceFormat="\u2566";


                        break;
                    case 3:
                        fillOrientation(true,false,true,true);
                        pieceFormat="\u2563";
                        break;

                }
                break;
            case 4:
                fillOrientation(true,true,true,true);
                pieceFormat="\u256C";
                break;
            case 5:
                switch (orientation){
                    case 0:
                        fillOrientation(true,true,false,false);
                        pieceFormat="\u2517";
                        break;
                    case 1:
                        fillOrientation(false,true,true,false);
                        pieceFormat="\u250F";
                        break;
                    case 2:
                        fillOrientation(false,false,true,true);
                        pieceFormat="\u2513";
                        break;
                    case 3:
                        fillOrientation(true,false,false,true);
                        pieceFormat="\u251B";
                        break;

                }
                break;
        }

        pieceType=type;
        pieceOrientation=orientation;

    }

    /**
     * Takes the direction of each piece and fill it into the pieceOrientation variable
     * @param north
     * @param east
     * @param south
     * @param west
     */
    private void fillOrientation(boolean north, boolean east, boolean south, boolean west){
        pieceDirection[0]=north;
        pieceDirection[1]=east;
        pieceDirection[2]=south;
        pieceDirection[3]=west;
    }

    /**
     * Rotate the current piece in the direct sense
     */
    public void rotate(){
        Piece p=afterRotating();
        this.pieceOrientation=p.pieceOrientation;
        this.pieceType=p.pieceType;
        this.pieceDirection=p.pieceDirection;
        this.pieceFormat=p.pieceFormat;
    }

    /**
     * This method will be used to construct the search tree to solve the Grid
     * Rotating a piece consists of moving each element of the pieceDirection array by one position to the left
     * @return the new Piece we get after the rotation
     */
    public Piece afterRotating(){
        Piece p=new Piece(this.pieceType,this.pieceOrientation);
        //the piece of type 0 and 4 don't change after a rotation, there is no need to perform the operations
        if(p.pieceType!=0 && p.pieceType!=4){
            boolean[] temp=new boolean[4];
            for(int i=0;i<p.pieceDirection.length;i++){
                if(i==3){
                    temp[0]=p.pieceDirection[3];
                }else {
                    temp[i+1]=p.pieceDirection[i];
                }
            }
            p.pieceDirection=temp;
            int binaryCodeOfPiece=0;
            for(int k=0;k<p.pieceDirection.length;k++){
                if(p.pieceDirection[k]) binaryCodeOfPiece+=Math.pow(2,k);
            }
            return new Piece(binaryCodeOfPiece);
        }
        return p;
    }

    public int getPieceType() {
        return pieceType;
    }

    public int getPieceOrientation() {
        return pieceOrientation;
    }

    public boolean getPieceDirection(int position) {
        return pieceDirection[position];
    }

    public String getPieceFormat() {
        return pieceFormat;
    }
    @Override
    /**
     * To make it simple for debbuging
     */
    public String toString() {
        return pieceFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        if (pieceType != piece.pieceType) return false;
        if (pieceOrientation != piece.pieceOrientation) return false;
        return true;
    }
}

