package fr.ali.amanzegouarene.tennisstatsapi.model;

import java.util.List;

public class Data {

    private Integer rank;
    private Integer points;
    private Integer weight;
    private Integer height;
    private Integer age;
    private List<Integer> last = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Data() {
    }

    /**
     *
     * @param last
     * @param rank
     * @param weight
     * @param age
     * @param points
     * @param height
     */
    public Data(Integer rank, Integer points, Integer weight, Integer height, Integer age, List<Integer> last) {
        super();
        this.rank = rank;
        this.points = points;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.last = last;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Integer> getLast() {
        return last;
    }

    public void setLast(List<Integer> last) {
        this.last = last;
    }
}
