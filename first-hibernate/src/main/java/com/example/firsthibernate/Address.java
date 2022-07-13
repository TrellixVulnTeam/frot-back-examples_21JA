package com.example.firsthibernate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Column
    @Getter @Setter
    private String ciudad;

    @Column
    @Getter @Setter
    private String barrio;

    @Column
    @Getter @Setter
    private String calle;

    @ManyToOne
    @JoinColumn(nullable=false)
    @Getter @Setter
    private User user;

    public Address(String ciudad, String barrio, String calle, User user) {
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.calle = calle;
        this.user = user;
    }
}
