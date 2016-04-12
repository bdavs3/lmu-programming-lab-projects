public abstract class Shape {
    abstract Point maxBound();
    abstract Point minBound();
    abstract boolean insideShape(Point p);
    abstract void incrementNumberOfDarts();
    abstract int getNumberOfDarts();
}
