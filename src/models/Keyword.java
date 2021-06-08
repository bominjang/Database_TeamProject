package models;

/**
 * 영화 검색 화면의 ComboBox 구현을 위한 Keyword 모델
 *
 * @author 김서영
 *
 */
public class Keyword {
    /**
     * 사용자가 선택한 Keyword의 Key값
     *
     * @see #getKey()
     * @see #setKey(String)
     */
    private String key;
    /**
     * 사용자에게 보여지는 Keyword의 선택지
     *
     * @see #getValue()
     * @see #setValue(String)
     */
    private String value;

    public Keyword() {}

    /**
     * Keyword 객체 생성자
     *
     * @param key Java에서 처리할 key값
     * @param value 사용자에게 보여지는 value값
     */
    public Keyword(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
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
