package weather;

public class NoConnection extends SpecifiedException{

    public String getExceptionMessage() {
        return "No internet connection";
    }
}
