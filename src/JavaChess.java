




public class JavaChess {

    public static void main(String[] args){
        ChessPiece [][] chessBoard = new ChessPiece[8][8];
        for(int i=0;i<8;i++){
            chessBoard[1][i] = new Pawn(1, "1" + i);
        }
        for(int i=0;i<8;i++){
            chessBoard[6][i] = new Pawn(-1, "6" + i);
        }

        if(chessBoard[1][0].getPieceClass()=='P'){
            ((Pawn) chessBoard[1][0]).initiateMove("20", chessBoard);
            System.out.println("genesis");
        }
        System.out.println(chessBoard[2][0].returnColor());
        System.out.println(chessBoard[1][1].sameClass(chessBoard[2][0]));
        chessBoard[0][1] = new Rook(1, "01");
        ((Rook)(chessBoard[0][1])).initiateMove("03",chessBoard);
        System.out.println(chessBoard[0][3].getPieceClass());
    
        
        
    
        
        
    }
    

    

}

class ChessPiece{
    protected String pieceClass;
    protected int color;
    protected String currentPosition;

    //Checks if the square is empty
    public boolean freeToMove(String nextPosition, ChessPiece [][] board){
        int xPosition = nextPosition.charAt(0) - '0';
        int yPosition = nextPosition.charAt(1) - '0';
        boolean canMove = true;
        if(xPosition<0 || yPosition<0 || xPosition>=8 || yPosition>=8){
            return false;
        }
        
        if (board[xPosition][yPosition]!=null){
           canMove = !(board[xPosition][yPosition].returnColor()==(this.color));
        }   
     return canMove;
    }
    //Returns the color of the piece
    public int returnColor(){
        return color;
    }

    //Checks if the chosen move is possible by the piece
    public String possiblePosition(String entry, int moveX , int moveY){
        int xPosition = (int)entry.charAt(0) - '0' + moveX;
        int yPosition = (int)entry.charAt(1) - '0'+ moveY;
        return "" + xPosition + yPosition;
    }

    //Finalizes the move of the piece by changing the actual position in the array
    public void movePiece(String nextPosition , ChessPiece [][] board){

        int currentX = currentPosition.charAt(0) - '0';
        int currentY = currentPosition.charAt(1) - '0';
        int nextX = nextPosition.charAt(0) - '0';
        int nextY = nextPosition.charAt(1) - '0';
        
        board [currentX][currentY]=null;
        board [nextX][nextY]=this;
        currentPosition = nextPosition;
        
    }

    //Returns class
    public char getPieceClass(){
        return pieceClass.charAt(0);
    }
    //Checks if two pieces are of the same class
    public boolean sameClass(ChessPiece piece){

        return (piece.getPieceClass()==getPieceClass());
        
    }

    //Verify the pieces class

    


    

}

class Pawn extends ChessPiece{
    //Constructor of pawn, sets color and the starting position
    Pawn(int color,String currentPosition){
        this.color = color;
        this.currentPosition = currentPosition;
        this.pieceClass = "Pawn";
    }

    //Checks if the square is free and if the position can be possible
    
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        if(freeToMove(nextPosition, board)){
            if(possiblePosition(currentPosition,1*color,0).equals(nextPosition)){
                movePiece(nextPosition, board);
            }
            

        }
    }
    
}

class Rook extends ChessPiece{
    //Constructor of pawn, sets color and the starting position
    Rook(int color,String currentPosition){
        this.color = color;
        this.currentPosition = currentPosition;
        this.pieceClass = "Rook";
    }

    //Checks if the square is free and if the position can be possible
    
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        
        int xCurrent = (int)currentPosition.charAt(0) - '0';
        int yCurrent = (int)currentPosition.charAt(1) - '0';
        int xPosition = (int)nextPosition.charAt(0) - '0';
        int yPosition = (int)nextPosition.charAt(1) - '0';
        boolean positioning = (possiblePosition(currentPosition,Math.abs(xCurrent-xPosition),0).equals(nextPosition))||(possiblePosition(currentPosition,0,Math.abs(yCurrent-yPosition)).equals(nextPosition));
        if(freeToMove(nextPosition, board)){
            if(positioning){
                movePiece(nextPosition, board);
            }
            

        }
    }
    
}

