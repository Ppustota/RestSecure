package zura.pustota.restsecure.model;

public enum Permission {
    USER_WRITE("user:write"),
    USER_READ("user:read");

    private String permission;

    Permission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
