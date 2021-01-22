package com.netkreker.crackermarket.model.category;

import com.netkreker.crackermarket.model.core.BaseEntity;

public class Category extends BaseEntity {

    public static Category findByName(String name) {
        return find("name", name.toLowerCase()).firstResult();
    }

}
