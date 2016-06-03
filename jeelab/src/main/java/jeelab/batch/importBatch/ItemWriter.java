package jeelab.batch.importBatch;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;

import jeelab.exception.UserUnavailableException;
import jeelab.model.dao.UserDao;
import jeelab.model.entity.User;

@Named
public class ItemWriter extends AbstractItemWriter {
    
	@Inject
	private UserDao userDao;

   @Override
   public void writeItems(List list) {
       System.out.println("writeItems: " + list);
       for (Object o : list) {
    	   User user = (User) o;
           user.setPassword("ChangeMe");
           try{
        	   userDao.save(user);
           } catch (UserUnavailableException ex){
        	   System.out.println("writeItems: User exists, skip");
           }
       }
   }
}