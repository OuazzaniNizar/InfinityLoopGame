package fr.dauphine.javaavance.phineloops.model;

import java.util.LinkedList;

/**
 * Will handle the task of solving a given grid
 * The basic idea (that is given in the project cheat) is to build a tree search where the nodes are each piece of the grid
 * and the state corresponding to each node is the grid with the given position of the node and the action is the rotation of the piece
 * of a given node. Each node will have 4 children, the node will have a child if the state of this child makes a valid grid (ie the check
 * method returns true)
 */
public class Solver {
    //variable to keep trace of the number of created nodes
    static int idNode=0;
    //variable to keep trace of the number of visited nodes
    static int nbVisitedNodes=0;
    /**
     * inner class to model a node of the search tree that will be browsed to look for a valid solution
     */
    public static class Node{
        PieceContract piece;
        //Current node's parent
        Node parent=null;
        //Current node's children
        Node position1=null;
        Node position2=null;
        Node position3=null;
        Node position4=null;
        //Id of node
        int numNode;
        //Cursors allows to move from the actual piece in root node to the next piece in the child node
        int cursori;
        int cursorj;
        //each node has a state that much the position of the piece. It allows us to check if the state we get after an action is a valid solution to stop the search
        Grid state;

        @Override
        /**
         *---DEBUGGING ONLY---
         */
        public String toString() {
            return "Node{"+ numNode+"} Parent{"+parent+"}";
        }
    }

    public static Grid solve(Grid matrix){
        //We create the root node
        Node root= new Node();
        root.state=matrix;
        root.cursori=0;
        root.cursorj=0;
        root.piece=matrix.getGridMatrix()[0][0];
        root.parent=null;
        root.numNode=idNode;

        //We model a stack with LinkedList and use the Iterative Deepening Depth First Search algorithm
        LinkedList<Node> stack = new LinkedList<Node>();
        stack.add(root);

        while (!stack.isEmpty()){
            nbVisitedNodes++;
            Node current=stack.removeLast();
            //---DEBUGGING ONLY---
            //System.out.println(current);

            if(current.state.check(current.state.getHeight(),current.state.getWidth())){
                //---DEBUGGING ONLY---
                //System.out.println(current.state);
                //System.out.println("Number of visited nodes : "+nbVisitedNodes);
                return current.state;
            } else {
                if(current.cursori<current.state.getGridMatrix().length && current.cursorj<current.state.getGridMatrix()[0].length){
                    //---DEBUGGING ONLY---
                    //if(nbVisitedNodes%10000==0)System.out.println("Number of visited nodes : "+nbVisitedNodes);
                    //if(nbVisitedNodes%100000==0)System.out.println(current.state);

                    //First improvement of the algorithm : For a piece that isn't of type 0 or 4, we need to create 4 children since it does rotate 4 times.
                    if(current.piece.getPieceType()!=0 && current.piece.getPieceType()!=4){
                        current.position1=evaluateChild(current,current.piece);
                        if(current.position1!=null) stack.add(current.position1);
                        //Applying first rotation
                        current.position2=evaluateChild(current,current.piece.afterRotating());
                        if(current.position2!=null) stack.add(current.position2);
                        //Applying second rotation
                        current.position3=evaluateChild(current,current.piece.afterRotating().afterRotating());
                        if(current.position3!=null) stack.add(current.position3);
                        //Applying third rotation
                        current.position4=evaluateChild(current,current.piece.afterRotating().afterRotating().afterRotating());
                        if(current.position4!=null) stack.add(current.position4);


                    }
                    //First improvement of the algorithm : For a piece that is of type 0 or 4 there is no need to create 4 children since it doesn't rotate 4 times.
                    else {
                        current.position1=evaluateChild(current,current.piece);
                        if(current.position1!=null) stack.add(current.position1);
                    }

                }
                else{
                    //---DEBUGGING ONLY---
                    //should not happen since the grid is valid
                    //System.out.println("We reached the last line/row and the last column "+current.cursori+ " length "+(current.state.getGridMatrix().length-1) );
                    return null;
                }
            }

        }
        //---DEBUGGING ONLY---
        //should not happen if the grid is valid
        // System.out.println("Number of visited nodes : "+nbVisitedNodes);
        // System.out.println("Stack is now empty or number of visited nodes is > 500.000");
        return null;
    }


    public static Node evaluateChild(Node current,PieceContract bufferPiece){
        //---DEBUGGING ONLY---
        //System.out.println("Evaluating children of .. "+current+bufferPiece+" i "+current.cursori+" j "+current.cursorj+" State : ");
        int i=current.cursori;
        int j=current.cursorj;
        Node n=new Node();

        //Second improvement : testing the validity of the current position of the piece
        if(current.state.checkValidPosition(bufferPiece,i,j)){
            n.state=new Grid(copyOf(current.state));
            n.state.setGridMatrix(bufferPiece,i,j);

            //---DEBUGGING ONLY---
            //System.out.println(n.state);

            //Checking if the state we are getting SO FAR is a valid one
            if(n.state.check(i+1,j+1)){
                idNode++;
                n.numNode=idNode;
                n.parent=current;

                //Moving the cursors to the next position in order to choose the piece of the child node
                if(current.cursorj<current.state.getGridMatrix()[0].length-1) {
                    n.cursorj=current.cursorj+1;
                    n.cursori=current.cursori;
                }
                else {
                    if(current.cursori<current.state.getGridMatrix().length-1){
                        n.cursori=current.cursori+1;
                        n.cursorj=0;
                    }
                }
                //---DEBUGGING ONLY---
                //HAD BNT L97BA HYA SBAB LMACHAKIL ALLAH YN3L TBABN TBOUN MK
                //n.piece=current.state.getGridMatrix()[n.cursori][n.cursorj];
                n.piece=current.state.getGridMatrix()[n.cursori][n.cursorj];
                return n;
            }else {
                //To help garbage collector
                n=null;
                return null;
            }
        } else return null;
    }


    /**
     * function to fix a bug that occured while debbuging evaluatechildren method (the method kept editing the current grid while we were looking for editing the etati)
     * However the excessive use of it will lead to an Out of Memory Exception...
     * @param current
     * @return
     */
    public static PieceContract[][] copyOf(Grid current){
        PieceContract[][] m=new Piece[current.getGridMatrix().length][current.getGridMatrix()[0].length];
        for(int a=0;a<current.getGridMatrix().length;a++){
            for (int b=0;b<current.getGridMatrix()[0].length;b++){
                m[a][b]=current.getGridMatrix()[a][b];
            }
        }
        return m;
    }
}
