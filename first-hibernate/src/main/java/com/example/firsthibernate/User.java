package com.example.firsthibernate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@ToString(exclude = {"addresses"})
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Column(nullable = false)
    @Getter @Setter
    private String name;

    @Column(nullable = false)
    @Getter @Setter
    private Integer age;

    @OneToMany(mappedBy="user")
    @Getter @Setter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Address> addresses;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}

