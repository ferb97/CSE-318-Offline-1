public class Node {
    private int[][] board;
    private int dim;
    private int depth;
    private int heuristic;
    private Node prevNode;

    public Node(int[][] board, int dim, int depth, int heuristic, Node prevNode) {
        this.board = board;
        this.dim = dim;
        this.depth = depth;
        this.heuristic = heuristic;
        this.prevNode = prevNode;
    }

    public Node(int[][] board, int dim){
        this.board = board;
        this.dim = dim;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    public int[][] upNeighbour(){
        int[][] up = new int[dim][dim];
        int blankRowNo = -1, blankColumnNo = -1;

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if(board[i][j] == 0){
                   blankRowNo = i;
                   blankColumnNo = j;
                }
                up[i][j] = board[i][j];
            }
        }

        if(blankRowNo + 1 < dim){
           up[blankRowNo][blankColumnNo] = board[blankRowNo + 1][blankColumnNo];
           up[blankRowNo + 1][blankColumnNo] = 0;
           return up;
        }
        else
           return null;
    }

    public int[][] downNeighbour(){
        int[][] down = new int[dim][dim];
        int blankRowNo = -1, blankColumnNo = -1;

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if(board[i][j] == 0){
                    blankRowNo = i;
                    blankColumnNo = j;
                }
                down[i][j] = board[i][j];
            }
        }

        if(blankRowNo - 1 >= 0){
            down[blankRowNo][blankColumnNo] = board[blankRowNo - 1][blankColumnNo];
            down[blankRowNo - 1][blankColumnNo] = 0;
            return down;
        }
        else
            return null;
    }

    public int[][] leftNeighbour(){
        int[][] left = new int[dim][dim];
        int blankRowNo = -1, blankColumnNo = -1;

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if(board[i][j] == 0){
                    blankRowNo = i;
                    blankColumnNo = j;
                }
                left[i][j] = board[i][j];
            }
        }

        if(blankColumnNo + 1 < dim){
            left[blankRowNo][blankColumnNo] = board[blankRowNo][blankColumnNo + 1];
            left[blankRowNo][blankColumnNo + 1] = 0;
            return left;
        }
        else
            return null;
    }

    public int[][] rightNeighbour(){
        int[][] right = new int[dim][dim];
        int blankRowNo = -1, blankColumnNo = -1;

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if(board[i][j] == 0){
                    blankRowNo = i;
                    blankColumnNo = j;
                }
                right[i][j] = board[i][j];
            }
        }

        if(blankColumnNo - 1 >= 0){
            right[blankRowNo][blankColumnNo] = board[blankRowNo][blankColumnNo - 1];
            right[blankRowNo][blankColumnNo - 1] = 0;
            return right;
        }
        else
            return null;
    }

    public int getInversionCount(){
        int[] tmp = new int[dim * dim];
        int index = 0;

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                tmp[index] = board[i][j];
                index++;
            }
        }

        int cnt = 0;
        for(int i = 0; i < dim* dim; i++){
            for(int j = i + 1; j < dim * dim; j++){
                if(tmp[i] != 0 && tmp[j] != 0 && tmp[i] > tmp[j]){
                   cnt++;
                }
            }
        }

        return cnt;
    }

    public boolean isBlankSpaceRowFromLastOdd(){
        int blankSpaceRowNo = -1;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if(board[i][j] == 0){
                   blankSpaceRowNo = i;
                }
            }
        }

        if((dim - blankSpaceRowNo) % 2 == 0)
           return false;
        return true;
    }

    public boolean isSameNode(Node node){
        if(node == null)
           return false;
        int[][] board1 = node.getBoard();

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
               if(board[i][j] != board1[i][j]){
                  return false;
               }
            }
        }

        return true;
    }
}
