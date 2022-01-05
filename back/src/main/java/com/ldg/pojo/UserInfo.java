package com.ldg.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class UserInfo<D,G> implements Serializable {
    User user;
    int dynamicCount;
    int goodCount;
    List<D> dynamicData;
    List<G> goodData;
}
