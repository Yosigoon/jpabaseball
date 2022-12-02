package jpasports.jpabaseball.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String address;
    private String zipCode;

    //JPA에서 protected 허용 함부로 new 생성 방지
    protected Address() {
    }

    public Address(String city, String address, String zipCode) {
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
    }
}
