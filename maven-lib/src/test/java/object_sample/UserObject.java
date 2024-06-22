package object_sample;

import hobby.internetms52.object_pool.annotation.ObjectPoolConstructor;

import java.util.UUID;

public class UserObject {
    String userName;
    String userEmail;

    @ObjectPoolConstructor
    public UserObject() {
        this.userName = UUID.randomUUID().toString();
        this.userEmail = UUID.randomUUID().toString();
    }

    public UserObject(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
