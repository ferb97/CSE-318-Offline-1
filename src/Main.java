import java.util.*;

import static java.lang.Math.abs;

public class Main {

    public static int calculateHammingDistance(int[][] board, int dim){
        int cnt = 0, val = 0;

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                val++;
                if(board[i][j] != 0){
                   if(i * dim + j + 1 != val)
                      cnt++;
                }
            }
        }

        return cnt;
    }

    public static int calculateManhattanDistance(int[][] board, int dim){
        int val = 1, num = 0;
        int[] rowNo = new int[dim * dim + 1];
        int[] columnNo = new int[dim * dim + 1];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                rowNo[val] = num;
                val++;
            }
            num++;
        }

        val = 1;
        num = 0;

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                columnNo[val] = num;
                val++;
                num++;
            }
            num = 0;
        }

        int dist = 0;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                dist += abs(i - rowNo[board[i][j]]) + abs(j - columnNo[board[i][j]]);
            }
        }

        return dist;
    }

    public static void printBoard(int[][] board, int dim){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isFinalBoard(int[][] board, int dim){
        for(int i = 0; i < dim ; i++){
            for(int j = 0; j < dim; j++){
                if(i == dim -1 && j == dim - 1)
                  continue;
                if(i * dim + j + 1 != board[i][j])
                  return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int dim = scn.nextInt();
        int[][] initialBoard = new int[dim][dim];
        int heuristicOption = 2;

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                initialBoard[i][j] = scn.nextInt();
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        int heuristic = 0;
        if(heuristicOption == 1)
          heuristic = calculateHammingDistance(initialBoard, dim);
        else if(heuristicOption == 2)
          heuristic = calculateManhattanDistance(initialBoard, dim);
        Node node = new Node(initialBoard, dim, 0, heuristic, null);

        if(dim % 2 != 0){
            if(node.getInversionCount() % 2 != 0){
                System.out.println("Unsolvable puzzle");
                return;
            }
        }
        else{
            if(node.getInversionCount() % 2 == 0 && !(node.isBlankSpaceRowFromLastOdd())){
                System.out.println("Unsolvable puzzle");
                return;
            }
            else if(node.getInversionCount() % 2 != 0 && node.isBlankSpaceRowFromLastOdd()){
                System.out.println("Unsolvable puzzle");
                return;
            }
        }

        pq.add(node);
        Node finalNode;

        while(true){
            node = pq.poll();
            int[][] neighbour = new int[dim][dim];

            neighbour = node.upNeighbour();
            Node node1 = new Node(neighbour, dim);
            if(neighbour != null && !(node1.isSameNode(node.getPrevNode()))){
                node1.setDepth(node.getDepth() + 1);
                if(heuristicOption == 1)
                  node1.setHeuristic(calculateHammingDistance(neighbour, dim));
                else if(heuristicOption == 2)
                  node1.setHeuristic(calculateManhattanDistance(neighbour, dim));
                node1.setPrevNode(node);
                pq.add(node1);
                if(isFinalBoard(neighbour, dim)){
                   finalNode = node1;
                   break;
                }
            }

            neighbour = node.downNeighbour();
            Node node2 = new Node(neighbour, dim);
            if(neighbour != null && !(node2.isSameNode(node.getPrevNode()))){
                node2.setDepth(node.getDepth() + 1);
                if(heuristicOption == 1)
                    node2.setHeuristic(calculateHammingDistance(neighbour, dim));
                else if(heuristicOption == 2)
                    node2.setHeuristic(calculateManhattanDistance(neighbour, dim));
                node2.setPrevNode(node);
                pq.add(node2);
                if(isFinalBoard(neighbour, dim)){
                    finalNode = node2;
                    break;
                }
            }

            neighbour = node.leftNeighbour();
            Node node3 = new Node(neighbour, dim);
            if(neighbour != null && !(node3.isSameNode(node.getPrevNode()))){
                node3.setDepth(node.getDepth() + 1);
                if(heuristicOption == 1)
                    node3.setHeuristic(calculateHammingDistance(neighbour, dim));
                else if(heuristicOption == 2)
                    node3.setHeuristic(calculateManhattanDistance(neighbour, dim));
                node3.setPrevNode(node);
                pq.add(node3);
                if(isFinalBoard(neighbour, dim)){
                    finalNode = node3;
                    break;
                }
            }

            neighbour = node.rightNeighbour();
            Node node4 = new Node(neighbour, dim);
            if(neighbour != null && !(node4.isSameNode(node.getPrevNode()))){
                node4.setDepth(node.getDepth() + 1);
                if(heuristicOption == 1)
                    node4.setHeuristic(calculateHammingDistance(neighbour, dim));
                else if(heuristicOption == 2)
                    node4.setHeuristic(calculateManhattanDistance(neighbour, dim));
                node4.setPrevNode(node);
                pq.add(node4);
                if(isFinalBoard(neighbour, dim)){
                    finalNode = node4;
                    break;
                }
            }

        }

        List<Node> nodeList = new ArrayList<>();
        System.out.println("Minimum number of moves = " + finalNode.getDepth());
        System.out.println();
        node = finalNode;

        while(true){
            nodeList.add(node);
            if(node.getPrevNode() == null)
              break;
            node = node.getPrevNode();
        }

        for(int i = nodeList.size() - 1; i >= 0; i--){
            Node node1 = nodeList.get(i);
            printBoard(node1.getBoard(), dim);
            System.out.println();
        }
    }
}

class NodeComparator implements Comparator<Node>{

    @Override
    public int compare(Node node1, Node node2) {
        return (node1.getDepth() + node1.getHeuristic()) - (node2.getDepth() + node2.getHeuristic());
    }
}
