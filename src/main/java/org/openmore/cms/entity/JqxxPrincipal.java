package org.openmore.cms.entity;

import java.security.Principal;

public class JqxxPrincipal implements Principal {

    private String userNumber;

    private String nickName;

    public JqxxPrincipal(String nickName) {
        this.nickName = nickName;
    }
    public JqxxPrincipal(String userNumber, String nickName) {
        this.userNumber = userNumber;
        this.nickName = nickName;
    }

    @Override
    public String getName() {
        return this.nickName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}