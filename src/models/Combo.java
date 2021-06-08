package models;

/**
 * 리뷰 작성 화면의 ComboBox 구현을 위한 Combo 모델
 *
 * @author 김서영
 *
 */
public class Combo {
    /**
     * 사용자가 선택한 Combo의 Key값
     *
     * @see #getKey()
     * @see #setKey(int)
     */
    private int key;
    /**
     * 사용자에게 보여지는 Combo 선택지
     *
     * @see #getValue()
     * @see #setValue(String)
     */
    private String value;

    public Combo() {}

    /**
     * Combo 객체 생성자
     *
     * @param key Java에서 처리할 key값
     * @param value 사용자에게 보여지는 value값
     */
    public Combo(int key, String value) {
        this.key = key;
        this.value = value;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
