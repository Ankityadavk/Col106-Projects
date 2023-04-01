class dllnode {
    Node employee;
    // Node boss;
    dllnode next;
    dllnode prev;

    dllnode() {
    }

    dllnode(Node nobject) {
        employee = nobject;
    }
}

public class doublylinkedlist {
    int dll_size = 0;
    dllnode head = new dllnode();
    dllnode tail = new dllnode();

    doublylinkedlist() {
        head.next = tail;
        tail.prev = head;
    }

    public boolean isempty() {
        if (head.next == tail) {
            return true;
        } else
            return false;
    }

    public void insert(Node new_node_employee, Node boss) {
        dllnode new_dll_Node = new dllnode();
        new_dll_Node.employee = new_node_employee;
        new_dll_Node.employee.Parent = boss;
        new_dll_Node.prev = tail.prev;
        new_dll_Node.next = tail;
        tail.prev.next = new_dll_Node;
        tail.prev = new_dll_Node;
        dll_size++;
    }

    public void delete(int data) {
        dllnode itr = head;
        while (itr.next.employee.ID != data) {
            itr = itr.next;
        }
        itr.next.next.prev = itr;
        itr.next = itr.next.next;
        dll_size--;
    }

    public void enqueue(Node enque) {
        dllnode n = new dllnode(enque);
        tail.prev.next = n;
        n.prev = tail.prev;
        n.next = tail;
        tail.prev = n;
        dll_size++;
    }

    public Node dequeue() {
        Node exit = head.next.employee;
        head.next.next.prev = head;
        head.next = head.next.next;
        dll_size--;
        return exit;
    }
}
