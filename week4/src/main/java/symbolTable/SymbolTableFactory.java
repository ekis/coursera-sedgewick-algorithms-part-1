package symbolTable;

public final class SymbolTableFactory {

    private SymbolTableFactory(){}

    public static <K extends Comparable<? super K>, V> MySymbolTable<K, V> bst() {
        return MyBst.create();
    }

}
