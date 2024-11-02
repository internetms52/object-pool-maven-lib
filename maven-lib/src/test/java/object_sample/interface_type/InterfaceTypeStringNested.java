package object_sample.interface_type;

public class InterfaceTypeStringNested implements InterfaceTypeK<InterfaceTypeT<String>> {

    @Override
    public InterfaceTypeT<String> get() {
        return new InterfaceTypeTString();
    }
}
