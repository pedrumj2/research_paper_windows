package textFiles;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
/**
 * Created by Pedrum on 1/20/2017.
 */
public class txtWrite {
    BufferedWriter writer;
    public txtWrite(String __path)throws IOException{
        writer = new BufferedWriter(new FileWriter(__path));
    }
    public void write(String __text) throws IOException{
        writer.write(__text);
    }
}
