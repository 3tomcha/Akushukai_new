package site.kobatomo.akushukai.Member;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tomoya on 2018/05/23.
 */

public class KobetsuMember implements Serializable {
    private List nanbu;
    private List member;
    private List maisuu;
    private List url;

    public List getUrl() {
        return url;
    }

    public void setUrl(List url) {
        this.url = url;
    }


    public List getNanbu() {
        return nanbu;
    }

    public void setNanbu(List nanbu) {
        this.nanbu = nanbu;
    }

    public List getMember() {
        return member;
    }

    public void setMember(List member) {
        this.member = member;
    }

    public List getMaisuu() {
        return maisuu;
    }

    public void setMaisuu(List maisuu) {
        this.maisuu = maisuu;
    }
}
