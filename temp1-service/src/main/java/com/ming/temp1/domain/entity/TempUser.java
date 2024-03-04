package com.ming.temp1.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("temp_user")
public class TempUser {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 分数
     */
    private Integer point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}