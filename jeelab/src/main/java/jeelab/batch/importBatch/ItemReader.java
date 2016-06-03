package jeelab.batch.importBatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

@Named
public class ItemReader extends AbstractItemReader {
	private BufferedReader reader;
	 
    @Override
    public void open(Serializable checkpoint) throws Exception {
        reader = new BufferedReader(
                new InputStreamReader(
                    this
                    .getClass()
                    .getClassLoader()
                    .getResourceAsStream("/META-INF/users.csv")
                )
            );
    }
 
    @Override
    public String readItem() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ItemReader.class.getName()).log(Level.ERROR, null, ex);
        }
        return null;
    }
}
