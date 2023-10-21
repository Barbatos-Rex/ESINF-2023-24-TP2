package pt.ipp.isep.esinf.struct.searchable;

import pt.ipp.isep.esinf.struct.BST;

import java.util.*;
import java.util.function.Predicate;

public class SearchableBST<N, I extends Comparable<I>>
        implements BST<I>, Searchable<N, I>, Iterable<N> {

    private SearchableNode<N, I> root;


    public SearchableBST(SearchableNode<N, I> root) {
        this.root = root;
    }

    public SearchableBST() {
    }

    @Override
    public Iterator<N> iterator() {
        List<N> result = new ArrayList<>();
        inOrderFiller(result, root);
        return result.iterator();
    }

    public void inOrderFiller(List<N> result, SearchableNode<N, I> root) {
        if (root == null) {
            return;
        }
        inOrderFiller(result, root.getLeft());
        result.add(root.element);
        inOrderFiller(result, root.getRight());
    }

    protected SearchableNode<N, I> getRootNode() {
        return root;
    }

    @Override
    public Optional<I> root() {
        if (root == null) {
            return Optional.empty();
        }
        return Optional.of(root.getIndexKey());
    }

    @Override
    public int height() {
        return height(root);
    }

    private int height(SearchableNode<N, I> elem) {
        if (elem == null) {
            return 0;
        }
        return Math.max(height(elem.getLeft()), height(elem.getRight())) + 1;
    }

    @Override
    public Iterable<I> inOrder() {
        List<I> list = new ArrayList<>();
        inOrder(list, root);
        return list;
    }

    private void inOrder(Collection<I> collection, SearchableNode<N, I> root) {
        if (root == null) {
            return;
        }

        inOrder(collection, root.getLeft());
        collection.add(root.getIndexKey());
        inOrder(collection, root.getRight());
    }

    @Override
    public Iterable<I> preOrder() {
        List<I> list = new ArrayList<>();
        preOrder(list, root);
        return list;
    }

    private void preOrder(Collection<I> collection, SearchableNode<N, I> root) {
        if (root == null) {
            return;
        }
        collection.add(root.getIndexKey());
        inOrder(collection, root.getLeft());
        inOrder(collection, root.getRight());
    }

    @Override
    public Iterable<I> posOrder() {
        List<I> list = new ArrayList<>();
        posOrder(list, root);
        return list;
    }

    private void posOrder(Collection<I> collection, SearchableNode<N, I> root) {
        if (root == null) {
            return;
        }
        inOrder(collection, root.getLeft());
        inOrder(collection, root.getRight());
        collection.add(root.getIndexKey());
    }

    @Override
    @Deprecated
    public void insert(I elem) {
        throw new UnsupportedOperationException("Cannot insert key and element at the same time!");
    }

    @Override
    @Deprecated
    public void remove(I elem) {
        throw new UnsupportedOperationException("Cannot insert key and element at the same time!");
    }

    @Override
    public Map<Integer, Set<I>> nodesByLevel() {
        Map<Integer, Set<I>> result = new HashMap<>();
        nodesByLevels(result, root, 0);
        return result;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(SearchableNode<N, I> root) {
        if (root == null) {
            return 0;
        }
        return size(root.getLeft()) + size(root.getRight()) + 1;
    }

    private void nodesByLevels(Map<Integer, Set<I>> result, SearchableNode<N, I> node, int level) {
        if (node == null) {
            return;
        }
        if (!result.containsKey(level)) {
            result.put(level, new HashSet<>());
        }
        result.get(level).add(node.getIndexKey());
        nodesByLevels(result, node.getLeft(), level + 1);
        nodesByLevels(result, node.getRight(), level + 1);
    }

    @Override
    public Optional<N> search(I id) {
        return search(id, root);
    }

    public void insert(I id, N element) {
        if (root == null) {
            root = new SearchableNode<>(id, element);
        }
        root = insert(new SearchableNode<>(id, element), root);
    }

    private SearchableNode<N, I> insert(SearchableNode<N, I> elem, SearchableNode<N, I> root) {
        if (root == null) {
            return elem;
        }
        int v = root.compareTo(elem);
        if (v < 0) {
            root.setRight(insert(elem, root.getRight()));
        } else if (v > 0) {
            root.setLeft(insert(elem, root.getLeft()));
        } else {
            root.setElement(elem.getElement());
        }
        return root;
    }

    public void remove(I key, N elem) {
        root = delete(new SearchableNode<>(key, elem), root);
    }

    private SearchableNode<N, I> delete(SearchableNode<N, I> element, SearchableNode<N, I> root) {
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

    private SearchableNode<N, I> inOrderSuccessor(SearchableNode<N, I> root) {
        if (root == null) {
            return null;
        }
        SearchableNode<N, I> v = inOrderSuccessor(root.getLeft());
        if (v == null) {
            return root;
        }
        return v;
    }

    private Optional<N> search(I id, SearchableNode<N, I> root) {
        if (root == null) {
            return Optional.empty();
        }

        if (id.equals(root.getIndexKey())) {
            return Optional.of(root.getElement());
        }

        int v = id.compareTo(root.getIndexKey());

        if (v < 0) {
            return search(id, root.getLeft());
        }
        return search(id, root.getRight());
    }

    public Set<N> findAllThatMatch(Predicate<N> predicate) {
        Set<N> result = new HashSet<>();
        findAllThatMatch(predicate, result, root);
        return result;
    }

    private void findAllThatMatch(Predicate<N> predicate, Set<N> set, SearchableNode<N, I> node) {
        if (node == null) {
            return;
        }
        if (predicate.test(node.element)) {
            set.add(node.element);
        }
        findAllThatMatch(predicate, set, node.getLeft());
        findAllThatMatch(predicate, set, node.getRight());
    }

    static class SearchableNode<N, I extends Comparable<I>> implements Comparable<SearchableNode<N, I>> {

        private SearchableNode<N, I> left;
        private I indexKey;
        private N element;
        private SearchableNode<N, I> right;

        public SearchableNode(SearchableNode<N, I> left, I indexKey, N element, SearchableNode<N, I> right) {
            this.left = left;
            this.indexKey = indexKey;
            this.element = element;
            this.right = right;
        }

        public SearchableNode(I indexKey, N element) {
            this.indexKey = indexKey;
            this.element = element;
        }

        public SearchableNode<N, I> getLeft() {
            return left;
        }

        public void setLeft(SearchableNode<N, I> left) {
            this.left = left;
        }

        public I getIndexKey() {
            return indexKey;
        }

        public void setIndexKey(I indexKey) {
            this.indexKey = indexKey;
        }

        public N getElement() {
            return element;
        }

        public void setElement(N element) {
            this.element = element;
        }

        public SearchableNode<N, I> getRight() {
            return right;
        }

        public void setRight(SearchableNode<N, I> right) {
            this.right = right;
        }

        @Override
        public int compareTo(SearchableNode<N, I> o) {
            return indexKey.compareTo(o.indexKey);
        }
    }

}
