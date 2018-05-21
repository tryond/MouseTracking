import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StrokeLogger implements StrokeBuilderListener {

    public void strokeBuilt(Stroke stroke) {
        System.out.println("Stroke Built");
    }


//    public void logStroke(Stroke stroke) {
//        try {
//
//            FileOutputStream = new FileOutputStream(new File("myObjects.txt"));
//
//
//        }
//        catch (FileNotFoundException ex) {
//            System.out.println("File not found");
//            ex.printStackTrace();
//        }
//        catch (IOException ex) {
//            System.out.println("Error initializing stream");
//            ex.printStackTrace();
//        }
//        catch (ClassNotFoundException ex) {
//            System.out.println("Class not found");
//            ex.printStackTrace();
//        }
//
//    }



}
