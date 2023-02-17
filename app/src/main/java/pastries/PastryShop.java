package pastries;

import java.util.List;

class PastryShop {
    public static void main(String[] args) {
        List<Pastry> pastries = List.of(new Beignet(), new Cruller());
        PastryNamer pastryNamer = new PastryNamer();
        pastries.forEach(p -> p.accept(pastryNamer));
    }
}
