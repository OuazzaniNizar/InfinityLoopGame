package fr.dauphine.javaavance.phineloops.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ReaderWriter {


    /**
     *a method that takes a grid (generated on the base of a wxh given by the user) as input and return the output file of it
     * @param fileName the name the user will give to the created file
     * @throws IOException throws an exception if a problem occurs while creating the file
     */
    public static void writeOutputFileGrid(String fileName, Grid g) throws IOException {
        PrintWriter writer = new PrintWriter(fileName+".dat", "UTF-8");
        writer.println(""+g.getWidth());
        writer.println(""+g.getHeight());
        for(int i=0;i<g.getGridMatrix().length;i++){
            for(int j=0;j<g.getGridMatrix()[0].length;j++){
                writer.println(g.getGridMatrix()[i][j].getPieceType()+" "+g.getGridMatrix()[i][j].getPieceOrientation());
            }
        }
        writer.close();
    }

    //

    /**
     *a method that takes a file's name as input and return the grid object it contains
     * @param fileName the name of the file given as an input
     * @return the grid corresponding to the input file
     * @throws IOException throws an exception if a problem occurs while reading the file
     */
    public static Grid readInputFileGrid(String fileName) throws IOException {
        //File file = new File(fileName+".dat");
        File file = new File(fileName);
        Scanner scnr = new Scanner(file);

        int h=0,w=0;
        int nbLine=0;
        String s="";
        while(scnr.hasNextLine()){
            if(nbLine==0) {
                //reading the width of the grid
                w=Integer.parseInt(scnr.nextLine().replaceAll("\\s+",""));
                nbLine++;
            }
            if(nbLine==1) {
                //reading the height of the grid
                h=Integer.parseInt(scnr.nextLine().replaceAll("\\s+",""));
                nbLine++;
            }
            else {
                //reading values of the grid and store them in a string
                s+=scnr.nextLine().replaceAll("\\s+","");
                nbLine++;
            }
        }

        //filling the grid matrix with the values we just stored in the string s
        //the variable k will help us get track on as we look into the string
        Piece[][] m=new Piece[h][w];
        int k=0;
        int type=0;
        int orientation=0;
        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[0].length;j++){
                type=Integer.parseInt(""+s.charAt(k));
                k++;
                orientation=Integer.parseInt(""+s.charAt(k));
                k++;
                m[i][j]= new Piece(type,orientation);
            }
        }
        return new Grid(m);
    }
}
