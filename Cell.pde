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
