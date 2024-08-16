package co.kr.ignite.samplelocalcache.banner.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Banner(String title, String content, @JsonIgnore DisplayCondition displayCondition) {
    public static List<Banner> createSamples() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        return LongStream.range(-10L, 30L)
                         .mapToObj(index -> createSample(now.plusMinutes(index)))
                         .collect(Collectors.toList());
    }

    private static Banner createSample(LocalDateTime dateTime) {
        DisplayCondition displayCondition = new DisplayCondition(new DisplayCondition.Period(dateTime, dateTime.plusMinutes(5L)));
        return new Banner("title for " + dateTime, "content for " + dateTime, displayCondition);
    }

    public boolean isDisplayable(LocalDateTime dateTime) {
        return displayCondition.isDisplayable(dateTime);
    }

    private record DisplayCondition(Period period) {
        public boolean isDisplayable(LocalDateTime dateTime) {
            return this.period.isDisplayable(dateTime);
        }

        private record Period(LocalDateTime startsAt, LocalDateTime endsAt) {
            public Period {
                validate(startsAt, endsAt);
            }

            private void validate(LocalDateTime startsAt, LocalDateTime endsAt) {
                Objects.requireNonNull(startsAt);
                Objects.requireNonNull(endsAt);
                if (endsAt.isBefore(startsAt)) {
                    throw new IllegalArgumentException("endsAt(" + endsAt + ") must be after startsAt(" + startsAt + ")");
                }
            }

            private boolean isDisplayable(LocalDateTime dateTime) {
                if (startsAt.isEqual(dateTime)) {
                    return true;
                }
                return startsAt.isBefore(dateTime) && endsAt().isAfter(dateTime);
            }
        }
    }
}
