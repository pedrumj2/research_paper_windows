import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Pedrum on 1/14/2017.
 */
public class csvRead {
    private static BufferedReader _bufferReader;
    public static String line;
    public csvRead() throws FileNotFoundException, IOException{
        String _csvPath;

        _csvPath = getCSVPath();
        initFileReader(_csvPath);
        line= readLline();

    }


    private static String getCSVPath(){
        String _curPath;
        _curPath =  System.getProperty("user.dir") +  "\\output.csv";
        return _curPath;
    }

    public static String readLline() throws IOException {

        line = _bufferReader.readLine();
        return line;

    }
    private static void initFileReader(String __path) throws FileNotFoundException {
        _bufferReader = new BufferedReader(new FileReader(__path));
    }
}
