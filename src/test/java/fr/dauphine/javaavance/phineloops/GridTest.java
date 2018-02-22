package fr.dauphine.javaavance.phineloops;

import fr.dauphine.javaavance.phineloops.model.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.junit.Assert.assertTrue;

public class GridTest extends TestCase {
    Grid gridToTest;

    /**
     * Rigourous Test :-)
     */
    public void testValidSolution() throws Exception {
        PieceContract[][] gridMatrix=new Piece[2][2];
        gridMatrix[0][0]=new Piece(5,1);
        gridMatrix[0][1]=new Piece(1,3);
        gridMatrix[1][0]=new Piece(1,0);
        gridMatrix[1][1]=new Piece(0,0);
        gridToTest=new Grid(gridMatrix);
        assertEquals(gridToTest.check(2,2),true);
    }

    public void testInvalidSolution() throws Exception {
        PieceContract[][] gridMatrix=new Piece[2][2];
        gridMatrix[0][0]=new Piece(5,2);
        gridMatrix[0][1]=new Piece(1,3);
        gridMatrix[1][0]=new Piece(1,0);
        gridMatrix[1][1]=new Piece(0,0);
        gridToTest=new Grid(gridMatrix);
        assertEquals(gridToTest.check(2,2),false);
    }

    public void testSolver() throws Exception {
        gridToTest=new Grid(10,10);
        gridToTest.generateValidGrid();
        gridToTest= Solver.solve(gridToTest);
        try{
        assertEquals(gridToTest.check(10,10),true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testReaderWriter() throws Exception {
        PieceContract[][] gridMatrix=new Piece[2][2];
        gridMatrix[0][0]=new Piece(5,2);
        gridMatrix[0][1]=new Piece(1,3);
        gridMatrix[1][0]=new Piece(1,0);
        gridMatrix[1][1]=new Piece(0,0);
        gridToTest=new Grid(gridMatrix);

        ReaderWriter.writeOutputFileGrid("grid",gridToTest);
        Grid g=ReaderWriter.readInputFileGrid("grid.dat");
        assertEquals(g.equals(gridToTest),true);

    }

    public void testChildEvaluation() throws Exception {

    }

    public void testValidPosition() throws Exception {
        PieceContract[][] gridMatrix=new Piece[2][2];
        gridMatrix[0][0]=new Piece(5,1);
        gridMatrix[0][1]=new Piece(1,3);
        gridMatrix[1][0]=new Piece(1,0);
        gridMatrix[1][1]=new Piece(0,0);
        gridToTest=new Grid(gridMatrix);
        assertEquals(gridToTest.checkValidPosition(gridMatrix[0][0],0,0),true);
    }
}
