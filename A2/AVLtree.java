class insertexception extends Exception {
    insertexception(String s) {
        super(s);
    }
}

class Avlnode {
    int height = -1;
    Node assigned_node;
    Avlnode parent;
    Avlnode right;
    Avlnode left;

    Avlnode() {
    }

    Avlnode(Node new_node) {
        assigned_node = new_node;
    }
}

public class AVLtree {
    Avlnode root = new Avlnode();

    public boolean isempty() {
        if (root.assigned_node == null) {
            return true;
        } else {
            return false;
        }
    }

    public void insert_root(Node newnode) {
        if (isempty()) {
            root.assigned_node = newnode;
            root.right = new Avlnode();
            root.right.parent = root;
            root.left = new Avlnode();
            root.left.parent = root;
            root.height++;
            root.parent = null;
        }
    }

    public Avlnode search(Node s, Avlnode curr) {
        if (curr.assigned_node == null || curr.assigned_node.ID == s.ID) {
            return curr;
        } else if (curr.assigned_node.ID > s.ID) {
            return search(s, curr.left);
        } else {
            return search(s, curr.right);
        }
    }

    public Avlnode search(int bossid, Avlnode curr) {
        if (curr.assigned_node.ID == bossid || curr.assigned_node.ID == 0) {
            return curr;
        } else if (curr.assigned_node.ID > bossid) {
            return search(bossid, curr.left);
        } else {
            return search(bossid, curr.right);
        }
    }

    public void delete(Node del) {
        Avlnode temp = search(del, root);
        // System.out.println(temp.left.assigned_node.ID);
        // System.out.println(temp.right.assigned_node.ID);
        if (temp.left.assigned_node == null && temp.right.assigned_node == null) {
            Avlnode new_n = new Avlnode();
            new_n.parent = temp.parent;
            if (temp.parent.left == temp) {
                temp.parent.left = new_n;
            } else {
                temp.parent.right = new_n;
            }
            height_update(new_n.parent);
            balance(new_n.parent);
        } else if (temp.left.assigned_node == null || temp.right.assigned_node == null) {
            Avlnode new_n = temp.left.assigned_node == null ? temp.right : temp.left;
            new_n.parent = temp.parent;
            if (temp.parent.left == temp) {
                temp.parent.left = new_n;
            } else {
                temp.parent.right = new_n;
            }
            height_update(new_n.parent);
            balance(new_n.parent);
        } else {
            Avlnode it = temp;
            // System.out.println(temp.assigned_node.ID);
            it = it.left;
            // System.out.println(it.assigned_node.ID);
            while (it.right.assigned_node != null) {
                it = it.right;
                // System.out.println(it.assigned_node.ID);
            }
            // System.out.println();
            // it = it.parent;
            Node kulveer = it.assigned_node;
            // Avlnode new_n = new Avlnode();
            // it.parent.left = it.parent;
            // it.left.parent=temp;
            // System.out.println(it.assigned_node.ID);
            delete(it.assigned_node);
            temp.assigned_node=kulveer;
            height_update(it);
            // arrange(new_n.parent);
            balance(it);
        }
    }

    public void height_update(Avlnode n) {
        Avlnode temp = n;
        while (temp != null) {
            int pre_h = temp.height;
            temp.height = temp.right.height > temp.left.height ? temp.right.height + 1 : temp.left.height + 1;
            // System.out.println("*");
            if (temp.height == pre_h && !(temp.right.height == -1 && temp.left.height == -1)) {
                break;
            }
            temp = temp.parent;
        }
    }

    public void balance(Avlnode n) {
        Avlnode temp = n;
        while (temp != null) {
            // System.out.println("*");
            if ((temp.right.height - temp.left.height > 1 || temp.right.height - temp.left.height < -1)) {
                arrange(temp);
                break;
            }
            temp = temp.parent;
        }
    }

    public void arrange(Avlnode z) {
        Avlnode[] re = new Avlnode[7];
        Avlnode y;
        Avlnode x;
        if (z.left.height < z.right.height) {
            y = z.right;
            re[0] = z.left;
            re[1] = z;
            if (y.left.height < y.right.height) {
                x = y.right;
                re[2] = y.left;
                re[3] = y;
                re[4] = x.left;
                re[5] = x;
                re[6] = x.right;
            } else {
                x = y.left;
                re[2] = x.left;
                re[3] = x;
                re[4] = x.right;
                re[5] = y;
                re[6] = y.right;
            }
        } else {
            y = z.left;
            re[5] = z;
            re[6] = z.right;
            if (y.left.height > y.right.height) {
                x = y.left;
                re[0] = x.left;
                re[1] = x;
                re[2] = x.right;
                re[3] = y;
                re[4] = y.right;
            } else {
                x = y.right;
                re[0] = y.left;
                re[1] = y;
                re[2] = x.left;
                re[3] = x;
                re[4] = x.right;
            }
        }
        re[3].parent = z.parent;
        if (z.parent != null) {
            if (z == z.parent.right) {
                z.parent.right = re[3];
            } else {
                z.parent.left = re[3];
            }
        } else {
            root = re[3];
        }
        re[3].left = re[1];
        re[1].left = re[0];
        re[1].right = re[2];
        re[3].right = re[5];
        re[5].left = re[4];
        re[5].right = re[6];

        re[0].parent = re[1];
        re[2].parent = re[1];
        re[1].parent = re[3];
        re[6].parent = re[5];
        re[4].parent = re[5];
        re[5].parent = re[3];

        height_update(re[1]);
        height_update(re[5]);

    }

    public void insert(Node newnode) throws insertexception {
        if (!isempty()) {
            Avlnode n = search(newnode, root);
            if (n.assigned_node == null) {
                n.assigned_node = newnode;
                n.right = new Avlnode();
                n.right.parent = n;
                n.left = new Avlnode();
                n.left.parent = n;
                n.height++;
                height_update(n);
                balance(n);
            } else
                throw new insertexception("ID already exists");
        } else {
            throw new insertexception("trying to insert at root and root is not empty");
        }
    }
}

// public void delete(Node del) {
//     Avlnode temp = search(del, root);
//     if (temp.left.assigned_node == null && temp.right.assigned_node == null) {
//         Avlnode new_n = new Avlnode();
//         new_n.parent = temp.parent;
//         if (temp.parent.left == temp) {
//             temp.parent.left = new_n;
//         } else {
//             temp.parent.right = new_n;
//         }
//         height_update(new_n.parent);
//         balance(new_n.parent);
//     } else if (temp.left.assigned_node == null || temp.right.assigned_node == null) {
//         Avlnode new_n = temp.left.assigned_node == null ? temp.right : temp.left;
//         new_n.parent = temp.parent;
//         if (temp.parent.left == temp) {
//             temp.parent.left = new_n;
//         } else {
//             temp.parent.right = new_n;
//         }
//         height_update(new_n.parent);
//         balance(new_n.parent);
//     } else {
//         Avlnode it = temp;
//         it = it.left;
//         while (it.right.assigned_node != null) {
//             it = it.right;
//         }
//         // it = it.parent;
//         temp.assigned_node = it.assigned_node;
//         // Avlnode new_n = new Avlnode();
//         it.parent.left = it.parent;
//         it.left.parent=temp;
//         height_update(it);
//         // arrange(new_n.parent);
//         balance(it);
//     }
// }
