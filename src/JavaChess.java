import java.lang.*;
import java.util.Scanner;



public class JavaChess {
    public static void printTable(ChessPiece [][] boardReference){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(boardReference[i][j]!=null){
                    
                    System.out.print(((boardReference[i][j].toString()).toLowerCase()).charAt(0)+ "" +(boardReference[i][j].toString()).charAt(6) + " ");

                }else{
                    System.out.print('0'+ " ");

                }
                
            }
            System.err.println("");
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
        while(!playerInput.equals("0")){
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

    //Checks if the chosen move is possible by the piece
    public String possiblePosition(int moveX , int moveY){
        int xPosition = (int)currentPosition.charAt(0) - '0' + moveX;
        int yPosition = (int)currentPosition.charAt(1) - '0'+ moveY;
        return "" + xPosition + yPosition;
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

    //Checks if the square is free and if the position can be possible
    @Override
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        if(freeToMove(nextPosition, board)){
            if(possiblePosition(1*color,0).equals(nextPosition)){
                movePiece(nextPosition, board);
            }
            

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

    //Checks if the square is free and if the position can be possible
    @Override
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        
        int xCurrent = (int)currentPosition.charAt(0) - '0';
        int yCurrent = (int)currentPosition.charAt(1) - '0';
        int xPosition = (int)nextPosition.charAt(0) - '0';
        int yPosition = (int)nextPosition.charAt(1) - '0';
        boolean PossiblePositionUp =  possiblePosition(xCurrent-xPosition,0).equals(nextPosition);
        boolean PossiblePositionDown =  possiblePosition(xPosition-xCurrent,0).equals(nextPosition);
        boolean PossiblePositionLeft =  possiblePosition(0,yCurrent-yPosition).equals(nextPosition);
        boolean PossiblePositionRight =  possiblePosition(0,yPosition-yCurrent).equals(nextPosition);

        boolean positioning = (PossiblePositionUp||PossiblePositionDown||PossiblePositionLeft||PossiblePositionRight);
        if(freeToMove(nextPosition, board)){
            if(positioning){
                movePiece(nextPosition, board);
            }
            

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
    

    //Checks if the square is free and if the position can be possible
    
    @Override
    public void initiateMove(String nextPosition, ChessPiece [][] board ){
        int nextX = (int)nextPosition.charAt(0) - '0';
        int nextY = (int)nextPosition.charAt(1) - '0';
        
        boolean possibleMove = (Math.abs(nextX)-((int)currentPosition.charAt(0) - '0') == Math.abs(nextY)-((int)currentPosition.charAt(1) - '0'));



        if(freeToMove(nextPosition, board)){
            if(possibleMove){
                movePiece(nextPosition, board);
            }
            

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

        int nextX = (int)nextPosition.charAt(0) - '0';
        int nextY = (int)nextPosition.charAt(1) - '0';
        //Same boolean as the bishop
        boolean possibleMove = (Math.abs(nextX)-((int)currentPosition.charAt(0) - '0') == Math.abs(nextY)-((int)currentPosition.charAt(1) - '0'));
        int xCurrent = (int)currentPosition.charAt(0) - '0';
        int yCurrent = (int)currentPosition.charAt(1) - '0';
        //Same boolean as the rook
        boolean positioning = (possiblePosition(Math.abs(xCurrent-nextX),0).equals(nextPosition))||(possiblePosition(0,Math.abs(yCurrent-nextY)).equals(nextPosition));
        if(freeToMove(nextPosition, board)){
            if(possibleMove || positioning){
                movePiece(nextPosition, board);
            }
            

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
        if(freeToMove(nextPosition, board)){
            if(possiblePosition(1*color,0).equals(nextPosition)){
                movePiece(nextPosition, board);
            }
            

        }
    }
    
}

