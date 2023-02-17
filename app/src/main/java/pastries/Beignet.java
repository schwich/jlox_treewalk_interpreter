package pastries;

class Beignet extends Pastry {
    @Override
    void accept(PastryVisitor v) {
        v.visitBeignet(this);
    }
}
