package imie.campus.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeTO extends AnnounceTO {

    private Integer category;

    private String exchangeType;

    private Set<PictureTO> pictures;

    private Set<PackageTO> packages;

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Set<PictureTO> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureTO> pictures) {
        this.pictures = pictures;
    }

    public Set<PackageTO> getPackages() {
        return packages;
    }

    public void setPackages(Set<PackageTO> packages) {
        this.packages = packages;
    }
}
