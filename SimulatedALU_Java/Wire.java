//Purpose: represents a wire in the ALU where false is 0 and true is 1

public class Wire {

    boolean value;

    public void set(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    public Wire(){
        this.value = false;
    }

}
