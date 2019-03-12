package main.java;

import java.io.*;

public class StrokeLogger implements StrokeBuilderListener {

    private final String N = "n_stroke.txt";
    private final String NW = "nw_stroke.txt";
    private final String W = "w_stroke.txt";
    private final String SW = "sw_stroke.txt";
    private final String S = "s_stroke.txt";
    private final String SE = "se_stroke.txt";
    private final String E = "e_stroke.txt";
    private final String NE = "ne_stroke.txt";



    public void strokeBuilt(Stroke stroke) {

        stroke.normalizeStroke();
        logStroke(stroke);
    }


    public StrokeLogger() {

        // try



    }

    public void logStroke(Stroke stroke) {

        try(FileWriter fw = new FileWriter("myfile.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("the text");
            //more code
            out.println("more text");
            //more code
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

    }



}
