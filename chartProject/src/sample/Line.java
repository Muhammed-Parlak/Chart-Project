package sample;

class Line {

    private String Country;
    private String Category;
    private String Name;
    private String Year;
    private int Value;


    public Line(String Year, String Name, String Country, int Value, String Category  ) {
        this.Country = Country;
        this.Year = Year;
        this.Name = Name;
        this.Value =Value;
        this.Category =Category;
    }

    @Override
    public String toString() {
        return  Year +  " " + Name+ " " +Country  +" " + Value + " "
                + Category  ;
    }

    public String getCountry() {
        return Country;
    }

    public String getCategory() {
        return Category;
    }

    public String getName() {
        return Name;
    }

    public String getYear() {
        return Year;
    }

    public int getValue() {
        return Value;
    }
}