package com.pokedex.reactiveweb.model;

import java.util.Objects;
import java.util.StringJoiner;

public class PokemonEvent {
    private Long eventId;
    private String eventType;

    public PokemonEvent() {
    }

    public PokemonEvent(Long eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonEvent that = (PokemonEvent) o;
        return eventId.equals(that.eventId) &&
                Objects.equals(eventType, that.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventType);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PokemonEvent.class.getSimpleName() + "[", "]")
                .add("eventId=" + eventId)
                .add("eventType='" + eventType + "'")
                .toString();
    }
}
