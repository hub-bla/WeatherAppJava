package weather;

public class NotFound extends SpecifiedException{


    public String getExceptionMessage() {
        return "Cannot found city";
    }
}
