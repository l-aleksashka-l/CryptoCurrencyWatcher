package financeid.model;

import jdk.jfr.DataAmount;

import javax.persistence.*;

@Entity
public class Crypto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String symbol;
    private String name;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Crypto() {
    }

    public Crypto(String symbol, String name, Double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  id + " | " + symbol + " | " + name  + " | " + price;
    }

}
