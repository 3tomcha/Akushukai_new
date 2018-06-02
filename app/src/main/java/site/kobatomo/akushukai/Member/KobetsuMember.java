package site.kobatomo.akushukai.Member;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tomoya on 2018/05/23.
 */

public class KobetsuMember implements Serializable {
    private String nanbu;
    private List member;
    private String maisuu;

    public String getNanbu() {
        return nanbu;
    }

    public void setNanbu(String nanbu) {
        this.nanbu = nanbu;
    }

    public List getMember() {
        return member;
    }

    public void setMember(List member) {
        this.member = member;
    }

    public String getMaisuu() {
        return maisuu;
    }

    public void setMaisuu(String maisuu) {
        this.maisuu = maisuu;
    }
}
