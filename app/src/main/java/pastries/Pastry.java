package pastries;

abstract class Pastry {
    abstract void accept(PastryVisitor v);
}
