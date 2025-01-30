package com.csye6225.webapp.entity;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "health_checks")
public class HealthCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_id")
    private int checkId;

    @Column(name = "datetime")
    private Instant datetime;

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "HealthCheck{" +
                "checkId=" + checkId +
                ", datetime=" + datetime +
                '}';
    }
}
