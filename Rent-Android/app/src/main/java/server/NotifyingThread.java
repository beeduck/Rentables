
//A class for threads to extend. When the thread is finished executing it will call the
//notifyListeners method and then it will call each individual listener's notifyOfThreadCompletion method.

package server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class NotifyingThread implements Runnable {

    private final Set<ThreadListener> listeners = new CopyOnWriteArraySet<>();
    private ArrayList<String> errors = new ArrayList();
    private ArrayList notifications = new ArrayList();

    public void addListener(ThreadListener listenerToAdd){
        listeners.add(listenerToAdd);
    }

    public void removeListener(ThreadListener listenerToRemove){
        listeners.remove(listenerToRemove);
    }

    public void notifyListeners(){

        for(ThreadListener listener: listeners){

            listener.notifyOfThreadCompletion(this);
        }
    }

    public Object getErrorAt(int index){

        return errors.get(index);
    }

    public Object getNotificationAt(int index){

        return notifications.get(index);
    }

    public ArrayList<String> getErrors(){

        ArrayList copyList = null;
        Iterator errorsIterator = errors.iterator();

        if(!errors.isEmpty()){

            copyList = new ArrayList();

            while(errorsIterator.hasNext()){

                copyList.add(errorsIterator.next());
            }
        }

        return copyList;
    }

    public ArrayList getNotifications(){

        ArrayList copyList = new ArrayList();
        Iterator notificationsIterator = errors.iterator();

        while(notificationsIterator.hasNext()){

            copyList.add(notificationsIterator.next());
        }

        return copyList;
    }

    protected void addError(String object){

        errors.add(object);
    }

    protected void addNotification(Object object){

        notifications.add(object);
    }

    @Override
    public void run(){
        try{

            doRun();
        }finally{

            notifyListeners();
        }
    }

    public abstract void doRun();
}
