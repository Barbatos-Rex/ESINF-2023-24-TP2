package pt.ipp.isep.esinf.struct.simple;

import pt.ipp.isep.esinf.struct.BST;

import java.util.*;

public class SimpleBST<N extends Comparable<N>> implements BST<N> {

    private Node<N> root;


    @Override
    public Optional<N> root() {
        return Optional.ofNullable(root).map(Node::getElement);
    }

    @Override
    public int height() {
        return height(root);
    }

    private int height(Node<N> elem) {
        if (elem == null) {
            return 0;
        }
        return Math.max(height(elem.getLeft()), height(elem.getRight())) + 1;
    }


    @Override
    public Iterable<N> inOrder() {
        List<N> list = new ArrayList<>();
        inOrder(list, root);
        return list;
    }

    private void inOrder(Collection<N> collection, Node<N> root) {
        if (root == null) {
            return;
        }

        inOrder(collection, root.getLeft());
        collection.add(root.getElement());
        inOrder(collection, root.getRight());
    }

    @Override
    public Iterable<N> preOrder() {
        List<N> list = new ArrayList<>();
        preOrder(list, root);
        return list;
    }

    private void preOrder(Collection<N> collection, Node<N> root) {
        if (root == null) {
            return;
        }
        collection.add(root.getElement());
        inOrder(collection, root.getLeft());
        inOrder(collection, root.getRight());
    }

    @Override
    public Iterable<N> posOrder() {
        List<N> list = new ArrayList<>();
        posOrder(list, root);
        return list;
    }

    private void posOrder(Collection<N> collection, Node<N> root) {
        if (root == null) {
            return;
        }
        inOrder(collection, root.getLeft());
        inOrder(collection, root.getRight());
        collection.add(root.getElement());
    }

    @Override
    public void insert(N elem) {
        root = insert(new Node<>(elem), root);
    }

    private Node<N> insert(Node<N> elem, Node<N> root) {
        if (root == null) {
            return elem;
        }
        int v = root.compareTo(elem);
        if (v < 0) {
            root.setRight(insert(root.getRight(), elem));
        } else if (v > 0) {
            root.setLeft(insert(root.getLeft(), elem));
        } else {
            root.setElement(elem.getElement());
        }
        return root;
    }

    @Override
    public void remove(N elem) {
        root = delete(new Node<>(elem), root);
    }


    public Node<N> delete(Node<N> element, Node<N> root) {
        if (root == null) {
            return root;
        }

        int v = element.compareTo(root);

        if (v < 0) {
            root.setLeft(delete(element, root.getLeft()));
        } else if (v > 0) {
            root.setRight(delete(element, root.getRight()));
        } else {
            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            }
            root = inOrderSuccessor(root.getRight());
            root.setRight(delete(element, root.getRight()));
        }
        return root;
    }

    private Node<N> inOrderSuccessor(Node<N> root) {
        if (root == null) {
            return null;
        }
        Node<N> v = inOrderSuccessor(root.getLeft());
        if (v == null) {
            return root;
        }
        return v;
    }


    @Override
    public Map<Integer, Set<N>> nodesByLevel() {
        Map<Integer, Set<N>> result = new HashMap<>();
        nodesByLevels(result, root, 0);
        return result;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(BST.Node<N> root) {
        if (root == null) {
            return 0;
        }
        return size(root.getLeft()) + size(root.getRight()) + 1;
    }


    private void nodesByLevels(Map<Integer, Set<N>> result, Node<N> node, int level) {
        if (node == null) {
            return;
        }
        if (!result.containsKey(level)) {
            result.put(level, new HashSet<>());
        }
        result.get(level).add(node.getElement());
        nodesByLevels(result, node.getLeft(), level + 1);
        nodesByLevels(result, node.getRight(), level + 1);
    }
}
