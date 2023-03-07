public class UtilPos {
    private Position position;
    private Integer utility;

    public UtilPos(Integer u, Position p){
    this.position = p;
    this.utility = u;
    }

    public Integer getUtil(){
        return this.utility;
    }

    public Position getPos(){
        return this.position;
    }

    public void setUtil(int i){
        this.utility = i;
    }

    public void setPos(Position p){
        this.position = p;
    }
}
