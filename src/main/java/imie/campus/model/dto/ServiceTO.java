package imie.campus.model.dto;

public class ServiceTO extends AnnounceTO {


    private Integer category;

    private Float price;


    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
