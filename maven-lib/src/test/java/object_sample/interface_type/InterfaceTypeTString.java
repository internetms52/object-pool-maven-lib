package object_sample.interface_type;

public class InterfaceTypeTString implements InterfaceTypeT<String> {
    @Override
    public String get() {
        return "test";
    }
}
