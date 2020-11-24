package com.digitalharbor.evaluation.bo.client.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Control {
    private String controlClass;
    private String url;
    private boolean needConfirmation;
    private String name;

}
