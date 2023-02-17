package pastries;

class Cruller extends Pastry {
    @Override
    void accept(PastryVisitor v) {
        v.visitCruller(this);
    }
}
