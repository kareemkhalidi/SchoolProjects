package base;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implements an abstract threaded subject class used for working with information and updating
 * observers.
 *
 * @author John Gauci
 */

public abstract class Subject extends RobustThread {

    private final ArrayList<Observer> observers;


    private final AtomicReference<Data> data;

    /**
     * Initializes a subject which can hold multiple observers.
     *
     * @author John Gauci
     */
    public Subject() {
        data = new AtomicReference<>();
        observers = new ArrayList<>();
    }

    /**
     * Notifies all the attached observers by calling update on each one.
     *
     * @author John Gauci
     */
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }


    /**
     * Returns the data of the subject.
     *
     * @return the data of the subject
     * @author John Gauci
     */
    public Data getData() {
        return data.get();
    }

    /**
     * Sets the data of the subject to the given data.
     *
     * @param data the data to be setPlaying to
     * @author John Gauci
     */
    public void setData(Data data) {
        this.data.set(data);
    }


}
