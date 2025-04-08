//Author: Mara Johnson
//Piece name: Pawn
//This is a normal pawn so it has the ability to move forward one space on each turn(on its first turn it has the choice to move one or two spaces). 
//The pawn captures diagonally.

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Pawn extends Piece {

    public Pawn(boolean color, String img_file){
      super(color, img_file);
    }

    public String toString(){
      if(color == true){
        return "A white Pawn.";
      } else{
        return "A black Pawn.";
      }
    }

    //pre-condition: board is a valid 8 x 8 array of squares, and start is a valid square with row and column assignments both between 0 and 8(not including 8)
    //post-condition: returns a list of every square that is "controlled" by this piece. 
    //Pawns control squares diagonally in front of them(in the direction they are going)
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
    //creates an ArrayList of Squares that will be returned later.
      ArrayList <Square> controlled = new ArrayList<Square>();
     //sets up variables so the code is easiet to read, holds starting values
     Piece current = start.getOccupyingPiece();
     int currentRow = start.getRow();
     int currentCol = start.getCol();

     //for white pieces
     if(current.getColor()){
      //if the pawn is at the end of the board they do not control any squares
      if(currentRow == 0){
        return controlled;
      }
      //if there is a square in front of and to the right of the pawn it will add this square to the ArrayList controlled
      if(currentCol > 0 && currentCol < 8){
      controlled.add(board[currentRow - 1][currentCol - 1]);
      }
      //if there is a square in front of and to the left of the pawn's current location it will add this quare to controlled
      if(currentCol >= 0 && currentCol < 7){
      controlled.add(board[currentRow - 1][currentCol + 1]);
      }
      
     } else{
      //for black pawns
      //if the pawn is at the end of the board they do not control any squares.
      if(currentRow == 7){
        return controlled;
      }
      //Looking at the board, if there is a square one row below and one column right it will add this square to controlled.
      if(currentCol >= 0 && currentCol < 7){
      controlled.add(board[currentRow + 1][currentCol + 1]);
      }
      //Looking at the board, if there is a square one row below and one column left it will add this square to controlled.
      if(currentCol > 0 && currentCol < 8){
      controlled.add(board[currentRow + 1][currentCol - 1]);
      }
     }
     //returns the final ArrayList consisting of all the squares a selected pawn controls
      return controlled;
    }
    

    //pre-condition: board is a valid 8 x 8 array of squares, and start is a valid square with row and column assignments both between 0 and 8(not including 8)
    //post-condition: returns a list of all allowed moves for a pawn, including normal movement and possible captures
    //For a white pawn: if there are spaces in bounds to move to, a white pawn will move 'up' a row one square at a time,
      //with the option to move one or two squares on their first turn. They can capture diagonally.
    //For a black pawn: if there are spaces in bounds to move to, a black pawn will move 'down' on the screen/down a row one square at a time,
      //with the option to move one or two squares on their first turn. They can capture diagonally.
    public ArrayList<Square> getLegalMoves(Board b, Square start){
      //creates the ArrayList that will be returned at the end.
      ArrayList<Square> moves = new ArrayList<Square>();
      //sets up variables to make it easier to read
      Piece current = start.getOccupyingPiece(); 
      int currentRow = start.getRow();
      int currentCol = start.getCol();
      //this ArrayList contains all of the squares controlled by a selected pawn
      ArrayList <Square> controlled = getControlledSquares(b.getSquareArray(), start);
      
      //if the pawn is white and it is white turn
        if(current.getColor() && b.getTurn()){
          //if it will end up out of bounds there are no legal moves
          if(currentRow == 0){
            return moves;
           }
           //if there is a square in front of the piece and it is not occupied, it is a legal move and added to the moves ArrayList
           if(currentRow - 1 >= 0 && !b.getSquareArray()[currentRow - 1][currentCol].isOccupied()){
            moves.add(b.getSquareArray()[currentRow - 1][currentCol]);
           }
          //if it is the pawn's first turn(judged via typical location on the board) and the square two above the pawn isn't occupied
          //if will add this square to the moves ArrayList
          if(currentRow == 6 && !b.getSquareArray()[currentRow - 2][currentCol].isOccupied()){
            moves.add(b.getSquareArray()[currentRow - 2][currentCol]);
          }
          //if there are controlled squares that are occupied by pieces of the opposite color it will add that square to the moves ArrayList
          for(Square s: controlled){
            if(s.isOccupied() && !s.getOccupyingPiece().getColor()){
              moves.add(s);
            }
          }
          
          //if it is black and it is black's turn
        } else if(!current.getColor() && !b.getTurn()){
          //if it will end up out of bounds there are no legal moves
          if(currentRow == 7){
          return moves;
        }
        //if there is a square in front of the piece(below) and it is not occupied, it is a legal move and added to the moves ArrayList
        if(currentRow + 1 < 8 && !b.getSquareArray()[currentRow + 1][currentCol].isOccupied()){
        moves.add(b.getSquareArray()[currentRow + 1][currentCol]);
        }
        //if it is the pawn's first turn(judged via typical location on the board) and the square two below the pawn isn't occupied
          //if will add this square to the moves ArrayList
        if(currentRow == 1 && !b.getSquareArray()[currentRow + 2][currentCol].isOccupied()){
          moves.add(b.getSquareArray()[currentRow + 2][currentCol]);
        } 
        //if there are controlled squares that are occupied by pieces of the opposite color it will add that square to the moves ArrayList 
        for(Square s : controlled){
          if(s.isOccupied() && s.getOccupyingPiece().getColor()){
            moves.add(s);
          }
        }
        
        }
        //returns the moves ArrayList
        return moves;
    }
        
  }