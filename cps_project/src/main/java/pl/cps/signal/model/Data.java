package pl.cps.signal.model;

//class representing points in chart
public class Data implements Comparable<Data> {
    private Double x;
    private Double y;

    public Data(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public int compareTo(Data o) {
        return Double.compare(this.getX(),o.getX());
    }

    @Override
    public String toString() {
        return "Data{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double addY(Data d1, Data d2){
        if(d1.getX()==d2.getX()){
            return d1.getY()+d2.getY();
        }
        return 0;
    }
    public double substractY(Data d1, Data d2){
        if(d1.getX()==d2.getX()){
            return d1.getY()-d2.getY();
        }
        return 0;
    }
    public double multiplyY(Data d1, Data d2){
        if(d1.getX()==d2.getX()){
            return d1.getY()*d2.getY();
        }
        return 0;
    }
    public double divideY(Data d1, Data d2){
        if(d1.getX()==d2.getX()){
            return d1.getY()/d2.getY();
        }
        return 0;
    }
}
