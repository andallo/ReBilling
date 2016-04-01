package models.tariffs;

/**
 * Created by andrey on 25.03.16.
 */
public class Operation {
    private String name;
    // packet / limit
    private String type;
    private Integer counter;
    private Integer limit;
    private boolean allow_over_limit;

    public void setName(String name) {
        this.name = name;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setAllow_over_limit(boolean allow_over_limit) {
        this.allow_over_limit = allow_over_limit;
    }

    public String getName() {
        return name;
    }

    public Integer getCounter() {
        return counter;
    }

    public Integer getLimit() {
        return limit;
    }

    public boolean isAllow_over_limit() {
        return allow_over_limit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
