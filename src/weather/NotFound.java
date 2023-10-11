package weather;

public class NotFound extends SpecifiedException{
    private String city;
    public NotFound(String uCity){
        city = uCity;
    }

    public String getExceptionMessage() {
        return "Cannot found city: "+city;
    }
}
