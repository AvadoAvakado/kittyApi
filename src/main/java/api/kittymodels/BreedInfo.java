package api.kittymodels;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class BreedInfo {
    @Getter(AccessLevel.NONE)
    private WeightModel weight;
    private String id;
    private String name;
    private String cfa_url;
    private String vetstreet_url;
    private String vcahospitals_url;
    private String temperament;
    private String origin;
    private String country_codes;
    private String country_code;
    private String description;
    private String life_span;
    private int indoor;
    private int lap;
    private String alt_names;
    private int adaptability;
    private int affection_level;
    private int child_friendly;
    private int dog_friendly;
    private int energy_level;
    private int grooming;
    private int health_issues;
    private int intelligence;
    private int shedding_level;
    private int social_needs;
    private int stranger_friendly;
    private int vocalisation;
    private int experimental;
    private int hairless;
    private int natural;
    private int rare;
    private int rex;
    private int suppressed_tail;
    private int short_legs;
    private String wikipedia_url;
    private int hypoallergenic;

    public String getWeightImperial() {
        return weight.getImperial();
    }

    public String getWeightMetric() {
        return weight.getMetric();
    }

    @Getter(AccessLevel.PRIVATE)
    private class WeightModel {
        private String imperial;
        private String metric;
    }
}
