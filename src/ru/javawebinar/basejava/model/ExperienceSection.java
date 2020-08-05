package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ExperienceSection extends AbstractSection {
    private final List<Experience> experiences;

    public ExperienceSection(List<Experience> experiences) {
        Objects.requireNonNull(experiences, "experiences must not be null");
        this.experiences = experiences;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    @Override
    public String toString() {
        return experiences.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSection that = (ExperienceSection) o;

        return experiences.equals(that.experiences);
    }

    @Override
    public int hashCode() {
        return experiences.hashCode();
    }
}
