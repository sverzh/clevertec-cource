package ru.clevertec.checkrunner.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cardnumbers")
public class Card {
    @Id
    @Column(name = "cardnumber")
    private int number;
    @Column(name = "discount")
    private int discount;

}
