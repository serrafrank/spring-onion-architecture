package org.pay_my_buddy.shared.common.domain.api;

public abstract class BaseEvent implements Event {
    private int version = 1;

    public int version(){
        return version;
    }

    public void version(int version){
        this.version = version;
    }

    public void incrementVersion(){
        this.version++;
    }


}
