package server;

import server.NotifyingThread;

public interface ThreadListener {

    //When a thread is finished executing it will call this method below. Each object that
    //is listening to that thread will have to have this method implemented.
    void notifyOfThreadCompletion(final NotifyingThread notifyThread);
}
