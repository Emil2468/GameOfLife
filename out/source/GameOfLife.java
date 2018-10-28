import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GameOfLife extends PApplet {

Board board;
int sideLen = 10;
boolean updateBoard = false;

public void setup() {
    
    background(0);
    board = new Board(width, height, sideLen);
    board.DrawGrid();
    
}

public void draw() {
    board.RedrawBoard();
    if(updateBoard) {
        board.UpdateBoardState();
    }
}



public void mousePressed() {
    int x = (int)(mouseX / sideLen);
    int y = (int)(mouseY / sideLen);
    board.ChangeCellState(x, y);
}

public void keyPressed() {
    if(keyCode == ENTER) {
        updateBoard = !updateBoard;
        println("updateBoard: "+updateBoard);
        if(updateBoard) {
            frameRate(5);
        } else {
            frameRate(50);
        }
    } else if(keyCode == 67) {
        board.ClearAll();
    }
}
public class Board {

    private int sizeX, sizeY;
    private Cell[][] cells;
    int sideLen;

    public Board (int sizeX, int sizeY, int sideLen) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sideLen = sideLen;
        cells = new Cell[sizeX / sideLen][sizeY / sideLen];
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(new PVector(i, j), sideLen);
            }
        }
    }

    public void DrawGrid() {
        stroke(255);
        strokeWeight(1);
        for(int x = sideLen; x <= sizeX; x+= sideLen) {
            line(x, 0, x, sizeY);
        }
        for(int y = sideLen; y <= sizeY; y+= sideLen) {
            line(0,y, sizeX, y);
        }
    }

    public void ChangeCellState(int x, int y) {
        cells[x][y].ChangeState();
        
    }

    public void RedrawBoard() {
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j].DrawCell();
            }
        }
    }

    //Counts neighbours of all cells to determine the new state of the board
    public void UpdateBoardState() {
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                int neighbours = countNeighbourgs(i, j);
                if(neighbours < 2 && cells[i][j].IsAlive()) {
                    cells[i][j].ToChange = true;
                } else if(neighbours > 3 && cells[i][j].IsAlive()) {
                    cells[i][j].ToChange = true;
                } else if(neighbours == 3 && !cells[i][j].IsAlive()) {
                    cells[i][j].ToChange = true;
                }
            }
        }

        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                if(cells[i][j].ToChange) {
                    ChangeCellState(i,j);
                    cells[i][j].ToChange = false;
                }
            }
        }
    }
    
    private int countNeighbourgs(int x, int y) {
        int neighbours = 0;
        if(x - 1 >= 0) {
            if(cells[x - 1][y].IsAlive()) {
                neighbours++;
            }
            if(y - 1 >= 0) {
                if(cells[x - 1][y - 1].IsAlive()) {
                    neighbours++;
                }
            }
            if(y + 1 < cells[y].length) {
                if(cells[x - 1][y + 1].IsAlive()){
                    neighbours++;
                }
            }
        }
        if(x + 1 < cells.length) {
            if(cells[x + 1][y].IsAlive()) {
                neighbours++;
            }
            if(y - 1 >= 0) {
                if(cells[x + 1][y - 1].IsAlive()) {
                    neighbours++;
                }
            }
            if(y + 1 < cells[y].length) {
                if(cells[x + 1][y + 1].IsAlive()){
                    neighbours++;
                }
            }
        }
        if(y - 1 >= 0) {
            if(cells[x][y - 1].IsAlive()) {
                neighbours++;
            }
        }
        if(y + 1 < cells.length) {
            if(cells[x][y + 1].IsAlive()) {
                neighbours++;
            }
        }
        return neighbours;
    }

    public void ClearAll() {
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                if(cells[i][j].IsAlive()) {
                    ChangeCellState(i,j);
                }
            }
        }
    }
}
public class Cell {

    private boolean isAlive = false;
    //Set to true while looping over cells in order to change when done
    public boolean ToChange = false;
    //Pos should contain the coordiates of the box the cell lives in
    //not coordiates in pixels
    private PVector pos;
    private int sideLen;

    public Cell (PVector pos, int sideLen) {
        this.pos = pos;
        this.sideLen = sideLen;
    }

    public boolean IsAlive() {
        return isAlive;
    }

    //Changes the cell from dead to alive or alive to dead
    public void ChangeState() {
        isAlive = !isAlive;
    }

    public void DrawCell() {
        if(isAlive) {
            stroke(241, 241, 0);
            fill(241, 241, 0);
        } else {
            stroke(0);
            fill(0);
        }
        //Subtract and add ones and twos in order to not draw over the grid
        rect(pos.x * sideLen + 1, pos.y * sideLen + 1, sideLen - 2, sideLen - 2);
    }
}
  public void settings() {  size(900,900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GameOfLife" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
