package base;

import org.eclipse.swt.events.KeyEvent;


/**
 * Class for an SWT Key Input
 *
 * @author John Gauci
 */
public class SWTKeyInput extends KeyInput {

    /**
     * Constructor for an SWT Key Input
     *
     * @param keyEvent the key input event
     * @author John Gauci
     */
    public SWTKeyInput(KeyEvent keyEvent) {
        setCharacter(keyEvent.character);
        setKeycode(keyEvent.keyCode);
    }

}
