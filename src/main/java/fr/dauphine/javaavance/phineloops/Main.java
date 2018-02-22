package fr.dauphine.javaavance.phineloops;

import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.model.ReaderWriter;
import fr.dauphine.javaavance.phineloops.model.Solver;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class Main {
    private static String inputFile= null;  
    private static String outputFile= null;
    private static Integer width = -1;
    private static Integer height = -1;
    private static Integer maxcc = -1; 
    
    
    public static void main(String[] args) {
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        
        options.addOption("g", "generate ", true, "Generate a grid of size height x width.");
        options.addOption("c", "check", true, "Check whether the grid in <arg> is solved.");
        
        options.addOption("s", "solve", true, "Solve the grid stored in <arg>.");   
        options.addOption("o", "output", true, "Store the generated or solved grid in <arg>. (Use only with --generate and --solve.)");
        options.addOption("t", "threads", true, "Maximum number of solver threads. (Use only with --solve.)");
        options.addOption("x", "nbcc", true, "Maximum number of connected components. (Use only with --generate.)");
        options.addOption("h", "help", false, "Display this help");
        
        try {
            cmd = parser.parse( options, args);         
        } catch (ParseException e) {
            System.err.println("Error: invalid command line format.");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "phineloops", options );
            System.exit(1);
        }       
                
    try{    
        if( cmd.hasOption( "g" ) ) {
            System.out.println("Running phineloops generator.");
            String[] gridformat = cmd.getOptionValue( "g" ).split("x");
            width = Integer.parseInt(gridformat[0]);
            height = Integer.parseInt(gridformat[1]); 
            if(! cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");
            outputFile = cmd.getOptionValue( "o" );

            // generate grid and store it to outputFile...
            //...
            Grid g=new Grid(width,height);
            g.generateValidGrid();
            //System.out.println(g);
            try {
                ReaderWriter.writeOutputFileGrid(outputFile,g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if( cmd.hasOption( "s" ) ) {
            System.out.println("Running phineloops solver.");
            inputFile = cmd.getOptionValue( "s" );
            if(! cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");      
            outputFile = cmd.getOptionValue( "o" );
            boolean solved = false; 
        
            // load grid from inputFile, solve it and store result to outputFile...
            // ...
            Grid g;
            try {
                g=ReaderWriter.readInputFileGrid(inputFile);
                solved= Solver.solve(g)!=null;
                ReaderWriter.writeOutputFileGrid(outputFile,g);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("SOLVED: " + solved);            
        }
        
        else if( cmd.hasOption( "c" )) {
            System.out.println("Running phineloops checker.");
            inputFile = cmd.getOptionValue( "c" );
            boolean solved = false; 

            // load grid from inputFile and check if it is solved... 
            //...
            Grid g;
            try {
                g=ReaderWriter.readInputFileGrid(inputFile+".dat");
                solved=g.check(g.getHeight(),g.getWidth());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("SOLVED: " + solved);

        }
        else {
            throw new ParseException("You must specify at least one of the following options: -generate -check -solve ");           
        }
        } catch (ParseException e) {
        // TODO Auto-generated catch block
            System.err.println("Error parsing commandline : " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "phineloops", options );         
            System.exit(1); // exit with error      
    }
        System.exit(0); // exit with success                            
    }
}
