package org.example;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


public class InfoImp {
    private String ip;
    private String serial;
    private Integer c;
    private Integer m;
    private Integer y;
    private Integer k;
    private Integer mono;
    private Integer color;
    private Integer total;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Integer getMono() {
        return mono;
    }

    public void setMono(Integer mono) {
        this.mono = mono;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public InfoImp(String ip, String serial, Integer c, Integer m, Integer y, Integer k, Integer mono, Integer color, Integer total) {
        this.ip = ip;
        this.serial = serial;
        this.c = c;
        this.m = m;
        this.y = y;
        this.k = k;
        this.mono = mono;
        this.color = color;
        this.total = total;
    }

    public InfoImp() {}

    @Override
    public String toString() {
        return "InfoImp{" +
                "ip='" + ip + '\'' +
                ", serial='" + serial + '\'' +
                ", c=" + c +
                ", m=" + m +
                ", y=" + y +
                ", k=" + k +
                ", mono=" + mono +
                ", color=" + color +
                ", total=" + total +
                '}';
    }

    public int transformToInt(String value) {
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            Number number = format.parse(value);

            return (int) Math.round(number.doubleValue());

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao converter numero");
        }
    }
}
