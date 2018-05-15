package serializers;

public class ProfileSerializer {
    private String id;
    private String uuid;
    private KontoUserSerializer kontoUser;

    public ProfileSerializer() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public KontoUserSerializer getKontoUser() {
        return kontoUser;
    }

    public void setKontoUser(KontoUserSerializer kontoUser) {
        this.kontoUser = kontoUser;
    }
}
