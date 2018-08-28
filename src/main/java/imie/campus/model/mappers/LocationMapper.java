package imie.campus.model.mappers;

import imie.campus.core.mappers.AbstractEntityMapper;
import imie.campus.model.dto.LocationTO;
import imie.campus.model.entities.Location;
import org.springframework.stereotype.Service;

import java.util.Iterator;

import static java.util.Arrays.asList;

@Service
public class LocationMapper extends AbstractEntityMapper<Location, LocationTO> {
    public LocationMapper() {
        super(Location.class, LocationTO.class);
    }

    @Override
    public void configure() {}

    @Override
    public LocationTO map(Location source) {
        LocationTO target = super.map(source);
        target.setAddresses(asList(
                source.getAddress(), source.getAddress2(), source.getAddresse3()));
        return target;
    }

    @Override
    public Location reverse(LocationTO target) {
        Location source = super.reverse(target);
        Iterator<String> it = target.getAddresses().iterator();

        if (it.hasNext()) source.setAddress (it.next());
        if (it.hasNext()) source.setAddress2(it.next());
        if (it.hasNext()) source.setAddress3(it.next());

        return source;
    }
}
