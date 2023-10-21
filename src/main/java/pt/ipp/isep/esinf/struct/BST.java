package pt.ipp.isep.esinf.struct;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface BST<N extends Comparable<N>> {

    Optional<N> root();

    int height();

    Iterable<N> inOrder();

    Iterable<N> preOrder();

    Iterable<N> posOrder();

    void insert(N elem);

    void remove(N elem);

    Map<Integer, Set<N>> nodesByLevel();

    int size();

    class Node<N extends Comparable<N>> implements Comparable<Node<N>> {
        private Node<N> left;
        private N element;
        private Node<N> right;

        public Node(Node<N> left, N element, Node<N> right) {
            this.left = left;
            this.element = element;
            this.right = right;
        }

        public Node(N element) {
            this.element = element;
        }

        public Node<N> getLeft() {
            return left;
        }

        public void setLeft(Node<N> left) {
            this.left = left;
        }

        public N getElement() {
            return element;
        }

        public void setElement(N element) {
            this.element = element;
        }

        public Node<N> getRight() {
            return right;
        }

        public void setRight(Node<N> right) {
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(left, node.left) && Objects.equals(element, node.element) && Objects.equals(right, node.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, element, right);
        }

        @Override
        public int compareTo(Node<N> o) {
            return getElement().compareTo(o.getElement());
        }
    }

}
