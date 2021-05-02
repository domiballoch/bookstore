package bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    ARTS_AND_MUSIC("ARTS"),
    BIOGRAPHIES("BIO"),
    KIDS("KIDS"),
    TECHNOLOGY("TECH"),
    COOKING("COOK"),
    EDUCATIONAL("EDU"),
    FICTION("FICT"),
    HEALTH_AND_FITNESS("FIT"),
    HISTORY("HIST"),
    HORROR("HORR"),
    LITERATURE("LIT"),
    ROMANCE("ROM"),
    SCIENCE_FICTION("SCIFI"),
    TRAVEL("TRVL"),
    YOUNG_ADULT("YA");

    private String abbreviation;

}
