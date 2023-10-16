package pt.ipp.isep.esinf.struct.simple;

import pt.ipp.isep.esinf.domain.trip.TimeCoordenates;
import pt.ipp.isep.esinf.struct.auxiliary.Pair;

import java.util.Objects;

public class TwoDTree {

    private class TwoDNode {
        private double x;
        private double y;
        private TwoDNode left;
        private TwoDNode right;

        public TwoDNode(double x, double y, TwoDNode left, TwoDNode right) {
            this.x = x;
            this.y = y;
            this.left = left;
            this.right = right;
        }

        public TwoDNode(double x, double y) {
            this.x = x;
            this.y = y;
        }


        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public TwoDNode getLeft() {
            return left;
        }

        public TwoDNode getRight() {
            return right;
        }

        public void setLeft(TwoDNode left) {
            this.left = left;
        }

        public void setRight(TwoDNode right) {
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TwoDNode twoDNode = (TwoDNode) o;
            return Double.compare(x, twoDNode.x) == 0 && Double.compare(y, twoDNode.y) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


    private TwoDNode root;


    public TwoDTree() {
        root = null;
    }

    public TwoDTree(TwoDNode root) {
        this.root = root;
    }

    public void insert(double x, double y) {
        root = insert(new TwoDNode(x, y), root, true);
    }

    public Pair<Double, Double> root() {
        return new Pair<>(root.x, root.y);
    }

    private TwoDNode insert(TwoDNode node, TwoDNode root, boolean isX) {
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


//    public Pair<Pair<Double, Double>, Pair<Double, Double>> obtainClosestCoordenatesToOriginDestination(Pair<> start, TimeCoordenates end){
//
//    }


}
