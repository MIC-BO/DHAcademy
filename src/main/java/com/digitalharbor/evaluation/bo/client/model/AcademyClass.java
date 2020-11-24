package com.digitalharbor.evaluation.bo.client.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Academy_Class")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademyClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private String title;
    private String description;
}