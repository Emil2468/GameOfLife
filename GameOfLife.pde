Board board;
int sideLen = 10;
boolean updateBoard = false;

void setup() {
    size(900,900);
    background(0);
    board = new Board(width, height, sideLen);
    board.DrawGrid();
    
}

void draw() {
    board.RedrawBoard();
    if(updateBoard) {
        board.UpdateBoardState();
    }
}



void mousePressed() {
    int x = (int)(mouseX / sideLen);
    int y = (int)(mouseY / sideLen);
    board.ChangeCellState(x, y);
}

void keyPressed() {
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