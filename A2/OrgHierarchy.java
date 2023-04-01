import java.io.*;
import java.util.*;

// Tree node
class Node {
	int ID;
	int Level;
	Node Parent;
	doublylinkedlist children = new doublylinkedlist();

	Node(int id, int level) {
		ID = id;
		Level = level;
	}

	Node(int id) {
		ID = id;
	}
}

public class OrgHierarchy implements OrgHierarchyInterface {

	int size = 0;
	AVLtree avl = new AVLtree();
	// root node
	Node root;

	public boolean isEmpty() {
		// your implementation
		if (avl.root.assigned_node == null) {
			return true;
		}
		return false;
		// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public int size() {
		// your implementation
		// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		return size;
	}

	public int level(int id) throws IllegalIDException {
		// your implementation
		try {
			Node node_of_id = avl.search(id, avl.root).assigned_node;
			return node_of_id.Level;
		} catch (Exception e) {
			throw new IllegalIDException(e.toString());
		}
		// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public void hireOwner(int id) throws NotEmptyException {
		// your implementation
		if (isEmpty()) {
			root = new Node(id, 1);
			avl.insert_root(root);
			size++;
		} else
			throw new NotEmptyException("cant insert, org not empty");
	}

	public void hireEmployee(int id, int bossid) throws IllegalIDException {
		try {
			Node boss = avl.search(bossid, avl.root).assigned_node;
			Node new_emp = new Node(id, boss.Level + 1);
			avl.insert(new_emp);
			boss.children.insert(new_emp, boss);
			size++;
		} catch (Exception e) {
			throw new IllegalIDException(e.toString());
		}
	}

	public void fireEmployee(int id) throws IllegalIDException {
		try {
			Node node_of_id = avl.search(id, avl.root).assigned_node;
			if (node_of_id.children.isempty()) {
				avl.delete(node_of_id);
				node_of_id.Parent.children.delete(id);
				size--;
				// System.out.println("all clear");
				// System.out.println(avl.root.right.left.left.assigned_node.ID);
				// System.out.println(root.children.head.next.next.next.employee.children.head.next.employee.Parent.ID);
			} else {
				throw new IllegalIDException("cannot fire, employee is boss");
			}
		} catch (Exception e) {
			throw new IllegalIDException(e.toString());
		}

	}

	public void fireEmployee(int id, int manageid) throws IllegalIDException {
		// your implementation
		Node node_of_id = avl.search(id, avl.root).assigned_node;

		avl.delete(node_of_id);
		node_of_id.Parent.children.delete(id);
		dllnode it = node_of_id.Parent.children.head.next;
		while (it.employee.ID != manageid) {
			it = it.next;
		}
		dllnode managid = it;
		doublylinkedlist children_deleted = node_of_id.children;
		// update parent
		dllnode parent_up_itr = children_deleted.head.next;
		while (parent_up_itr != children_deleted.tail) {
			parent_up_itr.employee.Parent = managid.employee;
			parent_up_itr = parent_up_itr.next;
		}
		// public void append(dllnode managid, doublylinkedlist children_deleted) {
		children_deleted.head.next.prev = managid.employee.children.tail.prev;
		children_deleted.head.next.prev.next = children_deleted.head.next;
		managid.employee.children.tail.prev = children_deleted.tail.prev;
		managid.employee.children.tail.prev.next = managid.employee.children.tail;
		size--;
		// to be deleted
		Avlnode itr = avl.root;
		while (itr.assigned_node != null) {
			itr = itr.right;
		}
		itr = avl.root;
		while (itr.assigned_node != null) {
			itr = itr.left;
		}
	}

	public int boss(int id) throws IllegalIDException {
		try {
			if (root.ID == id) {
				return -1;
			} else {
				Node node_of_id = avl.search(id, avl.root).assigned_node;
				return node_of_id.Parent.ID;
			}
		} catch (Exception e) {
			throw new IllegalIDException(e.toString());
		}
	}

	public int lowestCommonBoss(int id1, int id2) throws IllegalIDException {
		try {
			int id_1[] = new int[size];
			int id_2[] = new int[size];
			int i = 0, j = 0;
			Node node_of_id1 = avl.search(id1, avl.root).assigned_node;
			Node node_of_id2 = avl.search(id2, avl.root).assigned_node;
			while (node_of_id1.Parent != null) {
				id_1[i] = node_of_id1.Parent.ID;
				// System.out.print(id_1[i]+" ");
				i++;
				node_of_id1 = node_of_id1.Parent;
			}
			// System.out.println();
			while (node_of_id2.Parent != null) {
				id_2[j] = node_of_id2.Parent.ID;
				// System.out.print(id_2[j]+" ");
				j++;
				node_of_id2 = node_of_id2.Parent;
			}
			// System.out.println();
			// for (int k = 0; k < j; k++) {
			// System.out.print(id_2[k]+" ");
			// }
			// System.out.println();
			while (i > 0 && j > 0 && id_1[i - 1] == id_2[j - 1]) {
				i--;
				j--;
				// System.out.println("all clear");
			}
			return id_1[i];
		} catch (Exception e) {
			throw new IllegalIDException(e.toString());
		}

		// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public String toString(int id) throws IllegalIDException {
		// your implementation
		try {
			String s = "" + '"';
			Node node_of_id = avl.search(id, avl.root).assigned_node;
			doublylinkedlist que = new doublylinkedlist();
			que.enqueue(node_of_id);
			dllnode itr = new dllnode();
			s = s + String.valueOf(node_of_id.ID) + ",";
			int b = 1;
			// while(que.dll_size!=0 && node_of_id.children.dll_size!=0){
			while (que.dll_size != 0) {
				// System.out.println("all clear");
				while (b != 0) {
					node_of_id = que.dequeue();
					itr = node_of_id.children.head.next;
					while (itr != node_of_id.children.tail) {
						que.enqueue(itr.employee);
						itr = itr.next;
					}
					b--;
				}

				int w = que.dll_size;
				b = que.dll_size;
				int[] z = new int[w];
				itr = que.tail.prev;
				while (w != 0) {
					z[w - 1] = itr.employee.ID;
					w--;
					itr = itr.prev;

				}
				// sorting array using quick sort;
				for (int i = 0; i < z.length; i++) {
					for (int j = i + 1; j < z.length; j++) {
						int temp = 0;
						if (z[j] < z[i]) {
							temp = z[i];
							z[i] = z[j];
							z[j] = temp;
						}
					}
				}
				for (int i = 0; i < z.length; i++) {
					s = s + String.valueOf(z[i]) + " ";
				}
				s = s.substring(0, s.length() - 1);
				s = s + ",";
			}
			s = s.substring(0, s.length() - 1);
			s = s + '"';
			return s;
		} catch (Exception e) {
			throw new IllegalIDException(e.toString());
		}

		// throw new java.lang.UnsupportedOperationException("Not implemented yet.");

	}
}
