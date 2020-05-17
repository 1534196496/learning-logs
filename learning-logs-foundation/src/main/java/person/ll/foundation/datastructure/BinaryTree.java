package person.ll.foundation.datastructure;

import lombok.Data;

import java.util.*;

/**
 * 二叉树
 * @param
 */
@Data
public class BinaryTree<T extends Comparable<T>> {
    public enum Sort{
        Q,Z,H
    }
    /**
     *  添加元素
     * @param data
     */
    public void add(T data){
        if(this.root == null){
            //第一次插入元素
            this.root = new TreeNode<T>(data);
            return;
        }
        TreeNode<T> newTreeNode = new TreeNode<T>(data);
        add(newTreeNode,this.root);
    }

    private TreeNode<T> root;




    /**
     * 删除节点
     * @param args
     */

    /**
     * 计算树的深度
     */
    public int getDeep(){
        int deep = getDeep(this.root);
        return deep;
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
     * 广度优先遍历
     */
    public void breadth(){
        if(this.root == null){
            return;
        }
        Queue<TreeNode<T>> queue = new LinkedList<TreeNode<T>>();
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode<T> remove = queue.remove();
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
        Stack<TreeNode<T>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode<T> pop = stack.pop();
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

    @Data
    public static class TreeNode<T extends Comparable<T>>{
        private T data;//数据
        private TreeNode<T> parent;//父节点
        private TreeNode<T> left;//
        private TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
        }
    }


    /**
     *
     *               6
     *            5    8
     *          2  6 7   9
     *                      11
     * @param args
     */
    public static void main(String[] args) {
        BinaryTree<Integer> binaryTree = init();
        System.out.println("树的深度：");
        System.out.println(binaryTree.getDeep());
        System.out.println("广度优先遍历：");
        binaryTree.breadth();
        System.out.println("深度优先遍历：");
        binaryTree.deep();
        System.out.println("前序遍历递归：");
        binaryTree.QDG();
        System.out.println("中序遍历递归：");
        binaryTree.ZDG();
        System.out.println("后序遍历递归：");
        binaryTree.HDG();
        System.out.println();

    }

    private static BinaryTree<Integer> init() {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.add(80);
        binaryTree.add(60);
        binaryTree.add(100);
        binaryTree.add(90);
        binaryTree.add(120);
        binaryTree.add(95);
        return binaryTree;
    }

    /**
     * 将数据插入 以root为根的子树下
     * @param newTreeNode
     * @param root
     */
    private void add(TreeNode<T> newTreeNode, TreeNode<T> root){

        if(newTreeNode.getData().compareTo(root.getData())<=0){
            //插入左边
            if(root.getLeft()==null){
                //左子为空
                newTreeNode.setParent(root);
                root.setLeft(newTreeNode);
            }else{
                //左子树不为空
                add(newTreeNode,root.getLeft());
            }
        }else{
            //插入右边
            if(root.getRight() == null){
                //右子树为空
                newTreeNode.setParent(root);
                root.setRight(newTreeNode);
            }else{
                //右子树不为空
                add(newTreeNode,root.getRight());
            }
        }
    }
    private int getDeep(TreeNode treeNode){
        if(treeNode == null) return 0;
        return Math.max(getDeep(treeNode.getLeft())+1,getDeep(treeNode.getRight())+1);
    }
    private void sort(TreeNode<T> root,Sort sort){
        if(root == null)return;
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
}
