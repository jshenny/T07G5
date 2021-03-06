import java.util.Arrays;
import java.util.ArrayList;

public class Board extends Shape{
  private int[][] board;
  private int maxX;
  private int maxY;
  private String direction = "";
  private int score;

  public Board() {
    // default contructor that creates empty board
    board = new int[16][10];

  }
  /**
  * Obtains the board of the Board object
  * @return the board
  */
  public int[][] getBoard(){
    return board;
  }

  /**
  * Places a certain shape on the board
  * @param shape This is the shape to be placed on the board
  */
  public void placeShape(Shape shape){
      // to find the "highest block" of the shape and put it on the very top of the board
      maxY = shape.getY(0);
      for (int i = 0; i<4; i++){
        if (maxY < shape.getY(i)){
          maxY = shape.getY(i);
        }
      }
      // to find the block(s) to the very right of the shape and centre the placement from there
      maxX = shape.getX(0);
      for (int j = 0; j < 4; j++){
        if (maxX < shape.getX(j)){
          maxX = shape.getX(j);
        }
      }
      if (shape.getShape() != ShapeType.NoShape){
        // this if statment ensures that the shape being placed is not actually a "no shape"
        for (int k = 0; k < 4; k++){
          int x = shape.getX(k);
          int y = shape.getY(k);
          board[maxY-y][5+x-maxX] = 1;
          }
        }
      }

  /**
  * Checks if a row has been completely filled
  * @return true if one row is completely occupied, while also clearing it at the same time
  */
  public boolean checkFullRow(){
    boolean cleared = false;
    for (int i = 0; i < 16; i++){
      int increment = 0;
      for (int j = 0; j < 10; j++){
        if (board[i][j] != 0){
          increment += 1;
      }
    }
      if (increment == 10){
        // ensures that the whole row(which has 10 columns) is occupied
        for (int col = 0 ; col < 10; col++){
          board[i][col] = 0;
        }
        for (int clearedRow = i; clearedRow > 0; clearedRow--){
          for (int col = 0; col < 10; col++){
            board[clearedRow][col] = board[clearedRow-1][col];
            board[clearedRow-1][col] = 0;
          }
        }
        updateScore();
        cleared = true;
      }
    }
    return cleared;
  }

  //Prints the board with the current shapes that have been placed
  public void print2D(){
			for (int row = 0; row < 16; row++){
					for (int col = 0; col < 10; col++){
							System.out.print(" " + board[row][col]);
          }
					System.out.println();
      }
      printScore();
	}
  /**
  * Clears the board by filling it with zeroes
  */
  public void clearBoard() {
    for (int i = 0; i < 16; i++){
				for (int j = 0; j < 10; j++) {
            board[i][j] = 0;
          }
    }
  }
  /**
  * Moves the shape one space to the left
  *@param shape is the random shape that is placed on the board
  */
  public void moveLeft(Shape shape){
    if (shape.getShape() != ShapeType.NoShape){
      for (int k = 0; k < 4; k++){
        int x = shape.getX(k);
        int y = shape.getY(k);
        board[maxY-y][5+x-maxX] = 0;
        // The y is the actual coordinate of the Y-coord on the board,
        // and the maxY is the relative coordinate of a part of the shape
        // The 5+x is the actual coordinate of the X-coord on the board,
        // and the maxX is the relative coordinate of a part of the shape
        }
      }
    // sets the new x-coords after moving left
    for (int x = 0; x < 4; x++)
      shape.setNewX(x, shape.getX(x)-1);

    //applies the shape's new coordinates to the board
    if (shape.getShape() != ShapeType.NoShape){
      for (int k = 0; k < 4; k++){
        int x = shape.getX(k);
        int y = shape.getY(k);
        board[maxY-y][5+x-maxX] = 1;
        }
      }

  }
  /**
  * Moves the shape one space to the right
  * @param shape is the current random shape being moved on the board
  */
  public void moveRight(Shape shape){
    if (shape.getShape() != ShapeType.NoShape){
      for (int k = 0; k < 4; k++){
        int x = shape.getX(k);
        int y = shape.getY(k);
        board[maxY-y][5+x-maxX] = 0;
        }
      }
      // changes x-coords to the new ones after moving right
      for (int x = 0; x < 4; x++)
        shape.setNewX(x, shape.getX(x)+1);

      if (shape.getShape() != ShapeType.NoShape){
        for (int k = 0; k < 4; k++){
          int x = shape.getX(k);
          int y = shape.getY(k);
          board[maxY-y][5+x-maxX] = 1;
          }
        }

  }
  /**
   *Moves the shape one space down
   *@param shape is the current random shape being moved on the board
   */
  public void moveDown(Shape shape){
    if (shape.getShape() != ShapeType.NoShape){
      for (int k = 0; k < 4; k++){
        int x = shape.getX(k);
        int y = shape.getY(k);
        board[maxY-y][5+x-maxX] = 0;
        }
      }
    //sets the new y-coordinates after moving one down
    for (int y = 0; y < 4; y++){
    shape.setNewY(y, shape.getY(y)-1);
  }
    if (shape.getShape() != ShapeType.NoShape){
      for (int k = 0; k < 4; k++){
        int x = shape.getX(k);
        int y = shape.getY(k);
        board[maxY-y][5+x-maxX] = 1;
      }
    }
  }

/**
* Checks if moving the shape left will collide with any other shapes or the edge of the board
* @param shape is the current random shape being moved on the board
* @return true if the collision will happen
*/
  public boolean leftCollision(Shape shape){
    for (int k = 0; k < 4; k++){
      int x = shape.getX(k);
      if ((5+x-maxX-1) < 0){
        // The 5+x is the coordinate of the right-most X-coord of the shape
        // being moved on the board and the maxX is the relative coordinate of a part of the shape
        // 5-maxX+x gives us the current point, and subutracting one gives us the point to the left
        // we can check if it's the edge by checking if this point to the left is negative
        return true;
      }
    }
    // this ArrayList stores the index in which gives us the x-coord that is to the very left
    // of the shape. We do this because the shape can have multiple left-most parts, hence
    // we check each of these to see if they collide with any shapes to the left
    ArrayList<Integer> leftMostIndex = new ArrayList<Integer>();
    int xvalue = shape.getX(0);
    leftMostIndex.add(0);
    for (int index = 1; index < 4; index++){
      if (xvalue > shape.getX(index)){
        // if statement to make sure that we have the left-most x-coord
        xvalue = shape.getX(index);
        leftMostIndex.clear();
        // we clear the arraylist if there is an index with a "left-er" x-coord, and store this new index
        leftMostIndex.add(index);
      }
      else if (xvalue == shape.getX(index)){
        leftMostIndex.add(index);
        // if the x-coord is the same for two indexes, we have to check both of these
        // so we just add this new index, rather than ignoring it or replacing the previous
      }
    }

    // for-loop checks every left-most x-coord if the spot to the left of the shape is clear
    for (int indexed : leftMostIndex){
      int yCoord = shape.getY(indexed);
      int xCoord = shape.getX(indexed);
      if (board[maxY-yCoord][5+xCoord-maxX-1] == 2){
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the current shape will collide with another shape on its right or the right edge of the board
   * @param shape is the current random shape being moved on the board
   * @return true if the collision will happen
   */
  public boolean rightCollision(Shape shape){
    for (int k = 0; k < 4; k++){
      int x = shape.getX(k);
      if ((5+x-maxX+1) > 9){
        // similar to leftCollision, except adding 1 to check to the right. At the edge if greater than 9
        return true;
      }
    }
    // same as leftCollision, except storing indexes that give the right-most x-coord when using getX() method
    ArrayList<Integer> rightMostIndex = new ArrayList<Integer>();
    int xvalue = shape.getX(0);
    rightMostIndex.add(0);
    for (int index = 1; index < 4; index++){
      if (xvalue < shape.getX(index)){
        xvalue = shape.getX(index);
        rightMostIndex.clear();
        rightMostIndex.add(index);
      }
      else if (xvalue == shape.getX(index)){
        rightMostIndex.add(index);
      }
    }
    // same as leftCollision, except checking if the right-most x-coords will collide against something to the right
    for (int indexed : rightMostIndex){
      int yCoord = shape.getY(indexed);
      int xCoord = shape.getX(indexed);
      // we grab the relative coordinates of both x and y of a point of a shape
      if (board[maxY-yCoord][5+xCoord-maxX+1] == 2){
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the current shape will collide with another shape below it or the bottom edge of the board
   * It is also used for endgame; if a new shape respawns and encounters a bottom collision right away,
   * the game ends
   * @param shape is the current random shape being moved on the board
   * @return true if the collision will happen
   */
  public boolean bottomCollision(Shape shape){
    for (int k = 0; k < 4; k++){
      int y = shape.getY(k);
      if ((maxY-y+1) > 15){
        // The y is the highest Y-coord of the shape on the board,
        // and the maxY is the relative coordinate of a part of the shape
        // adding 1 will check one row below it. It's at the edge if one
        // of the points goes over the index 15
        return true;
      }
    }

    for (int k = 0; k < 4; k++){
      int yCoord = shape.getY(k);
      int xCoord = shape.getX(k);
      if (board[maxY-yCoord+1][5+xCoord-maxX] == 2)
        return true;
    }

  return false;
}
  public void rotateLeft(Shape shape, Shape originalShape) {
	    boolean rotatable = true;
	    ArrayList<Integer> originalXValues = new ArrayList<Integer>();
	    int centerX;
	    int centerY;
	    if (shape.getShape() != ShapeType.TShape) {
		    centerX = shape.getX(2);
		    centerY = shape.getY(2);
	    } else {
	    	centerX = shape.getX(1);
	    	centerY = shape.getY(1);
	    }
	    if (shape.getShape() != ShapeType.NoShape && shape.getShape() != ShapeType.SquareShape){
	      for (int k = 0; k < 4; k++){
	        int x = shape.getX(k);
	        int y = shape.getY(k);
	        board[maxY-y][5+x-maxX] = 0;
	        }
	      }
	    for (int x = 0; x < 4; x++) {
	      originalXValues.add(originalShape.getX(x));
	      originalShape.setNewX(x, -originalShape.getY(x));
	    }

	    for (int y = 0; y < 4; y++){
	      originalShape.setNewY(y, originalXValues.get(y));
	    }

	    //check if there is a block in the way of rotating
	    if (shape.getShape() != ShapeType.NoShape && shape.getShape() != ShapeType.SquareShape){
	      for (int k = 0; k < 4; k++){
	        int x = centerX + originalShape.getX(k);
	        int y = centerY + originalShape.getY(k);
	        if ((5+x-maxX) < 0 || (5+x-maxX) > 9 || (maxY-y) > 15 || (maxY - y) < 0) {
	          rotatable = false;
	        } else if ((board[maxY-y][5+x-maxX] == 2)) {
	          rotatable = false;
	        }
	      }
	    }
	    if (shape.getShape() != ShapeType.NoShape && shape.getShape() != ShapeType.SquareShape && rotatable == true){
	        for (int k = 0; k < 4; k++){
	          int x = centerX + originalShape.getX(k);
	          int y = centerY + originalShape.getY(k);
	          shape.setNewX(k, x);
	          shape.setNewY(k, y);
	          board[maxY-y][5+x-maxX] = 1;
	        }
	      }
	    //undos the rotate
	    else if (rotatable == false) {
	      for (int k = 0; k < 4; k++){
	        int x = shape.getX(k);
	        int y = shape.getY(k);
	        board[maxY-y][5+x-maxX] = 1;
	      }
	    }
	  }
	  public void rotateRight(Shape shape, Shape originalShape) {
	    boolean rotatable = true;
	    ArrayList<Integer> originalXValues = new ArrayList<Integer>();
	    int centerX;
	    int centerY;
	    if (shape.getShape() != ShapeType.TShape) {
		    centerX = shape.getX(2);
		    centerY = shape.getY(2);
	    } else {
	    	centerX = shape.getX(1);
	    	centerY = shape.getY(1);
	    }
	    if (shape.getShape() != ShapeType.NoShape && shape.getShape() != ShapeType.SquareShape){
	      for (int k = 0; k < 4; k++){
	        int x = shape.getX(k);
	        int y = shape.getY(k);
	        board[maxY-y][5+x-maxX] = 0;
	        }
	      }
	    for (int x = 0; x < 4; x++) {
	      originalXValues.add(originalShape.getX(x));
	      originalShape.setNewX(x, originalShape.getY(x));
	    }

	    for (int y = 0; y < 4; y++){
	      originalShape.setNewY(y, -originalXValues.get(y));
	    }

	    //check if there is a block in the way of rotating
	    if (shape.getShape() != ShapeType.NoShape && shape.getShape() != ShapeType.SquareShape){
	      for (int k = 0; k < 4; k++){
	        int x = centerX + originalShape.getX(k);
	        int y = centerY + originalShape.getY(k);
	        if ((5+x-maxX) < 0 || (5+x-maxX) > 9 || (maxY-y) > 15 || (maxY - y) < 0) {
	          rotatable = false;
	        } else if ((board[1-y][5+x-maxX] == 2)) {
	          rotatable = false;
	        }
	      }
	    }
	    if (shape.getShape() != ShapeType.NoShape && shape.getShape() != ShapeType.SquareShape && rotatable == true){
	        for (int k = 0; k < 4; k++){
	          int x = centerX + originalShape.getX(k);
	          int y = centerY + originalShape.getY(k);
	          shape.setNewX(k, x);
	          shape.setNewY(k, y);
	          board[maxY-y][5+x-maxX] = 1;
	        }
	      }
	    //undos the rotate
	    else if (rotatable == false) {
	      for (int k = 0; k < 4; k++){
	        int x = shape.getX(k);
	        int y = shape.getY(k);
	        board[maxY-y][5+x-maxX] = 1;
	      }
	    }
	}
    /*
    * this sets the board
    * this method goes through all the  index in the board to check for 1's, it changes 1's to two for collision purposes
    */
	  public void setBoard() {
		  for (int row = 0; row < 16; row++){
				for (int col = 0; col < 10; col++){
					if (board[row][col] == 1) {
						board[row][col] = 2;
					}
				}
		  }
    }
    /*
    * Updates the score by adding 40 pts
    */
    public void updateScore() {
      score += 40;
    }
    /*
    * Prints the score out in the format Score: <score> 
    */
    public void printScore() {
      System.out.println("Score: " + score);
    }
}
