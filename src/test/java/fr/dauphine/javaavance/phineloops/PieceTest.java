
package fr.dauphine.javaavance.phineloops; 

import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.model.Piece;
import fr.dauphine.javaavance.phineloops.model.PieceContract;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class PieceTest extends TestCase
{
    PieceContract pieceToTest=new Piece(5,1);


    public void testNewPieceAfterRotation() {
        PieceContract pAfterRotation=pieceToTest.afterRotating();
        PieceContract pExpected=new Piece(5,2);
        assertEquals(pAfterRotation,pExpected);
    }

    public void testPieceRotating(){
        pieceToTest.rotate();
        assertEquals(pieceToTest,new Piece(5,2));
    }

    public void testPieceConstructors(){
        assertEquals(pieceToTest,new Piece(6));
    }

    public void testOfPieceEqualsImplementation(){
        PieceContract p= new Piece(5,1);
        assertEquals(pieceToTest,p);
    }

}
