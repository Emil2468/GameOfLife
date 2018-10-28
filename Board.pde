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
