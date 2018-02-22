package fr.dauphine.javaavance.phineloops.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Grid {

    private int width;
    private int height;
    private final PieceContract[][] gridMatrix;

    /**
     * Copy constructor
     * @param pM
     */
    public Grid(PieceContract[][] pM){
        height=pM.length;
        width=pM[0].length;
        gridMatrix= pM;
    }

    /**
     * A constructor that will be used while generating a new Grid
     * @param w width of grid
     * @param h height of grid
     */
    public Grid(int w,int h){
        width=w;
        height=h;
        gridMatrix= new Piece[height][width];
    }

    /**
     * Generate a valid grid, ready to be solved by the user
     */
    public void generateValidGrid(){
        prepareGrid();
        shuffleGrid();
    }

    /**
     * Method used to change the position we initially got after applying the generateSolution method
     * so that the user won't find it very simple to solve the game
     * for that we use the rotate method of the piece class
     */
    public void shuffleGrid(){
        for(int i=0;i<gridMatrix.length;i++){
            for(int j=0;j<gridMatrix[0].length;j++){
                Random generator= new Random();
                //We generate a random integer in the range of 1<Random<4 and rotate the Piece this number of time
                //we added +1 to make sure that the piece will rotate (because otherwise, if we get an integer of 0 and there is no +1 the piece won't rotate)
                for(int nbRot=0;nbRot<generator.nextInt(3)+1;nbRot++) {
                    gridMatrix[i][j].rotate();
                }
            }
        }
    }

    /**
     * Generate the grid taking into account the constraint of each position
     */
    public void prepareGrid(){
        Random generator=new Random();
        boolean tableOfPossibleOrientations[]={false,false,false,false};

        //We start from Top-Left corner of the grid and reach at the end Bottom-Right corner of the grid
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){

                //1-Constraint : for a piece on the first line of the grid (ie) j=0, it should not have a connection on its west
                //therefore we set its west orientation to false
                if(j==0) tableOfPossibleOrientations[3]=false;
                //2-Constraint : in the case of j#0, the connection on the --west [3]-- should be equal to the --east [1]-- connection of the previous piece
                else tableOfPossibleOrientations[3]=gridMatrix[i][j-1].getPieceDirection(1);

                //3-Constraint : for a piece on the last line of the grid (ie) j=width-1, it should not have a connection on its east
                if(j==width-1) tableOfPossibleOrientations[1]=false;
                //4-Free choice : for a piece that is neither on the last line nor on the first, the east connection could be true or false.
                //we will choose it randomly as preconized on the paper
                else tableOfPossibleOrientations[1]=generator.nextBoolean();

                //5-Constraint : for a piece on the top column of the grid (ie) i=0, it should not have a connection on its north
                //therefore we set its north orientation to false
                if(i==0) tableOfPossibleOrientations[0]=false;
                //6-Constraint : in the case of i#i, the connection on the --north [0]-- should be equal to the --south [2]-- connection of the previous piece
                else tableOfPossibleOrientations[0]=gridMatrix[i-1][j].getPieceDirection(2);

                //7-Constraint : for a piece on the south column of the grid (ie) i=height-1, it should not have a connection on its south
                if(i==height-1) tableOfPossibleOrientations[2]=false;
                //8-Free choice : for a piece that is neither on the last column nor on the first, the south connection could be true or false.
                //we will choose it randomly as preconized on the paper
                else tableOfPossibleOrientations[2]=generator.nextBoolean();

                //After getting the orientation of the piece(i,j), we then move to initialize the piece
                //For that we start by converting the tableOfPossibleOrientations into a number which we will use to initialize the piece

                int binaryCodeOfPiece=0;
                for(int k=0;k<tableOfPossibleOrientations.length;k++){
                    if(tableOfPossibleOrientations[k]) binaryCodeOfPiece+=Math.pow(2,k);
                }

                gridMatrix[i][j]= new Piece(binaryCodeOfPiece);

            }
        }

    }

    /**
     * Allows us to check if the given Grid is solved or not yet
     * For the check function of the Project, there is no need to specify the parameters
     * but in order to use this method when solving the grid, it is necessary to use the current height and width
     * while browsing the search tree. This will be further discussing in the developer report
     * @param currentHeight
     * @param CurrentWidth
     * @return true if the grid is solved
     */
    public boolean check(int currentHeight, int CurrentWidth){
        for(int i=0;i<currentHeight;i++){
            for(int j=0;j<CurrentWidth;j++){
                if(i==0){
                    //Condition n°1 : If the piece on the first line has a connection on its north than it is not a solution
                    if(gridMatrix[i][j].getPieceDirection(0))return false;
                } else {
                    //Condition n°2 : If the piece is not on the first line, it should have the same connection state on its north as the upper
                    //piece has on its south
                    if(gridMatrix[i][j].getPieceDirection(0)!=gridMatrix[i-1][j].getPieceDirection(2))return false;

                }

                if(j==0){
                    //Condition n°3 : If the piece on the first column has a connection on its west than it is not a solution
                    if(gridMatrix[i][j].getPieceDirection(3))return false;
                } else {
                    //Condition n°4 : If the piece is not on the first column, it should have the same connection state on its west as the
                    //piece to its left has on its east
                    if(gridMatrix[i][j].getPieceDirection(3)!=gridMatrix[i][j-1].getPieceDirection(1))return false;
                }

                if(i==height-1 ){
                    //Condition n°5 : If the piece on the last line has a connection on its south than it is not a solution
                    if(gridMatrix[i][j].getPieceDirection(2))return false;
                }

                if(j==width-1 ){
                    //Condition n°7 : If the piece on the last column has a connection on its east than it is not a solution
                    if(gridMatrix[i][j].getPieceDirection(1))return false;
                }
            }
        }
        return true;
    }


    public boolean checkValidPosition(PieceContract p,int i,int j){
        if(i==0){
            //Condition n°1 : If the piece on the first line has a connection on its north than it is not a solution
            if(p.getPieceDirection(0)) return false;
        } else {
            //Condition n°2 : If the piece is not on the first line, it should have the same connection state on its north as the upper
            //piece has on its south
            if(p.getPieceDirection(0)!=gridMatrix[i-1][j].getPieceDirection(2))  return false;
        }

        if(j==0){
            //Condition n°3 : If the piece on the first column has a connection on its west than it is not a solution
            if(p.getPieceDirection(3)) return false;
        } else {
            //Condition n°4 : If the piece is not on the first column, it should have the same connection state on its west as the
            //piece to its left has on its east
            if(p.getPieceDirection(3)!=gridMatrix[i][j-1].getPieceDirection(1)) return false;
        }

        if(i==height-1 ){
            //Condition n°5 : If the piece on the last line has a connection on its south than it is not a solution
            if(p.getPieceDirection(2)) return false;
        }

        if(j==width-1 ){
            //Condition n°6 : If the piece on the last column has a connection on its east than it is not a solution
            if(p.getPieceDirection(1)) return false;
        }

        return true;
    }


    /**
     * use the Solver class to solve the grid
     * @return
     */
    public boolean solve(){
        return Solver.solve(this)!=null;
    }

    @Override
    public String toString() {
        String grid="";
        for(int i=0;i<gridMatrix.length;i++){
            String s="";
            for(int j=0;j<gridMatrix[0].length;j++){
                //s+="\t"+gridMatrix[i][j].getPieceFormat()+"("+gridMatrix[i][j].getPieceType()+","+gridMatrix[i][j].getPieceOrientation()+")";
                s+="\t"+gridMatrix[i][j].toString();
            }
            grid+=s+"\n";
        }

        return grid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grid grid = (Grid) o;

        if (width != grid.width) return false;
        if (height != grid.height) return false;
        return Arrays.deepEquals(gridMatrix, grid.gridMatrix);
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + Arrays.deepHashCode(gridMatrix);
        return result;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public PieceContract[][] getGridMatrix() {return gridMatrix;}

    //special setter to meet requirement of solver algorithm !!
    public void setGridMatrix(PieceContract buffer, int i,int j) {
        this.gridMatrix[i][j] = buffer;
    }

    }

