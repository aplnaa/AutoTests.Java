package requests.authkey;

import java.util.List;

public class RequestPostClientBL {
    private List<Object> blacklist;
    private Boolean overwrite;

    public RequestPostClientBL(List<Object> blacklist, Boolean overwrite) {
        this.blacklist = blacklist;
        this.overwrite = overwrite;
    }
}
