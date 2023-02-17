package pastries;

class PastryNamer implements PastryVisitor {
    @Override
    public void visitBeignet(Beignet b) {
        System.out.println("THIS IS FRENCH, Beignet yolo.");
    }

    @Override
    public void visitCruller(Cruller c) {
        System.out.println("WTF is a cruller?");
    }
}
