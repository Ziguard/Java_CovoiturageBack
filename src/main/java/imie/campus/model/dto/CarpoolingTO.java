package imie.campus.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class CarpoolingTO extends AnnounceTO {

    @NotNull @Length(min = 3)
    private String driver;

    private String createdDate;

    @Valid private Set<LagTO> lags;

    @Valid private Set<StepTO> steps;

    private Set<String> passengers;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Set<LagTO> getLags() {
        return lags;
    }

    public void setLags(Set<LagTO> lags) {
        this.lags = lags;
    }

    public Set<StepTO> getSteps() {
        return steps;
    }

    public void setSteps(Set<StepTO> steps) {
        this.steps = steps;
    }

    public Set<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<String> passengers) {
        this.passengers = passengers;
    }
}
