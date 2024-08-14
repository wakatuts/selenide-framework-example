package com.wakatuts.data.creds;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCredData extends AbstractCredData {

    private String username;
    private String password;

}
