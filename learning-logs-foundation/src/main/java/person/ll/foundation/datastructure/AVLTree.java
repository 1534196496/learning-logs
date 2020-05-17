package person.ll.foundation.datastructure;

import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 平衡二叉树
 * @param <T>
 */
@Data
public class AVLTree<T extends Comparable<T>>{
    private AVLNode<T> root;

    public enum Sort{
        Q,Z,H
    }

    public void add(T data) {
        if(this.root == null){
            //第一次插入元素
            this.root = new AVLNode<>(data);
            return;
        }
        AVLNode<T> node = new AVLNode<>(data);
        add(node,this.root);

    }

    /**
     * 前序遍历 递归
     */
    public void QDG(){
        sort(this.root,Sort.Q);
        System.out.println();
    }
    /**
     * 中序遍历 递归
     */
    public void ZDG(){
        sort(this.root,Sort.Z);
        System.out.println();
    }
    /**
     * 后序遍历 递归
     */
    public void HDG(){
        sort(this.root,Sort.H);
        System.out.println();
    }


    /**
     * 删除
     */
    public AVLNode<T> delete(T data){
        return delete(this.root,data);
    }
    private AVLNode<T> delete(AVLNode<T> root,T data){
        if(root == null || data == null) return null;
        int i = data.compareTo(root.data);
        if(i <0){
            root.left = delete(root.left,data);
        }else if(i>0){
            root.right = delete(root.right,data);
        }else{
            if (root.left != null && root.right != null) {
                root.data = findMin(root.right);
                root.right = delete(root.right, root.data);
            } else {
                //删除节点只有一个孩子或者没有孩子
                root = (root.left != null) ? root.left : root.right;
            }

        }

        //以下操作是为了恢复AVL树的平衡性
        int balance = root.balance;
        //左-左情况，这里使用>=而不是>就是为了保证这些情形下使用的是单旋转而不是双旋转
        if (balance > 1 && root.left.balance >= 0) {
            right_rotate(root);
            return root.left;
        }
        //左-右情况
        if (balance > 1 && root.left.balance < 0)
        {
             left_rotate(root.left);
            root.left = root.left.right;
             right_rotate(root);
             return root.left;
        }

        //右-右情况
        if (balance < -1 && root.right.balance <= 0) {
             left_rotate(root);
             return root.right;
        }
        //右-左情况
        if (balance < -1 && root.right.balance > 0)
        {
            right_rotate(root.right);
            root.right = root.right.left;
            left_rotate(root);
            return root.right;
        }
        return root;

    }

    private T findMin(AVLNode<T> root) {
        if (root.left == null) {
            return root.data;
        } else {
            return findMin(root.left);
        }
    }

    /**
     * 查找
     */
    public AVLNode<T> search(T data){
        if(data == null || this.root == null) return null;
        AVLNode<T> node = this.root;
        while (node != null){
            int i = node.data.compareTo(data);
            if(i == 0){
                return node;
            }else if(i < 0){
                node = node.left;
            }else {
                node = node.right;
            }
        }
        return null;
    }


    /**
     * 广度优先遍历
     */
    public void breadth(){
        if(this.root == null){
            return;
        }
        Queue<AVLNode<T>> queue = new LinkedList<AVLNode<T>>();
        queue.add(root);
        while (!queue.isEmpty()){
            AVLNode<T> remove = queue.remove();
            System.out.print(remove.getData()+"  ");
            if(remove.getLeft()!=null){
                queue.add(remove.getLeft());
            }
            if(remove.getRight()!=null){
                queue.add(remove.getRight());
            }
        }
        System.out.println();
    }

    /**
     * 深度优先遍历
     */
    public void deep(){
        if(this.root == null){
            return;
        }
        Stack<AVLNode<T>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            AVLNode<T> pop = stack.pop();
            System.out.print(pop.getData()+"  ");
            if(pop.getRight()!=null){
                stack.add(pop.getRight());
            }
            if(pop.getLeft()!=null){
                stack.add(pop.getLeft());
            }
        }
        System.out.println();
    }


    public static void main(String[] args) {
        for (int i = 2; i <=1024;i=2*i) {
            AVLTree<Integer> init = init((i-1));
            if(!init.isAVLTree(init.root)){
                throw new RuntimeException("垃圾！");
            }else{
                System.out.println(i+"   "+init.root.getData()+"   "+init.root.depth);
//                System.out.println(init.root.getData().intValue() == );
            }
        }
        System.out.println("SUCCESS!");
//        AVLTree<Integer> binaryTtree = init();
////        binaryTtree.delete(100);
////        binaryTtree.delete(80);
////        binaryTtree.delete(60);
//        System.out.println("是一颗平衡二叉树？");
//        System.out.println(binaryTtree.isAVLTree(binaryTtree.root));
//        System.out.println("树的深度：");
//        System.out.println(binaryTtree.root == null?0:binaryTtree.root.depth);
//        System.out.println("广度优先遍历：");
//        binaryTtree.breadth();
//        System.out.println("深度优先遍历：");
//        binaryTtree.deep();
//        System.out.println("前序遍历递归：");
//        binaryTtree.QDG();
//        System.out.println("中序遍历递归：");
//        binaryTtree.ZDG();
//        System.out.println("后序遍历递归：");
//        binaryTtree.HDG();
//        System.out.println();

    }

    /**
     *
     * @return
     */
    private static AVLTree<Integer> init(int size) {
        AVLTree<Integer> binaryTtree = new AVLTree<>();
        for (int i = 1; i <= size; i++) {
            binaryTtree.add(i);
        }

//        binaryTtree.add(80);
//        binaryTtree.add(50);
//        binaryTtree.add(100);
//        binaryTtree.add(40);
//        binaryTtree.add(70);
//        binaryTtree.add(60);
//        binaryTtree.add(7);
//        binaryTtree.add(8);
        return binaryTtree;
    }

    @Data
    public static class AVLNode<T extends Comparable<T>>{

        private T data;//数据
        private AVLNode<T> parent;//父节点
        private AVLNode<T> left;//
        private AVLNode<T> right;
        private int depth;
        private int balance;

        public AVLNode(T data) {
            this.data = data;
            this.balance = 0;
            this.depth = 1;
            this.left = null;
            this.right = null;
        }
    }



    private void add(AVLNode<T> node, AVLNode<T> root){

        if(node.data.compareTo(root.data)<=0){
            //插入左边
            if(root.left==null){
                //左子为空
                node.parent = root;
                root.left = node;
            }else{
                //左子树不为空
                add(node,root.left);
            }
        }else{
            //插入右边
            if(root.right == null){
                //右子树为空
                node.parent = root;
                root.right = node;
            }else{
                //右子树不为空
                add(node,root.right);
            }
        }

        /* 从插入的过程回溯回来的时候，计算平衡因子 */
        root.balance = calcBalance(root);
        /* 左子树高，应该右旋 */
        if (root.balance >= 2) {
            /* 右孙高，先左旋 */
            if (root.left.balance == -1)
                left_rotate(root.left);
            right_rotate(root);
        }

        if (root.balance <= -2) {
            if (root.right.balance == 1)
                right_rotate(root.right);
            left_rotate(root);
        }

        root.balance = calcBalance(root);
        root.depth = calcDepth(root);
    }

    /* 右子为父，父为左子，左孙变右孙 */
    private void left_rotate(AVLNode<T> p) {
        AVLNode<T> pParent = p.parent, pRightSon = p.right;
        AVLNode<T> pLeftGrandSon = pRightSon.left;

        pRightSon.parent = pParent;
        if (pParent != null) {
            if (p == pParent.right)
                pParent.right = pRightSon;
            else if (p == pParent.left)
                pParent.left = pRightSon;
        }else{
            //p 节点为根节点   这里是个坑
            this.root = pRightSon;
        }
        //右子为父
        p.parent = pRightSon;
        //父为左子
        pRightSon.left = p;

        /* 左孙变右孙 */
        p.right = pLeftGrandSon;
        if (pLeftGrandSon != null)
            pLeftGrandSon.parent = p;

        /* 重新计算平衡因子 */
        p.depth = calcDepth(p);
        p.balance = calcBalance(p);

        pRightSon.depth = calcDepth(pRightSon);
        pRightSon.balance = calcBalance(pRightSon);
    }
    /* 左子为父，父为右子，右孙变左孙 */
    private void right_rotate(AVLNode<T> p) {

        AVLNode<T> pParent = p.parent, pLeftSon = p.left;
        AVLNode<T> pLeftGrandSon = pLeftSon.right;

        /* 左子为父 */
        pLeftSon.parent = pParent;
        if (pParent != null) {
            if (p == pParent.left)
                pParent.left = pLeftSon;
            else if (p == pParent.right)
                pParent.right = pLeftSon;
        }else{
            //p 节点为根节点   这里是个坑
            this.root = pLeftSon;
        }
        //父为右子
        pLeftSon.right = p;
        p.parent = pLeftSon;

        /* 右孙变左孙 */
        p.left = pLeftGrandSon;
        if (pLeftGrandSon != null)
            pLeftGrandSon.parent = p;

        /* 重新计算平衡因子 */
        p.depth = calcDepth(p);
        p.balance = calcBalance(p);

        pLeftSon.depth = calcDepth(pLeftSon);
        pLeftSon.balance = calcBalance(pLeftSon);
    }

    /**
     * 计算平衡因子
     * @return
     */
    private int calcBalance(AVLNode<T> node){
        if(node == null) return 0;
        int left_depth;
        int right_depth;

        if (node.left != null)
            left_depth = node.left.depth;
        else
            left_depth = 0;

        if (node.right != null)
            right_depth = node.right.depth;
        else
            right_depth = 0;

        return left_depth - right_depth;
    }


    /**
     * 计算树的深度
     * @param node
     * @return
     */
    private int calcDepth(AVLNode<T> node){
        int depth = 0;
        if(node == null) return 0;
        if(node.left!=null){
            depth = node.left.depth;
        }
        if(node.right!=null && depth<node.right.depth){
            depth = node.right.depth;
        }
        depth++;
        return depth;
    }

    private void sort(AVLNode<T> root,Sort sort){
        if(root == null){
            return;
        }
        switch (sort){
            case Q:
                //前序
                System.out.print(root.getData()+"  ");
                sort(root.getLeft(),sort);
                sort(root.getRight(),sort);
                break;
            case Z:
                //中序
                sort(root.getLeft(),sort);
                System.out.print(root.getData()+"  ");
                sort(root.getRight(),sort);
                break;
            case H:
                //后序
                sort(root.getLeft(),sort);
                sort(root.getRight(),sort);
                System.out.print(root.getData()+"  ");
                break;
        }

    }

    public  boolean isAVLTree(AVLNode<T> root){
        if(root == null) return true;
        if(Math.abs(calcDepth(root.left)-calcDepth(root.right))>1) return false;
        return isAVLTree(root.left) && isAVLTree(root.right);
    }
}
