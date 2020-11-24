package com.digitalharbor.evaluation.bo.client.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentClassViewModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
}
