package base;

/**
 * Implements an abstract threaded Observer class for observing a subject.
 *
 * @author John Gauci
 */
public abstract class Observer extends RobustThread {

    private final Subject subject;
    private Data data;

    /**
     * Initializes an observer with the subject to observe.
     *
     * @param subject the subject to observe
     * @author John Gauci
     */
    public Observer(Subject subject) {
        subject.addObserver(this);
        this.subject = subject;
        data = subject.getData();
    }

    /**
     * Returns the subject
     *
     * @return the subject
     * @author John Gauci
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Updates the subject by retrieving the data of the subject.
     *
     * @author John Gauci
     */
    public void update() {
        data = subject.getData();
    }

    /**
     * Gets the data of the subject.
     *
     * @return the data of the subject.
     * @author John Gauci
     */
    public Data getData() {
        return data;
    }


}
