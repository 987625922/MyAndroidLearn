package com.wyt.common.entity;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/3/26 16:43
 */
public class Entity {
    public Entity() {
    }

    public Entity(String name) {
        this.name = name;
    }

    private String name;
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String privateName(){
        return "私有方法";
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                '}';
    }
}
