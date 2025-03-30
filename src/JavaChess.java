




public class JavaChess {

    public static void main(String[] args){
        System.out.println("Wassup");
    }


    

}

class ChessPiece{
    protected String pieceClass;
    protected String color;
    protected String currentPosition;


    public boolean freeToMove(String nextPosition, ChessPiece[][] board){
        boolean canMove = true;
        int xPosition = (int) nextPosition.charAt(0);
        int yPosition = (int) nextPosition.charAt(1);
        if (board[xPosition][yPosition]!=null){
           canMove = !(board[xPosition][yPosition].returnColor().equals(this.color));
        }   
     return canMove;
    }

    public String returnColor(){
        return color;
    }

    public String positionTagTransformation(String entry, int moveX , int moveY){
        int xPosition = (int)entry.charAt(0) + moveX;
        int yPosition = (int)entry.charAt(1) + moveY;
        String transformedPosition = (char)(xPosition+'0') +"free";
        return transformedPosition;
    }
    
    

}

class Pawn extends ChessPiece{
    
    Pawn(String color,String currentPosition){
        this.color = color;
        this.currentPosition = currentPosition;
        pieceClass = "Pawn";
    }

    public void initiateMove(String nextPosition, ChessPiece[][] board ){
        if(freeToMove(nextPosition, board) && (nextPosition.equals(currentPosition.charAt(0))){
            movePawn(nextPosition);

        }
    }

    public void movePawn(String nextPosition){
        currentPosition = nextPosition;
        
    }
}

