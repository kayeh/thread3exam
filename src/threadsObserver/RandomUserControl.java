package threadsObserver;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import randomperson.RandomUser;
import randomperson.RandomUserGenerator;

public class RandomUserControl extends Thread {
    
    
    Lock lock = new ReentrantLock();
    
    List<RandomObserver> observers = new ArrayList();
    
    public void registerRandomObserver(RandomObserver o) {
        observers.add(o);
    }
    
    @Override
    public void run(){
        RandomUser user = fetchRandomUser();
        for (RandomObserver observer : observers) {
            observer.update(user.getFirstName(), user.getLastName(), user.getStreet(), user.getCity(), user.getEmail());
        }
    }
  
  public RandomUser fetchRandomUser() {   
   RandomUser user= null;
    try {
        lock.lock();
      user = RandomUserGenerator.getRandomUser();
    } catch (InterruptedException ex) {
      Logger.getLogger(RandomUserControl.class.getName()).log(Level.SEVERE, null, ex);
    } finally{
        lock.unlock();
    }
   return user;
  }
}
