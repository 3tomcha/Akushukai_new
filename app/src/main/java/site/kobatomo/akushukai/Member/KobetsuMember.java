package site.kobatomo.akushukai.Member;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tomoya on 2018/05/23.
 */

public class KobetsuMember implements Serializable {
    private List nanbuList;
    private List member;
    private List maisuuList;
    private List url;

    public List getUrl() {
        return url;
    }

    public void setUrl(List url) {
        this.url = url;
    }


    public List getNanbu() {
        return nanbuList;
    }

    public void setNanbu(List nanbu) {
        this.nanbuList = nanbu;
    }

    public List getMember() {
        return member;
    }

    public void setMember(List member) {
        this.member = member;
    }

    public List getMaisuu() {
        return maisuuList;
    }

    public void setMaisuu(List maisuu) {
        this.maisuuList = maisuu;
    }
}
