package object_sample.interface_type;

public class InterfaceTypeIntegerNested implements InterfaceTypeK<InterfaceTypeT<Integer>> {

    @Override
    public InterfaceTypeT<Integer> get() {
        return new InterfaceTypeTInteger();
    }
}
