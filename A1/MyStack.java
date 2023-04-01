public class MyStack {
    Object[] array = new Object[1];
    int top = -1;

    public void push(Object o) {
        if (o != null) {
            if (top + 1 == array.length) {
                expand_array();
            }
            array[top + 1] = o;
            top++;
        }
    }

    public Object pop() throws EmptyStackException {
        if (top == -1) {
            throw new EmptyStackException("EmptyStackException");
        }
        top--;
        return array[top + 1];
    }

    public Object top() throws EmptyStackException {
        if (top == -1) {
            throw new EmptyStackException("EmptyStackException");
        }
        return array[top];
    }

    public boolean isEmpty() {
        if (top == -1) {
            return true;
        }
        return false;
    }

    public String toString() {
        String s = "[";
        for (int i = top; i >= 0; i--) {
            if (i == 0) {
                s += array[0];
            } else
                s += array[i] + ", ";
        }
        s += "]";
        return s;
    }

    public void expand_array() {
        Object[] temparray = new Object[2 * array.length];
        for (int i = 0; i < array.length; i++) {
            temparray[i] = array[i];
        }
        array = temparray;
    }
}