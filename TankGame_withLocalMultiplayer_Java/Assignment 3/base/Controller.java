package base;

/**
 * Implements an abstract strategy class for communicating between a subject and an observer.
 *
 * @author John Gauci
 */
public abstract class Controller {

    private final Subject subject;

    /**
     * Initializes a controller with a subject to communicate to
     *
     * @param subject the subject to communicate to
     * @author John Gauci
     */
    public Controller(Subject subject) {
        this.subject = subject;
    }

    /**
     * Returns the subject to communicate to
     *
     * @return the subject to communicate to
     * @author John Gauci
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Processes the given keyInput from a view
     *
     * @param keyInput the key input
     * @author John Gauci
     */
    public abstract void processKeyInput(KeyInput keyInput);

}
