package pt.ipp.isep.esinf.struct.simple;

import pt.ipp.isep.esinf.struct.auxiliary.Pair;

public class TwoDTree<E> {

    public static class TwoDNode<E> {

        private double x;
        private double y;
        private TwoDNode<E> left;

        private TwoDNode<E> right;
        private E element;

        public TwoDNode(double value1, double value2, TwoDNode<E> left, TwoDNode<E> right, E element) {
            this.x = value1;
            this.y = value2;
            this.left = left;
            this.right = right;
            this.element = element;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public TwoDNode(double value1, double value2, E element) {
            this.x = value1;
            this.y = value2;
            left = null;
            right = null;
            this.element = element;

        }

        public TwoDNode<E> getLeft() {
            return left;
        }

        public TwoDNode<E> getRight() {
            return right;
        }

        public void setLeft(TwoDNode<E> left) {
            this.left = left;
        }

        public void setRight(TwoDNode<E> right) {
            this.right = right;
        }

        public E getElement() {
            return element;
        }
    }

    protected TwoDNode<E> getRoot() {
        return root;
    }

    private TwoDNode<E> root;


    public TwoDTree() {
        root = null;
    }

    public TwoDTree(TwoDNode<E> root) {
        this.root = root;
    }

    public void insert(double x, double y, E element) {
        root = insert(new TwoDNode<>(x, y, element), root, true);
    }

    public Pair<Double, Double> root() {
        return new Pair<>(root.x, root.y);
    }

    private TwoDNode<E> insert(TwoDNode<E> node, TwoDNode<E> root, boolean isX) {
        if (root == null) {
            return node;
        }

        double rootVal = 0;
        double nodeVal = 0;


        if (isX) {
            rootVal = root.x;
            nodeVal = node.x;
        } else {
            rootVal = root.y;
            nodeVal = node.y;
        }


        if (Double.compare(nodeVal, rootVal) < 0) {
            root.setLeft(insert(node, root.getLeft(), !isX));
        } else if (Double.compare(nodeVal, rootVal) > 0) {
            root.setRight(insert(node, root.getRight(), !isX));
        }
        return root;
    }





}
