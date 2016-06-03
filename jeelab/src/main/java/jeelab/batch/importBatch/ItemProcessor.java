package jeelab.batch.importBatch;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import javax.inject.Named;

import jeelab.model.entity.User;

@Named
public class ItemProcessor implements javax.batch.api.chunk.ItemProcessor {
    SimpleDateFormat format = new SimpleDateFormat("M/dd/yy");
 
    @Override
    public User processItem(Object t) {
        System.out.println("processItem: " + t);
         
        StringTokenizer tokens = new StringTokenizer((String)t, ";");
 
        User user = new User();
        
        user.setFirstname(tokens.nextToken());
        user.setLastname(tokens.nextToken());
        user.setEmail(tokens.nextToken());
         
        return user;
    }
}