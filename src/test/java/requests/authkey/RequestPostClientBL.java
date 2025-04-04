package requests.authkey;

import java.util.List;

public class RequestPostClientBL {
    private List<Integer> blacklist;
    private Boolean overwrite;

    public RequestPostClientBL(List<Integer> blacklist, Boolean overwrite) {
        this.blacklist = blacklist;
        this.overwrite = overwrite;
    }
}
