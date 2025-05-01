import java.lang.*;
import java.util.Scanner;



public class JavaChess {
    public static void printTable(ChessPiece [][] boardReference){
        System.out.print("   ");
        for(int i =0;i<8;i++){
            System.out.printf("%-4d",i);

        }
        System.out.println("");
        for(int i=0;i<8;i++){
            System.out.printf("%-3d", i);
            for(int j=0;j<8;j++){
                
                if(boardReference[i][j]!=null){
                    
                    System.out.printf("%-4s",((boardReference[i][j].toString()).toLowerCase()).charAt(0)+ "-" +(boardReference[i][j].toString()).charAt(6) + " ");

                }else{
                    System.out.printf("%-4s","0");

                }
                
            }
            System.out.println("");
        }

        

    }
    

    public static void main(String[] args){

        //Board setup
        ChessPiece [][] chessBoard = new ChessPiece[8][8];
        //Rooks
        for(int i=0;i<8;i=i+7){
            chessBoard[0][i] = new Rook(1, "0" + i);
            chessBoard[7][i] = new Rook(-1, "7" + i);
        }
        //Horses
        for(int i=1;i<7;i=i+5){
            chessBoard[0][i] = new Horse(1, "0" + i);
            chessBoard[7][i] = new Horse(-1, "7" + i);
        }
        //Bishops
        for(int i=2;i<7;i=i+3){
            chessBoard[0][i] = new Bishop(1, "0" + i);
            chessBoard[7][i] = new Bishop(-1, "7" + i);
        }
        //Queens
        chessBoard[0][3] = new Queen(1, "03");
        chessBoard[7][3] = new Queen(-1, "73");
        //Kings
        chessBoard[0][4] = new King(1, "04");
        chessBoard[7][4] = new King(-1, "74");
        //Pawns
        for(int i=0;i<8;i++){
            chessBoard[1][i] = new Pawn(1, "1" + i);
            chessBoard[6][i] = new Pawn(-1, "6" + i);
        }
        

        
        
        

        //Actual game run
        String notation = "";//Keep notes
        Scanner input = new Scanner(System.in);
        int playerColor = -1;
        int pieceX;
        int pieceY;
        printTable(chessBoard);
        System.out.println("First Move:");
        String playerInput = input.nextLine();
        //Checking if the entered string has the correct pattern
        String pattern = "\\d{4}";
        //Input should be of type CCNN where CC is the current position of the piece to be moved and NN the next position
        while(!playerInput.equals("0") && !playerInput.equals("00")){
            if(playerInput.matches(pattern)){
                pieceX = playerInput.charAt(0) - '0';
                pieceY = playerInput.charAt(1) - '0';
                if(chessBoard[pieceX][pieceY] == null){
                    System.out.println("The space is empty! Please enter another move:");
                }else if (chessBoard[pieceX][pieceY].color != playerColor) {
                    System.out.println("Player can't move piece of opposite color! Please enter the move again:");
                }else{
                    notation += pieceX + "" + pieceY + chessBoard[pieceX][pieceY].getPieceClass() + playerInput.substring(2,4) + "\n";
                    if((chessBoard[pieceX][pieceY].pieceClass).equals("King")){
                        String winningSentence = (playerColor==-1)?"White " + " has won!":"Black " + " has won!";
                        System.out.println(winningSentence);
                        break;
                    }
                    chessBoard[pieceX][pieceY].initiateMove(playerInput.substring(2,4),chessBoard);
                    System.out.println("Next move:");
                    playerColor = playerColor*(-1);
                }

            }else{
                System.out.println("The input is not of the correct pattern. Please enter the move again:");
                

            }
            printTable(chessBoard);
            playerInput = input.nextLine();



        }
        if(playerInput.equals("00")){
            String surrenderSentence = (playerColor==-1)?"White " + " has surrendered. Black":"Black " + " has surrendered! White";
            System.out.println(surrenderSentence+" has won!");

        }
        System.out.println(notation);
        input.close();





        
        
        
        
    
        
        
    
        
        
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

    //Finalizes the move of the piece by changing the actual position in the array
    public void movePiece(String nextPosition , ChessPiece [][] board){

        int currentX = currentPosition.charAt(0) - '0';
        int currentY = currentPosition.charAt(1) - '0';
        int nextX = nextPosition.charAt(0) - '0';
        int nextY = nextPosition.charAt(1) - '0';
        
        board [currentX][currentY] = null;
        board [nextX][nextY] = this;
        currentPosition = nextPosition;
        
    }

    //Checks if straight path is open
    public boolean clearPath(String nextPosition, ChessPiece[][] board){
        int currentX = (int)currentPosition.charAt(0) - '0';
        int currentY = (int)currentPosition.charAt(1) - '0';
        int nextX = (int)nextPosition.charAt(0) - '0';
        int nextY = (int)nextPosition.charAt(1) - '0';
        //Horizontal
        if (currentX == nextX) { 
            for (int y = Math.min(currentY, nextY) + 1; y < Math.max(currentY, nextY); y++) {
                if (board[currentX][y] != null) return false;
            }
        }//Vertical
        else if (currentY == nextY) {
            for (int x = Math.min(currentX, nextX) + 1; x < Math.max(currentX, nextX); x++) {
                if (board[x][currentY] != null) return false;
            }
        }
        return true;


    }

    //Checks if diagonal path is open
    public boolean clearDiagonalPath(String nextPosition, ChessPiece[][] board){
        int currentX = (int)currentPosition.charAt(0) - '0';
        int currentY = (int)currentPosition.charAt(1) - '0';
        int nextX = (int)nextPosition.charAt(0) - '0';
        int nextY = (int)nextPosition.charAt(1) - '0';
        //Up or Down
        int directionX = nextX > currentX ? 1 : -1;
        //Right or Left
        int directionY = nextY > currentY ? 1 : -1;
        int x = currentX + directionX;
        int y = currentY + directionY;

        while (x != nextX && y != nextY) {
            if (board[x][y] != null) return false;
            x += directionX;
            y += directionY;
        }
        return true;


    }

    public void initiateMove(String nextPosition, ChessPiece [][] board){

        /*int currentX = currentPosition.charAt(0) - '0';
        int currentY = currentPosition.charAt(1) - '0';
        int nextX = nextPosition.charAt(0) - '0';
        int nextY = nextPosition.charAt(1) - '0';
        
        board [currentX][currentY] = null;
        board [nextX][nextY] = this;
        currentPosition = nextPosition;
        */
    }

    //Returns class
    public char getPieceClass(){
        return pieceClass.charAt(0);
    }

    //Checks if two pieces are of the same class
    public boolean sameClass(ChessPiece piece){

        return (piece.getPieceClass()==getPieceClass());
        
    }

    @Override
    public String toString(){
        return (color==-1)?"White " + pieceClass + " at " + currentPosition:"Black " + pieceClass + " at " + currentPosition;
    }

    


    

}

class Pawn extends ChessPiece{
    //Constructor of pawn, sets color and the starting position
    Pawn(int color,String currentPosition){
        this.color = color;
        this.currentPosition = currentPosition;
        this.pieceClass = "Pawn";
    }

    //Checks if the square is free and if the position can be done by the Pawn. Also the capture if possible
    @Override
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        int currentX = (int)currentPosition.charAt(0) - '0';
        int currentY = (int)currentPosition.charAt(1) - '0';
        int nextX = (int)nextPosition.charAt(0) - '0';
        int nextY = (int)nextPosition.charAt(1) - '0';

        //Pawn move
        if(freeToMove(nextPosition, board) && nextX==currentX+color && nextY==currentY){
                movePiece(nextPosition, board);
        }
        //Diagonal capture
        else if (Math.abs(nextY - currentY) == 1 && nextX == currentX + color && board[nextX][nextY] != null &&
             board[nextX][nextY].returnColor() != color) {
        movePiece(nextPosition, board);
        }

    }
    
}

class Rook extends ChessPiece{
    //Constructor of rook, sets color and the starting position
    Rook(int color,String currentPosition){
        this.color = color;
        this.currentPosition = currentPosition;
        this.pieceClass = "Rook";
    }
    //Initiates the move if the square is free and path is clear
    @Override
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        
        if (freeToMove(nextPosition, board) && clearPath(nextPosition, board)) {
            movePiece(nextPosition, board);
        }
    }
    
}

class Horse extends ChessPiece{
    //Constructor of horse, sets color and the starting position
    Horse(int color,String currentPosition){
        this.color = color;
        this.currentPosition = currentPosition;
        this.pieceClass = "Horse";
    }
    //Checks if the chosen move is possible by the Horse
    public String possiblePosition(int moveX , int moveY){
        int xPosition = (int)currentPosition.charAt(0) - '0' + moveX;
        int yPosition = (int)currentPosition.charAt(1) - '0'+ moveY;
        return "" + xPosition + yPosition;
    }

    //Checks if the square is free and if the position can be possible
    @Override
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        
        boolean PossiblePositionUp = possiblePosition(1,2).equals(nextPosition) || possiblePosition(-1,2).equals(nextPosition);
        boolean PossiblePositionDown = possiblePosition(1,-2).equals(nextPosition) || possiblePosition(-1,-2).equals(nextPosition);
        boolean PossiblePositionRight = possiblePosition(2,1).equals(nextPosition) || possiblePosition(2,-1).equals(nextPosition);
        boolean PossiblePositionLeft = possiblePosition(-2,1).equals(nextPosition) || possiblePosition(-2,-1).equals(nextPosition);


        boolean positioning = PossiblePositionDown || PossiblePositionUp || PossiblePositionRight || PossiblePositionLeft;
        if(freeToMove(nextPosition, board)){
            if(positioning){
                movePiece(nextPosition, board);
            }
            

        }
    }
    
}

class Bishop extends ChessPiece{
    //Constructor of bishop, sets color and the starting position
    Bishop(int color,String currentPosition){
        this.color = color;
        this.currentPosition = currentPosition;
        this.pieceClass = "Bishop";
    }
    
    //Initiates the move if the square is free and path is clear
    @Override
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        int currentX = currentPosition.charAt(0) - '0';
        int currentY = currentPosition.charAt(1) - '0';
        int nextX = nextPosition.charAt(0) - '0';
        int nextY = nextPosition.charAt(1) - '0';

        if (Math.abs(nextX - currentX) == Math.abs(nextY - currentY) && freeToMove(nextPosition, board) &&
        clearDiagonalPath(nextPosition, board)) {
            movePiece(nextPosition, board);
     }
    }
    
}

class Queen extends ChessPiece{
    //Constructor of pawn, sets color and the starting position
    Queen(int color,String currentPosition){
        this.color = color;
        this.currentPosition = currentPosition;
        this.pieceClass = "Queen";
    }

    //Checks if the square is free and if the position can be possible
    @Override
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        int currentX = currentPosition.charAt(0) - '0';
        int currentY = currentPosition.charAt(1) - '0';
        int nextX = nextPosition.charAt(0) - '0';
        int nextY = nextPosition.charAt(1) - '0';

        if (freeToMove(nextPosition, board) &&
            ((currentX == nextX || currentY == nextY) && clearPath(nextPosition, board)) ||
            (Math.abs(nextX - currentX) == Math.abs(nextY - currentY) && clearDiagonalPath(nextPosition, board))) {
            movePiece(nextPosition, board);
        }
        
    }
    
}
class King extends ChessPiece{
    //Constructor of pawn, sets color and the starting position
    King(int color,String currentPosition){
        this.color = color;
        this.currentPosition = currentPosition;
        this.pieceClass = "King";
    }

    //Checks if the square is free and if the position can be possible
    @Override
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        int currentX = currentPosition.charAt(0) - '0';
        int currentY = currentPosition.charAt(1) - '0';
        int nextX = nextPosition.charAt(0) - '0';
        int nextY = nextPosition.charAt(1) - '0';

        /* 
        boolean positionLeftRight = ((currentX==nextX) && (currentY==nextY+1))||((currentX==nextX) && (currentY==nextY-1));
        boolean positionUpDown = ((currentX==nextX+1) && (currentY==nextY))||((currentX==nextX-1) && (currentY==nextY));
        boolean positionRight = ((currentX==nextX+1) && (currentY==nextY+1))||((currentX==nextX-1) && (currentY==nextY+1));
        boolean positionLeft = ((currentX==nextX+1) && (currentY==nextY-1))||((currentX==nextX-1) && (currentY==nextY-1));
        boolean positionPossible = positionLeftRight || positionUpDown || positionRight || positionLeft;
        */
        //Checks if the next move is within a square of current position
        int differenceX = Math.abs(nextX - currentX);
        int differenceY = Math.abs(nextY - currentY);

        boolean positionPossible = (differenceX <= 1 && differenceY <= 1);

        if (freeToMove(nextPosition, board) && positionPossible) {
            movePiece(nextPosition, board);
        }

    }
    
}

